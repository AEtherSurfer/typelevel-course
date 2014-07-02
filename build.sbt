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

lazy val root = (project in file(".")).
  aggregate(exercises, answers)

lazy val exercises = project

lazy val answers = project

name := "TODO-name"

version in ThisBuild := "0.1.0-SNAPSHOT"

organization in ThisBuild := "com.nocandysw"

description := "TODO-describe"

licenses in ThisBuild :=
  Seq("Apache License, Version 2.0"
	-> url("http://www.apache.org/licenses/LICENSE-2.0"))

homepage in ThisBuild := Some(url("http://www.example.com")) // TODO

scalaVersion in ThisBuild := "2.11.1"

crossScalaVersions in ThisBuild := Seq("2.11.1", "2.10.4")

val scalazVersion = settingKey[String]("Version of scalaz to use.")

scalazVersion in ThisBuild := "7.0.6"

val monocleVersion = "0.4.0"

libraryDependencies in ThisBuild ++= Seq(
    "org.scalaz" %% "scalaz-core" % scalazVersion.value,
    "com.chuusai" %% "shapeless" % "2.0.0",
    "org.spire-math" %% "spire" % "0.7.5",
    "com.github.julien-truffaut" %% "monocle-core" % monocleVersion,
    "com.github.julien-truffaut" %% "monocle-generic" % monocleVersion,
    "com.github.julien-truffaut" %% "monocle-macro" % monocleVersion,
    "com.github.julien-truffaut" %% "monocle-law" % monocleVersion % "test",
    "commons-io" % "commons-io" % "2.4",
    "org.scalaz" %% "scalaz-scalacheck-binding" % scalazVersion.value % "test"
)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.5.2")

addCompilerPlugin("org.brianmckenna" %% "wartremover" % "0.10")

// reenable?
// scalacOptions in ThisBuild += "-P:wartremover:only-warn-traverser:org.brianmckenna.wartremover.warts.Unsafe"

org.scalastyle.sbt.ScalastylePlugin.Settings

SS.failOnError in ThisBuild := true

resolvers in ThisBuild ++= Seq(
  "Sonatype Nexus Releases"
    at "https://oss.sonatype.org/content/repositories/releases",
  "bintray/non" at "http://dl.bintray.com/non/maven"
)

publishMavenStyle in ThisBuild := true

publishTo in ThisBuild := {
  val ossrh = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at ossrh + "content/repositories/snapshots")
  else
    Some("releases"  at ossrh + "service/local/staging/deploy/maven2")
}

publishArtifact in Test in ThisBuild := false

pomIncludeRepository in ThisBuild := { _ => false }

// TODO fix URLs below
pomExtra in ThisBuild := (
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
