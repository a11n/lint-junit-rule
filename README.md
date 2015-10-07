# lint-junit-rule [![Build Status](https://travis-ci.org/a11n/lint-junit-rule.svg)](https://travis-ci.org/a11n/lint-junit-rule) [![Coverage Status](https://coveralls.io/repos/a11n/lint-junit-rule/badge.svg)](https://coveralls.io/r/a11n/lint-junit-rule) [ ![Download](https://api.bintray.com/packages/a11n/maven/com.ad.android.tools.lint/images/download.svg) ](https://bintray.com/a11n/maven/com.ad.android.tools.lint/_latestVersion)

A JUnit rule which allows unit testing of custom Lint rules.

If you want to learn more about custom Lint rules please check [this reference guide](https://github.com/a11n/android-lint). If you want to write your own custom Lint rule you could use [this custom Lint rule template](https://github.com/a11n/CustomLintRules). If you are still looking for more details you are welcome to check [this workshop on custom Lint rules](https://github.com/a11n/lint-workshop-slides).

## Motivation
Fortunately, with the release of the **lint-tests** library **version 24.3.0** at the end of July 2015 [1] there is now official test support available for custom Lint rules. Unfortunately there are still some drawbacks:

1. You have to use the **JUnit 3** approach of extending `LintDetectorTest`.
2. The assertion options are poor, all you get is one `String`.
3. There is an issue when loading your test resources [2].

Therefore the objective of this library is to take you one step further:

1. It provides **JUnit 4** support by wrapping the official test support into a dedicated JUnit rule.
2. Furthermore it exposes the *internal* Lint `Warnings` for you which allows powerful assertions.
3. Last but not least it fixes the aforementioned issue for you which gives you a convenient way to place your test resources in *test/res*.

## Usage
Import the dependency in your `build.gradle`. It's available via JCenter:

```groovy
dependencies{
  ...
  testCompile 'de.ad:lint-junit-rule:0.3.0'
  
  //optional
  testCompile 'de.ad:lint-assertj:0.3.0'
  testCompile 'de.ad:lint-hamcrest:0.3.0'
}
```
Apply the rule in your test class and specify the `Detector` as well as the `Issues` under test. Next provide the files to analyze. Finally do your assertions on the populated `Warnings`:

```java
  @Rule public Lint lint = new Lint(new MyCustomRule(), MyCustomRule.ISSUE);

  @Test
  public void test() throws Exception {
    List<Warning> lintResult = lint.files("AndroidManifest.xml", "res/values/string.xml");

    //AssertJ
    assertThat(lintResult)
      .hasWarnings(2)
      .in("AndroidManifest.xml", "string.xml")
      .atLine(8, 14)
      .withMessage("MyCustomRule warning message.",
                   "MyCustomRule warning message.");

    //Hamcrest
    assertThat(lintResult,
                hasWarnings(
                  in("AndroidManifest.xml", "string.xml"),
                  atLine(8, 14),
                  withMessage("MyCustomRule warning message.",
                              "MyCustomRule warning message.")));
  }
```
**Note:** Test resources are looked-up in `test/res`.

## References
1. https://bintray.com/android/android-tools/com.android.tools.lint.lint-tests/24.3.0/view
2. https://code.google.com/p/android/issues/detail?id=182195
