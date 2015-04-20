package com.ad.android.tools.lint;

import com.android.annotations.NonNull;
import com.android.tools.lint.Warning;
import com.android.tools.lint.detector.api.Issue;
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
  TestLintClient testLintClient = new TestLintClient();

  private String[] files;
  private Issue[] issues;

  private List<Warning> warnings;

  /**
   * Constructs a new {@code Lint} rule. <p> <b>Note:</b> Make sure to set files
   * and issues before calling {@code analyze()}.
   */
  public Lint() {
  }

  /**
   * Constructs a new {@code Lint} rule.
   *
   * @param files to be considered by Lint analysis.
   * @param issues to be considered by Lint analysis.
   */
  public Lint(@NonNull String[] files, @NonNull Issue[] issues) {
    this.files = files;
    this.issues = issues;
  }

  /**
   * This is called by the JUnit runner. <p> You don't need to interact with
   * this method.
   */
  @Override public Statement apply(Statement base, Description description) {
    return new LintStatement(base);
  }

  /**
   * Set files to be considered by Lint analysis.
   *
   * @param relativePaths to the file, starting at test/resources.
   */
  public void setFiles(@NonNull String... relativePaths) {
    files = relativePaths;
  }

  /**
   * Set issues to be considered by Lint analysis.
   *
   * @param issues to be considered by Lint analysis.
   */
  public void setIssues(@NonNull Issue... issues) {
    this.issues = issues;
  }

  /**
   * Performs a Lint analysis on the specified files and searching for the
   * specified issues. <p> Populated warnings could be obtained with {@code
   * getWarnings()}. <p> <b>Note:</b> Make sure {@code files} and {@code issues}
   * have been set prior to calling {@code analyze()}. Otherwise an {@code
   * IllegalStateException} will be thrown.
   *
   * @throws Exception for unexpected behavior during Lint analysis (e.g. could
   * not read or access file)
   */
  public void analyze() throws Exception {
    if (files == null || issues == null) {
      throw new IllegalStateException("Files or issues must not be null");
    }

    warnings = testLintClient.analyze(files, issues);
  }

  /**
   * Gets a list of warnings, populated by a previous Lint analysis. <p>A
   * successful Lint analysis will result in an empty (size == 0) list of
   * warnings.<p> <b>Note:</b> Calling this prior to calling {@code analyze()}
   * will result in an {@code IllegalStateException}.
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
}
