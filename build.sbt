name := "TODO-name"

version := "0.1.0-SNAPSHOT"

organization := "com.nocandysw"

description := "TODO-describe"

homepage := Some(url("http://www.example.com")) // TODO

scalaVersion := "2.10.3"

scalacOptions ++= Seq(
  "-encoding", "UTF-8", "-deprecation", "-unchecked")
// TODO consider -Xlint
// *or* -Ywarn-nullary-override -Ywarn-inaccessible -Ywarn-adapted-args

scalacOptions ++= {
  if (scalaVersion.value startsWith "2.9") Seq.empty[String]
  else Seq("-feature", "-language")
}

javacOptions in (Compile, compile) ++=
  Seq("-encoding", "UTF-8", "-Xlint")

libraryDependencies ++= Seq(
    "org.scalaz" %% "scalaz-core" % "7.0.3",
    "org.scalaz" %% "scalaz-scalacheck-binding" % "7.0.3" % "test",
    "org.scalacheck" %% "scalacheck" % "1.10.1" % "test")

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
