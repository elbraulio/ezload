[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/elbraulio/ezload/blob/master/LICENSE) [![Build Status](https://travis-ci.org/elbraulio/ezload.svg?branch=master)](https://travis-ci.org/elbraulio/ezload) [![](https://jitpack.io/v/com.elbraulio/ezload.svg)](https://jitpack.io/#com.elbraulio/ezload) [![](https://img.shields.io/badge/javadocs-ok-green.svg)](https://jitpack.io/com/elbraulio/ezload/latest/javadoc/) [![codecov](https://codecov.io/gh/elbraulio/ezload/branch/master/graph/badge.svg)](https://codecov.io/gh/elbraulio/ezload) [![codebeat badge](https://codebeat.co/badges/de82b3a8-e191-4a7e-8728-b829a4cf1484)](https://codebeat.co/projects/github-com-elbraulio-ezload-master)

# ezload

load data from formatted files to a relational data base.

# Install

#### maven

```xml
<dependencies>
    <dependency>
        <groupId>com.elbraulio</groupId>
        <artifactId>ezload</artifactId>
        <version>{version}</version>
    </dependency>
</dependencies>
<!-- for elbraulio's tools -->
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```

#### gradle

```groovy
dependencies {
        implementation 'com.elbraulio:ezload:{version}'
}
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```

# How to use

To load your data, you can use **EzLoad** and **EzCol** to set the file format. To do that you have to define each column of the file including the separation expression like this:

```java
Parser parser = EzLoad.parse(",", numberOfCols)
    .withCol(
        EzCol.integer(
            0, "units", new NoConstrain<>(), new ToInt()
        )
    ).withCol(
        EzCol.string(
            1, "names", new NoConstrain<>(), new ToString()
        )
).parser();
```

The final argument `ToString` and `ToInt` is included to make possible to add more complex transformations, these are the default ones. For instance, you can make a decorator where you take the value plus 100 from a number:

```java
class PlusHundred implements Transform<Integer> {
    private final Transform<Integer> origin;
    PlusHundred(Transform<Integer> origin) {
        this.origin = origin;
    }
    @Override
    public int from(String value) {
        return 100 + this.origin.from(value);
    }
}

EzCol.integer(
	0, "units", new NoConstrain<>(), new PlusHundred(new ToInt())
);
```

Now you can insert this using **EzInsert**. This allow you to insert from source to a data base like your are using the `executeBatch()`.

```java
int[] batchResult = EzInsert.fromParser(
   	connection, "table_name", parser, bufferedReader
);
```

# Bots

We are using several bots, this is the current list:

| name                                                         | description                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [Release Drafter](https://github.com/toolmantim/release-drafter) | Drafts your next release notes as pull requests are merged into master. Built with [Probot](https://github.com/probot/probot). |
| [wip](https://github.com/wip/app)                            | By default, WIP is setting a pull request status to pending if it finds one of the following terms in the pull request titles: wip, work in progress, 🚧.<br />The pending status can be overwritten by adding `@wip ready for review` to the pull request body. |
| [Stale](https://github.com/probot/stale)                     | A GitHub App built with [Probot](https://github.com/probot/probot) that closes abandoned Issues and Pull Requests after a period of inactivity. |
| [todo](https://github.com/JasonEtco/todo)                    | Using **todo** should be really simple. Once you've installed it in your repository, simply push some code (to your default branch, a PR; doesn't matter). If the code you pushed includes one of the configured keywords (defaults are `@todo` and `TODO`), then the integration will create a new issue for you using the comment your wrote in your code!<br/>To reduce noise and keep your **todo** notes in the right context, **todo** comments made in commits that are part of a pull request will be converted into comments on that pull request. When the PR is merged, **todo**will determine which of those **todo**s have yet to be resolved and open an appropriate issue. |

