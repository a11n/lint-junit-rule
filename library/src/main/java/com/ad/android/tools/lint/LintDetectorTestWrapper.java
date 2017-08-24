package com.ad.android.tools.lint;

import com.android.tools.lint.Warning;
import com.android.tools.lint.checks.infrastructure.LintDetectorTest;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.junit.Ignore;

@Ignore
final class LintDetectorTestWrapper extends LintDetectorTest implements Wrapper {
  private ExtendedTestLintClient testLintClient;

  private Detector detector;
  private List<Issue> issues;

  private List<Warning> warnings;

  public void setTestLintClient(
      ExtendedTestLintClient testLintClient) {
    this.testLintClient = testLintClient;
  }

  @Override public void analyze(Detector detector, List<Issue> issues, String... files)
      throws Exception {
    this.detector = detector;
    this.issues = issues;

    lintFiles(files);
  }

  @Override public void analyze(Detector detector, List<Issue> issues,
      com.ad.android.tools.lint.TestFile... files)
      throws Exception {
    this.detector = detector;
    this.issues = issues;

    TestFile[] testFiles = convert(files);

    lintProject(testFiles);
  }

  @NotNull private TestFile[] convert(com.ad.android.tools.lint.TestFile[] files) {
    TestFile[] testFiles = new TestFile[files.length];
    for (int i = 0; i < testFiles.length; i++) {
      testFiles[i] = file().to(files[i].getTo()).withSource(files[i].getSource());
    }
    return testFiles;
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
    if(testLintClient == null)
      throw new IllegalStateException("Please call setExtendedTestLintClient() first.");

    String result = checkLint(testLintClient, files);

    warnings = testLintClient.getWarnings();

    return result;
  }

    @Override
    protected InputStream getTestResource(String relativePath, boolean expectExists) {
        try {
            return FileUtil.getResource(relativePath, getClass().getProtectionDomain().getCodeSource());
        } catch (Exception e) {
            fail(e.getMessage());
        }
        return null;
    }

  class ExtendedTestLintClient extends TestLintClient {

    public List<Warning> getWarnings() {
      return mWarnings;
    }
  }
}
