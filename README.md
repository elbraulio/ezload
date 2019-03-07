[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/elbraulio/ezload/blob/master/LICENSE) [![Build Status](https://travis-ci.org/elbraulio/ezload.svg?branch=master)](https://travis-ci.org/elbraulio/ezload) [![](https://jitpack.io/v/com.elbraulio/ezload.svg)](https://jitpack.io/#com.elbraulio/ezload) [![](https://img.shields.io/badge/javadocs-ok-green.svg)](https://jitpack.io/com/elbraulio/ezload/latest/javadoc/) [![codecov](https://codecov.io/gh/elbraulio/ezload/branch/master/graph/badge.svg)](https://codecov.io/gh/elbraulio/ezload) [![codebeat badge](https://codebeat.co/badges/de82b3a8-e191-4a7e-8728-b829a4cf1484)](https://codebeat.co/projects/github-com-elbraulio-ezload-master)

# ezload

load data from formatted files to a relational data base.

# How to use

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