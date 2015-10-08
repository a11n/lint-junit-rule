package com.ad.android.tools.lint;

import com.android.tools.lint.Warning;
import com.android.tools.lint.checks.infrastructure.LintDetectorTest;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.junit.Ignore;

interface Wrapper {

  void analyze(Detector detector, List<Issue> issues, String... files) throws Exception;

  void analyze(Detector detector, List<Issue> issues, TestFile... files)
      throws Exception;

  List<Warning> getWarnings();
}
