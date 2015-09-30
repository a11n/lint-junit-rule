package com.ad.android.tools.lint.hamcrest;

import com.android.tools.lint.Warning;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import static com.ad.android.tools.lint.hamcrest.WarningMatcher.atLine;
import static com.ad.android.tools.lint.hamcrest.WarningMatcher.hasWarnings;
import static com.ad.android.tools.lint.hamcrest.WarningMatcher.in;
import static com.ad.android.tools.lint.hamcrest.WarningMatcher.withMessage;
import static org.hamcrest.MatcherAssert.assertThat;

public class WarningMatcherTest {
  @Test
  public void test() throws Exception {
    Warning firstWarning = new Warning(null, "Message 1", null, null);
    firstWarning.file = new File("File1.java");
    firstWarning.line = 8;

    Warning secondWarning = new Warning(null, "Message 2", null, null);
    secondWarning.file = new File("File2.xml");
    secondWarning.line = 14;

    List<Warning> lintResult = Arrays.asList(firstWarning, secondWarning);

    assertThat(lintResult,
        hasWarnings(in("File1.java", "File2.xml"), atLine(8, 14),
            withMessage("Message 1", "Message 2")));
  }
}
