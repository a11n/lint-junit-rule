package com.ad.android.tools.lint;

import org.junit.Test;

import static com.ad.android.tools.lint.TestFile.xml;

public class LintDetectorTestWrapperTest {
  @Test(expected = IllegalStateException.class)
  public void shouldThrowIllegalState() throws Exception {
    LintDetectorTestWrapper wrapper = new LintDetectorTestWrapper();

    wrapper.analyze(null, null, xml("file", ""));
  }

  @Test
  public void shouldAnalyzeFile() throws Exception {
    LintDetectorTestWrapper wrapper = new LintDetectorTestWrapper();
    wrapper.setTestLintClient(Helper.dummyClient());

    Helper.createTestFile(this, "file");

    wrapper.analyze(null, null, xml("file", ""));
  }

  @Test
  public void shouldAnalyzeProject() throws Exception {
    LintDetectorTestWrapper wrapper = new LintDetectorTestWrapper();
    wrapper.setTestLintClient(Helper.dummyClient());

    wrapper.analyze(null, null, xml("file", ""));
  }
}
