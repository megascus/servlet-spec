# Java Servlet API specification(日本語翻訳版)

これはServlet4.0仕様(Java EE8)のjavadocの日本語版を作成するプロジェクトです。
javaソースコードのjavadocを日本語に書き換えると自動ビルドされます。

成果物は以下から参照することが出来ます。(完了)

https://megascus.github.io/servlet-spec/docs/apidocs/




以下原文
----

Java Servlet API
================

Building
--------

Prerequisites:

* JDK8+
* Maven 3.0.3+

Run the build: 

`mvn install`

The build runs copyright check and generates the jar, sources-jar and javadoc-jar by default.

Checking findbugs
-----------------

`mvn -DskipTests -Dfindbugs.threshold=Low findbugs:findbugs`

