package de.ad.android.tools.lint;

import com.ad.android.tools.lint.Lint;
import com.ad.android.tools.lint.assertj.WarningAssert;
import com.android.tools.lint.Warning;
import java.util.List;
import org.hamcrest.MatcherAssert;
import org.junit.Rule;
import org.junit.Test;

import static com.ad.android.tools.lint.hamcrest.WarningMatcher.atLine;
import static com.ad.android.tools.lint.hamcrest.WarningMatcher.hasWarnings;
import static com.ad.android.tools.lint.hamcrest.WarningMatcher.in;
import static com.ad.android.tools.lint.hamcrest.WarningMatcher.withMessage;

public class HelloWorldDetectorTest {
  @Rule public Lint lint = new Lint(new HelloWorldDetector(), HelloWorldDetector.ISSUE);

  @Test
  public void shouldDetectWarning() throws Exception {
    List<Warning> lintResult = lint.files("AndroidManifest.xml");

    //AssertJ
    WarningAssert.assertThat(lintResult)
        .hasWarnings(1)
        .in("AndroidManifest.xml")
        .atLine(7)
        .withMessage("Unexpected title \"@string/app_name\". Should be \"Hello world.\".");

    //Hamcrest
    MatcherAssert.assertThat(lintResult,
        hasWarnings(
            in("AndroidManifest.xml"),
            atLine(7),
            withMessage("Unexpected title \"@string/app_name\". Should be \"Hello world.\".")));
  }
}
