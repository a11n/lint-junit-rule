package com.ad.android.tools.lint;

import com.android.tools.lint.Warning;
import com.android.tools.lint.detector.api.Issue;
import java.util.List;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LintTest {

  @Test
  public void shouldAnalyzeAndReturnWarnings() throws Exception {
    String[] files = {"file"};
    Issue[] issues = {Helper.dummyIssue()};
    Lint lint = new Lint(files, issues);
    
    lint.testLintClient = Helper.dummyTestLintClient();
    
    lint.analyze();
    
    List<Warning> warnings = lint.getWarnings();
    
    assertThat(warnings).isEqualTo(Helper.dummyWarnings());
  }

  @Test(expected = IllegalStateException.class)
  public void analyzeShouldThrowWhenFilesNotSet() throws Exception {
    Lint lint = new Lint();
    
    lint.setIssues(Helper.dummyIssue());

    lint.analyze();
  }

  @Test(expected = IllegalStateException.class)
  public void analyzeShouldThrowWhenIssuesNotSet() throws Exception {
    Lint lint = new Lint();

    lint.setFiles("file");

    lint.analyze();
  }

  @Test(expected = IllegalStateException.class)
  public void warningShouldThrowWhenCalledBeforeAnalyze() throws Exception {
    Lint lint = new Lint();

    lint.getWarnings();
  }
}
