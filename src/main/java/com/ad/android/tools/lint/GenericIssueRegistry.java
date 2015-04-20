package com.ad.android.tools.lint;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;
import java.util.Arrays;
import java.util.List;

class GenericIssueRegistry extends IssueRegistry{
  private Issue[] issues;
  
  public GenericIssueRegistry(){
    issues = new Issue[0];
  }

  public void setIssues(Issue[] issues) {
    this.issues = issues;
  }

  @Override public List<Issue> getIssues() {
    return Arrays.asList(issues);
  }
}
