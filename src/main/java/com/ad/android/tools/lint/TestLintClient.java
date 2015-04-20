package com.ad.android.tools.lint;

import com.android.tools.lint.LintCliClient;
import com.android.tools.lint.LintCliFlags;
import com.android.tools.lint.Reporter;
import com.android.tools.lint.TextReporter;
import com.android.tools.lint.Warning;
import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

class TestLintClient extends LintCliClient {
  Reporter reporter = new TextReporter(this, mFlags, new StringWriter(), false);
  GenericIssueRegistry issueRegistry = new GenericIssueRegistry();
  
  public TestLintClient() {
    super(new LintCliFlags());
    mFlags.getReporters().add(reporter);
  }

  public List<Warning> analyze(String[] relativePaths, Issue[] issues)
      throws IOException {
    List<File> files = getFiles(relativePaths);
    issueRegistry.setIssues(issues);

    run(issueRegistry, files);

    return mWarnings;
  }

  private List<File> getFiles(String... relativePaths) {
    List<File> files = new ArrayList<File>();
    for (String relativePath : relativePaths) {
      ClassLoader classLoader = TestLintClient.class.getClassLoader();
      files.add(new File(classLoader.getResource(relativePath).getFile()));
    }

    return files;
  }

  @Override public int run(IssueRegistry registry, List<File> files)
      throws IOException {
    return super.run(registry, files);
  }
}
