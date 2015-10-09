package com.ad.android.tools.lint;

import com.android.tools.lint.Warning;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.ad.android.tools.lint.TestFile.java;
import static com.ad.android.tools.lint.TestFile.xml;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.StrictAssertions.tuple;

public class LintRuleTest {
  @Rule public Lint lint = new Lint(Helper.dummyDetector(), Helper.dummyIssue());

  @Before
  public void setUp() throws Exception {
    Lint.wrapper = Helper.dummyWrapper();
  }

  @Test
  public void shouldLintFilesAndReturnWarnings() throws Exception {
    List<Warning> warnings = lint.files("file1", "file2");

    assertThat(warnings).extracting("file.name", "line", "message")
        .containsExactly(
            tuple("file1", 1, "Warning 1"),
            tuple("file2", 2, "Warning 2"));
  }

  @Test
  public void shouldLintProjectAndReturnWarnings() throws Exception {
    List<Warning> warnings = lint.project(
        xml("file1", "<xml></xml>"),
        java("file2", "public class Class{}"));

    assertThat(warnings).extracting("file.name", "line", "message")
        .containsExactly(
            tuple("file1", 1, "Warning 1"),
            tuple("file2", 2, "Warning 2"));
  }
}
