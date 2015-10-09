package com.ad.android.tools.lint;

import com.android.tools.lint.Warning;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static com.ad.android.tools.lint.TestFile.java;
import static com.ad.android.tools.lint.TestFile.xml;
import static org.assertj.core.api.Assertions.assertThat;

public class LintTest {
  @Before
  public void setUp() throws Exception {
    Lint.wrapper = Helper.dummyWrapper();
  }
  
  @Test
  public void shouldAnalyzeExternalResourceAndReturnWarnings() throws Exception {
    String[] files = { "file" };
    Detector detector = Helper.dummyDetector();
    Issue[] issues = { Helper.dummyIssue() };
    Lint lint = new Lint(detector, issues);

    List<Warning> warnings = lint.files(files);

    assertThat(warnings).isEqualTo(Helper.dummyWarnings());
  }

  @Test
  public void shouldAnalyzeInlineResourceAndReturnWarnings() throws Exception {
    TestFile[] files = {
        xml("file1", "<xml></xml>"),
        java("file2", "public class Class{}") };
    Detector detector = Helper.dummyDetector();
    Issue[] issues = { Helper.dummyIssue() };
    Lint lint = new Lint(detector, issues);

    List<Warning> warnings = lint.project(files);

    assertThat(warnings).isEqualTo(Helper.dummyWarnings());
  }
}
