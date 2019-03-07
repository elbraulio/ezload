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

# Bots

We are using several bots, this is the current list:

| name                                                         | description                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [Release Drafter](https://github.com/toolmantim/release-drafter) | Drafts your next release notes as pull requests are merged into master. Built with [Probot](https://github.com/probot/probot). |
| [wip](https://github.com/wip/app)                            | By default, WIP is setting a pull request status to pending if it finds one of the following terms in the pull request titles: wip, work in progress, 🚧.<br />The pending status can be overwritten by adding `@wip ready for review` to the pull request body. |
| [Stale](https://github.com/probot/stale)                     | A GitHub App built with [Probot](https://github.com/probot/probot) that closes abandoned Issues and Pull Requests after a period of inactivity. |