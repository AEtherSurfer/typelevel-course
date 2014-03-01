// build.sbt: A friendly build for an sbt project.
// Copyright (C) 2012-2014 Stephen Compall
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import org.scalastyle.sbt.{PluginKeys => SS}

name := "TODO-name"

version := "0.1.0-SNAPSHOT"

organization := "com.nocandysw"

description := "TODO-describe"

homepage := Some(url("http://www.example.com")) // TODO

scalaVersion := "2.10.3"

val scalazVersion = settingKey[String]("Version of scalaz to use.")

scalacOptions ++= Seq(
  "-encoding", "UTF-8", "-deprecation", "-unchecked",
  "-Xfatal-warnings")
// TODO consider -Xlint
// *or* -Ywarn-nullary-override -Ywarn-inaccessible
// -Yno-imports -Yno-predef are also interesting

scalacOptions ++= {
  if (scalaVersion.value startsWith "2.9") Seq("-Ydependent-method-types")
  else Seq("-Yno-adapted-args", "-feature", "-language",
           "-language:implicitConversions", "-language:higherKinds",
           "-language:existentials", "-language:postfixOps")
}

scalacOptions ++= {
  val sv = scalaVersion.value
  if (sv startsWith "2.10") Seq("-Xdivergence211")
  else if (!(sv startsWith "2.9"))
    Seq("-Ywarn-unused", "-Ywarn-unused-import", "-Ydelambdafy:method")
  else Seq.empty[String]
}

javacOptions in (Compile, compile) ++=
  Seq("-encoding", "UTF-8", "-Xlint", "-Werror")

scalazVersion := "7.0.5"

libraryDependencies ++= Seq(
    "org.scalaz" %% "scalaz-core" % scalazVersion.value,
    "org.scalaz" %% "scalaz-scalacheck-binding" % scalazVersion.value % "test"
)

org.scalastyle.sbt.ScalastylePlugin.Settings

SS.failOnError := true

resolvers += ("Sonatype Nexus Releases"
  at "https://oss.sonatype.org/content/repositories/releases")

licenses := Seq("Apache License, Version 2.0"
		-> url("http://www.apache.org/licenses/LICENSE-2.0"))

publishMavenStyle := true

publishTo := {
  val ossrh = "https://oss.sonatype.org/"
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at ossrh + "content/repositories/snapshots")
  else
    Some("releases"  at ossrh + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

// TODO fix URLs below
pomExtra := (
  <parent>
    <groupId>org.sonatype.oss</groupId>
    <artifactId>oss-parent</artifactId>
    <version>7</version>
  </parent>
  <scm>
    <url>https://code.launchpad.net/~scompall/sbt-adhoc-subprojects/trunk</url>
    <connection>scm:bazaar:http://bazaar.launchpad.net/~scompall/sbt-adhoc-subprojects/trunk/</connection>
  </scm>
  <developers>
    <developer>
      <id>S11001001</id>
      <name>Stephen Compall</name>
      <url>https://launchpad.net/~scompall</url>
    </developer>
  </developers>)
