package com.ad.android.tools.lint;

import com.android.tools.lint.detector.api.Issue;
import org.junit.Test;

public class TestLintClientTest {
  @Test
  public void shouldAnalyze() throws Exception {
    TestLintClient testLintClient = new TestLintClient();

    String[] files = {"file.txt"};
    Issue[] issues = {Helper.dummyIssue()};
    
    testLintClient.analyze(files, issues);
  }
}
