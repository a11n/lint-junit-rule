# lint-junit-rule [![Build Status](https://travis-ci.org/a11n/lint-junit-rule.svg)](https://travis-ci.org/a11n/lint-junit-rule) [![Coverage Status](https://coveralls.io/repos/a11n/lint-junit-rule/badge.svg)](https://coveralls.io/r/a11n/lint-junit-rule) [ ![Download](https://api.bintray.com/packages/a11n/maven/com.ad.android.tools.lint/images/download.svg) ](https://bintray.com/a11n/maven/com.ad.android.tools.lint/_latestVersion)
A JUnit rule which allows unit testing of custom Lint rules.

## Motivation
Writing custom Lint rules basically has two challenges:

1. Find documentation on how to write custom Lint rules
2. Test your custom Lint rules

I have found no sufficient sources on how to test custom Lint rules. There is one source from Google [1] which mentions unit testing custom Lint rules is easy by just extending `AbstractCheckTest`. Well, the truth is that a lot of internal dependencies exist on that class [2], which makes it somehow impossible to use this class. This is also the conlusion of a second source you will find when researching on how to test custom Lint rules [3].

This is why **Lint JUnit rule** might be what you are looking for.

## Usage
Import the dependency in your build.gradle:
```groovy
dependencies{
  testCompile 'com.android.tools.lint:lint:24.2.3'
  testCompile 'com.ad.android.tools.lint:lint-junit-rule:0.1.3'
}
```
Apply the rule in your test class, specify the files to analyze and the rules to apply and finally do your assertions on the populated warnings:
```java
  @Rule public Lint lint = new Lint();
   
  @Test
  public void test() throws Exception {
    lint.setFiles("AndroidManifest.xml", "res/values/string.xml");
    lint.setIssues(MyCustomRule.ISSUE);
    
    lint.analyze();

    List<Warning> warnings = lint.getWarnings();
    
    assertThat(warnings).hasSize(2);
  }
```
## Example
Please find a complete example of how to integrate **lint-junit-rule** into a custom Lint rule project [here](https://github.com/a11n/AndroidLintPlaceholderCheck).

## References
1. http://tools.android.com/tips/lint/writing-a-lint-check
2. https://android.googlesource.com/platform/tools/base/+/master/lint/cli/src/test/java/com/android/tools/lint/checks/AbstractCheckTest.java
3. https://engineering.linkedin.com/android/writing-custom-lint-checks-gradle
