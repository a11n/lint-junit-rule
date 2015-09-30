package com.ad.android.tools.lint.hamcrest;

import com.android.tools.lint.Warning;
import java.util.Arrays;
import java.util.List;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.AllOf;

public class WarningMatcher {
  public static Matcher<List<Warning>> hasWarnings(Matcher<List<Warning>>... matchers) {
    return new AllOf<>(Arrays.asList(matchers));
  }

  public static Matcher<List<Warning>> in(String... filenames) {
    return new FileMatcher(filenames);
  }

  public static Matcher<List<Warning>> atLine(Integer... lines) {
    return new LineNumberMatcher(lines);
  }

  public static Matcher<List<Warning>> withMessage(String... messages) {
    return new MessageMatcher(messages);
  }

  private static class FileMatcher extends TypeSafeMatcher<List<Warning>> {
    private final String[] filenames;

    private String expectedValue;
    private String actualValue;

    public FileMatcher(String[] filenames) {
      this.filenames = filenames;
    }

    @Override protected boolean matchesSafely(List<Warning> warnings) {
      if (warnings == null) {
        return false;
      }

      if (warnings.size() != filenames.length) {
        return false;
      }

      for (int i = 0; i < warnings.size(); i++) {
        expectedValue = filenames[i];
        actualValue = warnings.get(i).file.getName();
        if (!expectedValue.equals(actualValue)) {
          return false;
        }
      }

      return true;
    }

    @Override public void describeTo(Description description) {
      description.appendText("Expected file name ").appendValue(expectedValue);
    }

    @Override
    protected void describeMismatchSafely(List<Warning> item, Description mismatchDescription) {
      mismatchDescription.appendText("but was ").appendValue(actualValue);
    }
  }

  private static class LineNumberMatcher extends TypeSafeMatcher<List<Warning>> {
    private final Integer[] lines;

    private int expectedValue;
    private int actualValue;

    public LineNumberMatcher(Integer[] lines) {
      this.lines = lines;
    }

    @Override protected boolean matchesSafely(List<Warning> warnings) {
      if (warnings == null) {
        return false;
      }

      if (warnings.size() != lines.length) {
        return false;
      }

      for (int i = 0; i < warnings.size(); i++) {
        expectedValue = lines[i];
        actualValue = warnings.get(i).line;
        if (expectedValue != actualValue) {
          return false;
        }
      }

      return true;
    }

    @Override public void describeTo(Description description) {
      description.appendText("Expected line number ").appendValue(expectedValue);
    }

    @Override
    protected void describeMismatchSafely(List<Warning> item, Description mismatchDescription) {
      mismatchDescription.appendText("but was ").appendValue(actualValue);
    }
  }

  private static class MessageMatcher extends TypeSafeMatcher<List<Warning>> {
    private final String[] messages;

    private String expectedValue;
    private String actualValue;

    public MessageMatcher(String[] messages) {
      this.messages = messages;
    }

    @Override protected boolean matchesSafely(List<Warning> warnings) {
      if (warnings == null) {
        return false;
      }

      if (warnings.size() != messages.length) {
        return false;
      }

      for (int i = 0; i < warnings.size(); i++) {
        expectedValue = messages[i];
        actualValue = warnings.get(i).message;
        if (!expectedValue.equals(actualValue)) {
          return false;
        }
      }

      return true;
    }

    @Override public void describeTo(Description description) {
      description.appendText("Expected message ").appendValue(expectedValue);
    }

    @Override
    protected void describeMismatchSafely(List<Warning> item, Description mismatchDescription) {
      mismatchDescription.appendText("but was ").appendValue(actualValue);
    }
  }
}
