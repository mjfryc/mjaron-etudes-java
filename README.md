# mjaron-etudes-java

Library for printing Java object lists as a Markdown-formatted table.
Utils compatible with Java 1.8.

[![Maven Central](https://img.shields.io/maven-central/v/io.github.mjfryc/mjaron-etudes-java?color=dark-green&style=flat)](https://search.maven.org/artifact/io.github.mjfryc/mjaron-etudes-java/)
[![Java CI with Gradle](https://github.com/mjfryc/mjaron-etudes-java/actions/workflows/gradle.yml/badge.svg)](https://github.com/mjfryc/mjaron-etudes-java/actions/workflows/gradle.yml)
[![Publish package to GitHub Packages](https://github.com/mjfryc/mjaron-etudes-java/actions/workflows/gradle-publish.yml/badge.svg)](https://github.com/mjfryc/mjaron-etudes-java/actions/workflows/gradle-publish.yml)

![Etudes](other/Etudes.png)
_Pianists practice etudes, programmers also!_

## How to integrate with Gradle

### From Maven Central

<https://search.maven.org/artifact/io.github.mjfryc/mjaron-etudes-java/0.2.0/jar>

```gradle
implementation 'io.github.mjfryc:mjaron-etudes-java:0.2.0'
```

### As local `jar` file

* Download the latest release
    * From [here](https://github.com/mjfryc/mjaron-etudes-java/releases)
    * To `[gradle's root directory]/libs/`
    * E.g: `my-project/libs/mjaron-etudes-java-0.2.0.jar`
* In any Gradle subproject which needs this library, put following content:
    * `implementation files(project.rootDir.absolutePath + '/libs/mjaron-etudes-java-0.2.0.jar')`
* Now import package and use it, e.g:
    * `import pl.mjaron.etudes.Obj;`

## Table generation utils

### Printing object list as a Markdown / CSV / custom table

```java
// Sample class.
// When generating the table, each instance of this class will be converted to single row.
class Cat {
    String name = "Tom";
    int age = 2;

    Cat() {
    }

    Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

class Sample {
    void test() {

        // Sample array or iterable of Java objects.
        final Cat[] cats = new Cat[]{new Cat(), new Cat("_Michael_", 5)};

        Table.render(cats, Cat.class).run();

        // Or
        final String catsTableString = Table.render(cats, Cat.class).runToString();
        System.out.println(catsTableString);

        // Verbose options demo
        Table.render(cats, Cat.class)
                .withCsvWriter()
                .withCsvEscaper() // Escape the special characters.
                .withAlignedColumnWidths(false) // Optionally force align / do not align column widths.
                .withCellDelimiter(',') // Optionally use the custom cell delimiter.
                .withLineBreakCRLF() // How the lines will be separated.

                // Where to save the table.
                .toFile("build/sample.csv")
                // By default, the System.out is used, which can be specified as:
                // .to(System.out)
                // or: .to(Stream|PrintStream|Appendable|File|StringBuilder out)

                // Run the render operation.
                .run(); // or: .runString() to create the String with whole table.
    }
}
```

Sample generated table:

```
| name | legsCount |  lazy | topSpeed |
| ---- | --------- | ----- | -------- |
| John |         4 |  true |    35.24 |
|  Bob |         5 | false |     75.0 |
```

### Table generation customization

#### Escaping markdown special characters:

```java
class Sample {
    void test() {
        Table.render(cats, Cat.class).withMarkdownEscaper().run();
    }
}
```

So now all special characters are escaped by HTML number syntax, there is following raw text:

    |              name | age |
    | ----------------- | --- |
    |               Tom |   2 |
    | &#95;Michael&#95; |   5 |

Rendered by Markdown as:

|              name | age |
| ----------------- | --- |
|               Tom |   2 |
| &#95;Michael&#95; |   5 |

Without a `MarkdownEscaper`, cells will be rendered 'as is', which potentially can cause a bad rendering:

```java
class Sample {
    void test() {
        Table.render(cats, Cat.class);
    }
}
```

|      name | age |
| --------- | --- |
|       Tom |   2 |
| _Michael_ |   5 |

#### Markdown custom escaper - @TODO

@TODO Implement escaper which escapes only when preceded by (eg.) backslash `&#92;`.

### Escaping CSV characters

## Object utils

### Getting object values

```
{topSpeed=35.24, legsCount=4, lazy=true, name=John}
```

```java
class Sample {
    void test() {
        Cat cat = new Cat();
        Map<String, Object> values = Obj.getFieldValues(cat);
        System.out.println(values);
    }
}
```

## Array utils

### Joining arrays, adding elements

```java
class Sample {
    void test() {
        int[] a = new int[]{1, 2, 3};
        int[] b = new int[]{6, 7, 5};
        int[] c = Arr.add(a, b);
        assertArrayEquals(c, new int[]{1, 2, 3, 6, 7, 5});
    }
}
```

## Pair implementation

```java
class Sample {
    void test() {
        Pair<Integer, String> pair = new Pair<>(5, "C");
        assertEquals(5, pair.getKey());
        assertEquals("C", pair.getValue());
    }
}
```

## Resource (file) path utils

### Get extension(without a dot)

```java
class Sample {
    void test() {
        assertEquals("txt", Path.extension("/my/path/to/file.txt"));
        assertEquals("", Path.extension("/my/path/to/file"));
        assertEquals("", Path.extension("/my/path/to/file."));
    }
}
```

## String utils

```java
class Sample {
    void test() {
        assertEquals(2, Str.charsCount("commit", 'm'));
        assertEquals("String", Str.capitalize("string"));
        assertEquals("  3", Str.padLeft("3", 3));
        assertEquals("3  ", Str.padRight("3", 3));
    }
}
```

## Time utils

```java
class Sample {
    void test() {
        Timer t = new Timer();
        doLongOperation();
        System.out.println("Passed time: " + t.getMillis() + " milliseconds.");
    }
}
```
