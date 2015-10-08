package com.ad.android.tools.lint;

import com.android.tools.lint.Warning;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Project;
import com.android.tools.lint.detector.api.Severity;
import java.io.File;
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

  public static Wrapper dummyWrapper(){
    Wrapper detectorTest = mock(Wrapper.class);
    
    when(detectorTest.getWarnings()).thenReturn(dummyWarnings());
    
    return detectorTest;
  }
  
  public static List<Warning> dummyWarnings() {
    Warning warning1 = MockWarning.with("file1", 1, "Warning 1");
    Warning warning2 = MockWarning.with("file2", 2, "Warning 2");

    return Arrays.asList(warning1, warning2);
  }
  
  private static class MockWarning extends Warning{

    private MockWarning(Issue issue, String message, Severity severity,
        Project project) {
      super(issue, message, severity, project);
    }
    
    public static MockWarning with(String file, int line, String message){
      MockWarning mockWarning = new MockWarning(dummyIssue(), message, Severity.IGNORE, null);
      
      mockWarning.file = new File(file);
      mockWarning.line = line;
      
      return mockWarning;
    }
  }
}
