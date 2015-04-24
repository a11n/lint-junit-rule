package com.ad.android.tools.lint;

import com.android.tools.lint.detector.api.Issue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestLintClientTest {
  @Test
  public void shouldAnalyze() throws Exception {
    TestLintClient testLintClient = new TestLintClient();

    String[] files = {"file.txt"};
    Issue[] issues = {Helper.dummyIssue()};
    
    testLintClient.analyze(files, issues);
  }

  @Rule public ExpectedException exception = ExpectedException.none();
  @Test
  public void shouldThrowIllegalArgumentException() throws Exception {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("'invalid.file' is not a valid resource");

    TestLintClient testLintClient = new TestLintClient();

    String[] files = {"invalid.file"};
    Issue[] issues = {Helper.dummyIssue()};

    testLintClient.analyze(files, issues);
  }
}
