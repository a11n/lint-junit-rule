package com.ad.android.tools.lint;

import com.android.annotations.NonNull;
import org.intellij.lang.annotations.Language;

class TestFile {
  private final String to;
  private final String source;

  @NonNull
  public static TestFile java(@NonNull String to, @NonNull @Language("JAVA") String source) {
    return new TestFile(to, source);
  }

  @NonNull
  public static TestFile xml(@NonNull String to, @NonNull @Language("XML") String source) {
    return new TestFile(to, source);
  }

  private TestFile(String to, String source) {
    this.to = to;
    this.source = source;
  }

  public String getTo() {
    return to;
  }

  public String getSource() {
    return source;
  }
}
