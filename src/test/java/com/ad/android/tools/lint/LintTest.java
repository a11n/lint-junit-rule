package com.ad.android.tools.lint;

import com.android.tools.lint.Warning;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;
import java.util.List;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LintTest {

  @Test
  public void shouldAnalyzeAndReturnWarnings() throws Exception {
    String[] files = {"file"};
    Detector detector = Helper.dummyDetector();
    Issue[] issues = {Helper.dummyIssue()};
    Lint lint = new Lint(detector, issues);
    
    lint.wrapper = Helper.dummyWrapper();
    
    List<Warning> warnings = lint.files(files);
    
    assertThat(warnings).isEqualTo(Helper.dummyWarnings());
  }
}
