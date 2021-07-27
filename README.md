# mjaron-etudes-java

Common java code.

## Printing object list as a table

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

## Getting object values

```
{topSpeed=35.24, legsCount=4, lazy=true, name=John}
```

```java
final Cat cat = sampleCat();
var values = Obj.getFieldValues(cat);
System.out.println(values);
```
