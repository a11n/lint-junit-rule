package com.ad.android.tools.lint;

import com.android.tools.lint.Warning;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LintRuleTest {
  @Rule public Lint lint = new Lint();

  @Before
  public void setUp() throws Exception {
    lint.testLintClient = Helper.dummyTestLintClient();
  }

  @Test
  public void test() throws Exception {
    lint.setFiles("file1", "file2");
    lint.setIssues(Helper.dummyIssue());
    
    lint.analyze();

    List<Warning> warnings = lint.getWarnings();
    
    assertThat(warnings).hasSize(2);
  }
}
