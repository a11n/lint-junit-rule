package com.ad.android.tools.lint.assertj;

import com.android.tools.lint.Warning;
import java.util.List;
import org.assertj.core.api.AbstractListAssert;

public class WarningAssert extends AbstractListAssert<WarningAssert, List<Warning>, Warning> {
  private WarningAssert(List<Warning> actual) {
    super(actual, WarningAssert.class);
  }

  public static WarningAssert assertThat(List<Warning> actual) {
    return new WarningAssert(actual);
  }

  public WarningAssert hasWarnings(int count) {
    if (actual.size() != count) {
      failWithMessage("Expected warning's size to be <%d> but was <%d>", count, actual.size());
    }

    return this;
  }

  public WarningAssert in(String... filenames) {
    assertThat(actual).extracting("file.name").containsExactly(filenames);

    return this;
  }

  public WarningAssert atLine(Integer... lines) {
    assertThat(actual).extracting("line").containsExactly(lines);

    return this;
  }

  public WarningAssert withMessage(String... messages) {
    assertThat(actual).extracting("message").containsExactly(messages);

    return this;
  }
}
