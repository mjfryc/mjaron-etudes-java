# mjaron-etudes-java

Common java code.

[![Java CI with Gradle](https://github.com/mjfryc/mjaron-etudes-java/actions/workflows/gradle.yml/badge.svg)](https://github.com/mjfryc/mjaron-etudes-java/actions/workflows/gradle.yml)
[![Publish package to GitHub Packages](https://github.com/mjfryc/mjaron-etudes-java/actions/workflows/gradle-publish.yml/badge.svg)](https://github.com/mjfryc/mjaron-etudes-java/actions/workflows/gradle-publish.yml)

![Etudes](other/Etudes.png)
_Pianists practice etudes, programmers also!_

## How to integrate with Gradle

* Download latest release
    * From [here](https://github.com/mjfryc/mjaron-etudes-java/releases)
    * To `[gradle's root directory]/libs/`
    * E.g: `my-project/libs/mjaron-etudes-java-0.0.11.jar`
* In any Gradle subproject which needs this library, put following content:
    * `implementation files(project.rootDir.absolutePath + '/libs/mjaron-etudes-java-0.0.11.jar')`
* Now import package and use it, e.g:
    * `import pl.mjaron.etudes.Obj;`

## Object utils

### Printing object list as a Markdown table

```
| name | legsCount |  lazy | topSpeed |
| ---- | --------- | ----- | -------- |
| John |         4 |  true |    35.24 |
|  Bob |         5 | false |     75.0 |
```

```java
final Cat cat = sampleCat();
final String table = Obj.asTable(List.of(cat, otherCat()), Cat.class);
System.out.println(table);
```

### Getting object values

```
{topSpeed=35.24, legsCount=4, lazy=true, name=John}
```

```java
final Cat cat = sampleCat();
var values = Obj.getFieldValues(cat);
System.out.println(values);
```
## Array utils

### Joining arrays, adding elements

```java
int[] a = new int[]{1, 2, 3};
int[] b = new int[]{6, 7, 5};
int[] c = Arr.add(a, b);
assertArrayEquals(c, new int[]{1, 2, 3, 6, 7, 5});
```

## Pair implementation

```java
Pair<Integer, String> pair = new Pair<>(5, "C");
assertEquals(5, pair.getKey());
assertEquals("C", pair.getValue());
```

## Resource (file) path utils

### Get extension(without a dot)

```java
assertEquals("txt", Path.extension("/my/path/to/file.txt"));
assertEquals("", Path.extension("/my/path/to/file"));
assertEquals("", Path.extension("/my/path/to/file."));
```

## String utils

```java
assertEquals(2, Str.charsCount("commit", 'm'));
assertEquals("String", Str.capitalize("string"));
assertEquals("  3", Str.padLeft("3", 3));
assertEquals("3  ", Str.padRight("3", 3));
```
## Time utils

```java
Timer t = new Timer();
doLongOperation();
System.out.println("Passed time: " + t.getMillis() + " milliseconds.");
```
