package com.ad.android.tools.lint;

import com.android.annotations.NonNull;
import com.android.tools.lint.Warning;
import com.android.tools.lint.checks.infrastructure.LintDetectorTest;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import org.junit.Ignore;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * A JUnit rule for unit testing custom Lint rules. <p> Example:
 * <pre>
 * <code>
 * {@literal @}Rule public Lint lint =
 *  new Lint(new MyCustomRule(), MyCustomRule.ISSUE);
 *
 * {@literal @}Test
 * public void test() throws Exception {
 *  List&lt;Warning&gt; warnings =
 *    lint.files("AndroidManifest.xml", "res/values/string.xml");
 *
 *  assertThat(warnings).hasSize(2);
 * }
 * </code>
 * </pre>
 */
public class Lint implements TestRule {
  Wrapper wrapper;

  private final Detector detector;
  private final Issue[] issues;

  private String[] files;

  /**
   * Constructs a new {@code Lint} junit rule.
   * <p> Example:
   * <pre>
   * <code>
   * {@literal @}Rule public Lint lint =
   *  new Lint(new MyCustomRule(), MyCustomRule.ISSUE);
   * </code>
   * </pre>
   * 
   * @param detector the detector under test.
   * @param issues the issues under test.
   */
  public Lint(@NonNull Detector detector, @NonNull Issue... issues) {
    wrapper = new Wrapper.GenericDetectorTest();

    this.detector = detector;
    this.issues = issues;
  }

  /**
   * This is called by the JUnit runner. <p> You don't need to interact with this method.
   */
  @Override public Statement apply(Statement base, Description description) {
    return new LintStatement(base);
  }
  
  /**
   * Performs a Lint analysis on the specified files.
   * <p> Example:
   * <pre>
   * <code>
   *  List&lt;Warning&gt; warnings =
   *    lint.files("AndroidManifest.xml", "res/values/string.xml");
   *
   *  assertThat(warnings).hasSize(2);
   * </code>
   * </pre>
   *
   * <b>Note:</b> Lint uses the specified {@link Detector} and will report the specified {@link Issue}s.
   *
   * @param files the relative paths of the files to analyze.
   * 
   * @return a {@link List} of {@link Warning}s. 
   * 
   * @throws Exception for unexpected behavior during Lint analysis (e.g. could not find or access
   * file)
   */
  public List<Warning> files(@NonNull String... files) throws Exception {
    wrapper.analyze(detector, Arrays.asList(issues), files);

    return wrapper.getWarnings();
  }

  private static class LintStatement extends Statement {
    private Statement base;

    private LintStatement(Statement base) {
      this.base = base;
    }

    @Override public void evaluate() throws Throwable {
      base.evaluate();
    }
  }

  interface Wrapper {

    void analyze(Detector detector, List<Issue> issues, String... files) throws Exception;

    List<Warning> getWarnings();

    @Ignore
    final class GenericDetectorTest extends LintDetectorTest implements Wrapper {
      private Detector detector;
      private List<Issue> issues;

      private List<Warning> warnings;

      public void analyze(Detector detector, List<Issue> issues, String... files) throws Exception {
        this.detector = detector;
        this.issues = issues;

        lintFiles(files);
      }

      public List<Warning> getWarnings() {
        return warnings;
      }

      @Override protected Detector getDetector() {
        return detector;
      }

      @Override protected List<Issue> getIssues() {
        return issues;
      }

      @Override protected String checkLint(List<File> files) throws Exception {
        ExtendedTestLintClient testLintClient = new ExtendedTestLintClient();
        String result = checkLint(testLintClient, files);

        warnings = testLintClient.getWarning();

        return result;
      }

      //this needs to be overridden (see https://code.google.com/p/android/issues/detail?id=182195)
      @Override protected InputStream getTestResource(String relativePath, boolean expectExists) {
        String path = relativePath; //$NON-NLS-1$
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(path);
        if (!expectExists && stream == null) {
          return null;
        }
        return stream;
      }

      private class ExtendedTestLintClient extends TestLintClient {

        public List<Warning> getWarning() {
          return mWarnings;
        }
      }
    }
  }
}
