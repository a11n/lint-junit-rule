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
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * A JUnit rule for unit testing custom Lint rules. <p> Example:
 * <pre>
 * <code>
 * {@literal @}Rule public Lint lint = new Lint();
 *
 * {@literal @}Test
 * public void test() throws Exception {
 *  lint.setFiles("AndroidManifest.xml", "res/values/string.xml");
 *  lint.setIssues(MyCustomRule.ISSUE);
 *
 *  lint.analyze();
 *
 *  List&lt;Warning&gt; warnings = lint.getWarnings();
 *
 *  assertThat(warnings).hasSize(2);
 * }
 * </code>
 * </pre>
 */
public class Lint implements TestRule {
  GenericLintDetectorTest genericLintDetectorTest;

  private String[] files;
  private Detector detector;

  private Issue[] issues;
  private List<Warning> warnings;

  /**
   * Constructs a new {@code Lint} rule. <p> <b>Note:</b> Make sure to set files, detector and
   * issues before calling {@code analyze()}.
   */
  public Lint() {
    genericLintDetectorTest = new GenericLintDetectorTest();
  }

  /**
   * Constructs a new {@code Lint} rule.
   *
   * @param files to be analyzed by Lint.
   * @param detector to use for Lint analysis.
   * @param issues to be reported by Lint analysis.
   */
  public Lint(@NonNull String[] files, @NonNull Detector detector, @NonNull Issue[] issues) {
    this();

    this.files = files;
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
   * Set files to be analyzed by Lint.
   *
   * @param relativePaths to the file, starting at test/resources.
   */
  public void setFiles(@NonNull String... relativePaths) {
    files = relativePaths;
  }

  /**
   * Set detector to use for Lint analysis.
   *
   * @param detector to use for Lint analysis.
   */
  public void setDetector(@NonNull Detector detector) {
    this.detector = detector;
  }

  /**
   * Set issues to be reported by Lint analysis.
   *
   * @param issues to be reported by Lint analysis.
   */
  public void setIssues(@NonNull Issue... issues) {
    this.issues = issues;
  }

  /**
   * Performs a Lint analysis on the specified files, using the specified detector and searching for
   * the specified issues. <p> Populated warnings could be obtained with {@code getWarnings()}. <p>
   * <b>Note:</b> Make sure {@code files}, {@code detector} and {@code issues} have been set prior
   * to calling {@code analyze()}. Otherwise an {@code IllegalStateException} will be thrown.
   *
   * @throws Exception for unexpected behavior during Lint analysis (e.g. could not read or access
   * file)
   */
  public void analyze() throws Exception {
    if (files == null || detector == null || issues == null) {
      throw new IllegalStateException("Files, detector or issues must not be null");
    }

    genericLintDetectorTest.analyze(detector, Arrays.asList(issues), files);

    warnings = genericLintDetectorTest.getWarnings();
  }

  /**
   * Gets a list of warnings, populated by a previous Lint analysis. <p>A successful Lint analysis
   * will result in an empty (size == 0) list of warnings.<p> <b>Note:</b> Calling this prior to
   * calling {@code analyze()} will result in an {@code IllegalStateException}.
   *
   * @return a list of warnings
   */
  public @NonNull List<Warning> getWarnings() {
    if (warnings == null) {
      throw new IllegalStateException("analyze() must be called first");
    }

    return warnings;
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

  static class GenericLintDetectorTest extends LintDetectorTest {

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
