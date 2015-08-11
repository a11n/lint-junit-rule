package com.ad.android.tools.lint;

import com.android.tools.lint.Warning;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Severity;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Helper {
  public static Detector dummyDetector() {
    return mock(Detector.class);
  }

  public static Issue dummyIssue() {
    return Issue.create("", " ", " ", Category.LINT, 0, Severity.IGNORE,
        mock(Implementation.class));
  }

  public static Lint.GenericLintDetectorTest dummyDetectorTest(){
    Lint.GenericLintDetectorTest detectorTest = mock(Lint.GenericLintDetectorTest.class);
    
    when(detectorTest.getWarnings()).thenReturn(dummyWarnings());
    
    return detectorTest;
  }
  
  public static List<Warning> dummyWarnings() {
    Issue issue = dummyIssue();
    Warning warning1 = new Warning(issue, "Warning 1", Severity.IGNORE, null);
    Warning warning2 = new Warning(issue, "Warning 2", Severity.IGNORE, null);

    return Arrays.asList(warning1, warning2);
  }
}
