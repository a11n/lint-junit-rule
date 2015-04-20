package com.ad.android.tools.lint;

import com.android.tools.lint.detector.api.Issue;
import java.util.List;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GenericIssueRegistryTest {
  @Test
  public void shouldReturnEmptyIssueOnDefault() throws Exception {
    GenericIssueRegistry genericIssueRegistry = new GenericIssueRegistry();

    List<Issue> issues = genericIssueRegistry.getIssues();
    
    assertThat(issues).hasSize(0);
  }

  @Test
  public void shouldSetIssues() throws Exception {
    GenericIssueRegistry genericIssueRegistry = new GenericIssueRegistry();

    Issue[] issues = {Helper.dummyIssue(), Helper.dummyIssue()};
    genericIssueRegistry.setIssues(issues);
    
    List<Issue> actual = genericIssueRegistry.getIssues();
    
    assertThat(actual).hasSize(2);
  }
}
