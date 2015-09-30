package com.ad.android.tools.lint.assertj;

import com.android.tools.lint.Warning;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class WarningAssertTest {
  @Test
  public void testHasWarnings() throws Exception {
    Warning firstWarning = new Warning(null, "Message 1", null, null);
    firstWarning.file = new File("File1.java");
    firstWarning.line = 8;

    Warning secondWarning = new Warning(null, "Message 2", null, null);
    secondWarning.file = new File("File2.xml");
    secondWarning.line = 14;

    List<Warning> lintResult = Arrays.asList(firstWarning, secondWarning);

    WarningAssert.assertThat(lintResult)
        .hasWarnings(2)
        .in("File1.java", "File2.xml")
        .atLine(8, 14)
        .withMessage("Message 1", "Message 2");
  }
}
