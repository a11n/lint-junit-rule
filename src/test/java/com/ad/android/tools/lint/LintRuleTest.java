package com.ad.android.tools.lint;

import com.android.tools.lint.Warning;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LintRuleTest {
  @Rule public Lint lint = new Lint(Helper.dummyDetector(), Helper.dummyIssue());

  @Before
  public void setUp() throws Exception {
    lint.wrapper = Helper.dummyWrapper();
  }

  @Test
  public void test() throws Exception {
    List<Warning> warnings = lint.files("file1", "file2");

    assertThat(warnings).hasSize(2);
  }
}
