[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/elbraulio/ezload/blob/master/LICENSE) [![Build Status](https://travis-ci.org/elbraulio/ezload.svg?branch=master)](https://travis-ci.org/elbraulio/ezload) [![](https://jitpack.io/v/com.elbraulio/ezload.svg)](https://jitpack.io/#com.elbraulio/ezload/0.4.0) [![](https://img.shields.io/badge/javadocs-ok-green.svg)](https://jitpack.io/com/elbraulio/ezload/latest/javadoc/) [![codecov](https://codecov.io/gh/elbraulio/ezload/branch/master/graph/badge.svg)](https://codecov.io/gh/elbraulio/ezload) [![codebeat badge](https://codebeat.co/badges/de82b3a8-e191-4a7e-8728-b829a4cf1484)](https://codebeat.co/projects/github-com-elbraulio-ezload-master)

# ezload

Load, verify and use your line-formatted files easily.

# Install

**maven**

```xml
<dependencies>
    <dependency>
        <groupId>com.elbraulio</groupId>
        <artifactId>ezload</artifactId>
        <version>0.4.0</version>
    </dependency>
</dependencies>
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```

**gradle**

```groovy
dependencies {
        implementation 'com.elbraulio:ezload:0.4.0'
}
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```

# Review

Load and validate formatted files with *EzLoad* and *EzCol*. You only have to define the file format by setting:

- **For file**:
  - Separation expression
  - Number of columns

- **For each column**:
  - Position (starting at 0)
  - Name
  - Constraint

For instance, we have this csv:

```csv
2; Speed
3; Celcius
```

Then we can parse it like this:

```java
String separation = ";";
int numberOfCols = 2;
Parser parser = EzLoad.parse(separation, numberOfCols)
    .withCol(
        EzCol.integer(
            0, "value", new NoConstraint<>()
        )
    ).withCol(
        EzCol.string(
            1, "name", s -> !s.isEmpty()
        )
    ).parser();
```

Of course, a CSV may have **null values** and they must be defined as fixed text e.g. `null`, `NULL`, `none` or even `""` (empty string). See this example:

```csv
2; Speed
3; null
```

```java
EzLoad.parse(";", numberOfCols)
    .withCol(
        EzCol.integer(
            0, "value", new NoConstraint<>()
        )
    ).withCol(
        EzCol.nullable(
            "null",
            EzCol.integer(
                1, "name", s -> !s.isEmpty()
            )
    )
);
```

This notation allows **ezload** to check if a null value has been found in order to parse it as  `null` and not misread it as *String* in this case.

## EzInsert

Another important feature is the implementation of actions to process your CSV using the *Parser*. For instance, **ezload** provide *EzInsert* action. This allows you to insert data from file source to a database. Also, optionally, you can specify a size to insert data by chunks when your files are large.

```java
long insertedRows = EzInsert.fromParser(
   	connection, "table_name", parser, bufferedReader, chunkSize
);
```

To implement your own actions, keep reading and check the detailed explanation of them.

# How does it work?

As we know, a CSV is a file formatted by columns. So you have to define what those columns are. To do that you can use *EzCol*.

## EzCol

It defines the column's data type and constraint. You need to set the column format by providing its position on each CSV's line, name, constrain and optionally a way to transform the raw value.

```java
EzCol.integer(
    0, // column position
    "name", // column name
    i -> i > 0, // constraint
    Integer::parseInt // transform the raw string to a type
);
```

Currently, these are the supported columns types

|  Type  |      EzCol       |
| :----: | :--------------: |
|  int   | `EzCol.integer`  |
| double | `EzCol.doublee`  |
| String |  `EzCol.string`  |
|  null  | `EzCol.nullable` |

This allows **ezload** to check if a given column is right formatted. If it isn't, throws an *Exception* and gives you detailed information about what is wrong with the column.

## Parser

You can define a *Parser* to check and parse a CSV. To build a *Parser* you must define the expression that splits the CSV, the number of columns and the format for each column using *EzCol*.

```java
String separation = ";";
int numberOfCols = 2;
Parser parser = EzLoad.parse(separation, numberOfCols)
    .withCol(
        EzCol.integer(
            0, "value", new NoConstraint<>()
        )
    ).withCol(
        EzCol.string(
            1, "name", s -> !s.isEmpty()
        )
    ).parser();
```

Now you can parse a raw line using `parser.parse(rawLine)` and get a *Line* wich is a list of *Values* that can accept an *Action*. Check [InsertFromParser](https://github.com/elbraulio/ezload/blob/5e41c79e9431faad325fd55e32fb5cbdab337e7d/src/main/java/com/elbraulio/ezload/sql/InsertFromParser.java#L88) implementation:

```java
String raw;
while ((raw = bufferedReader.readLine()) != null) {
	Line parsedLine = this.parser.parse(raw);
	int index = 1;
	for (Value value : parsedLine.values()) {
  	value.accept(new AddPreparedStatement(psmt, index++));
  }
  // ...
}
```

## Actions

An *Action* make an operation over a *Value* depending on its type. [Here](https://github.com/elbraulio/ezload/blob/5e41c79e9431faad325fd55e32fb5cbdab337e7d/src/main/java/com/elbraulio/ezload/sql/InsertFromParser.java#L84) you can see an example:

```java
for (Value value : parsedLine.values()) {
        value.accept(new SomeAction(...));
}
```

This means an *Action* must define what to do for each supported type. You can see [here](https://github.com/elbraulio/ezload/blob/5e41c79e9431faad325fd55e32fb5cbdab337e7d/src/main/java/com/elbraulio/ezload/action/Action.java#L35) the *Action*'s interface with has a method for each supported type.

# Contribute

We are using several bots, this is the current list:

| name                                                         | description                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [Release Drafter](https://github.com/toolmantim/release-drafter) | Drafts your next release notes as pull requests are merged into master. Built with [Probot](https://github.com/probot/probot). |
| [wip](https://github.com/wip/app)                            | By default, WIP is setting a pull request status to pending if it finds one of the following terms in the pull request titles: wip, work in progress, 🚧.<br />The pending status can be overwritten by adding `@wip ready for review` to the pull request body. |
| [Stale](https://github.com/probot/stale)                     | A GitHub App built with [Probot](https://github.com/probot/probot) that closes abandoned Issues and Pull Requests after a period of inactivity. |
| [todo](https://github.com/JasonEtco/todo)                    | Using **todo** should be really simple. Once you've installed it in your repository, simply push some code (to your default branch, a PR; doesn't matter). If the code you pushed includes one of the configured keywords (defaults are `@todo` and `TODO`), then the integration will create a new issue for you using the comment your wrote in your code!<br/>To reduce noise and keep your **todo** notes in the right context, **todo** comments made in commits that are part of a pull request will be converted into comments on that pull request. When the PR is merged, **todo**will determine which of those **todo**s have yet to be resolved and open an appropriate issue. |

