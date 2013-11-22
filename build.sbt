name := "TODO-name"

version := "0.1.0-SNAPSHOT"

organization := "com.nocandysw"

description := "TODO-describe"

homepage := Some(url("http://www.example.com")) // TODO

scalaVersion := "2.10.3"

val scalazVersion = settingKey[String]("Version of scalaz to use.")

val scalacheckVersion = settingKey[String]("Version of scalacheck to use.")

scalacOptions ++= Seq(
  "-encoding", "UTF-8", "-deprecation", "-unchecked")
// TODO consider -Xlint
// *or* -Ywarn-nullary-override -Ywarn-inaccessible -Ywarn-adapted-args

scalacOptions ++= {
  if (scalaVersion.value startsWith "2.9") Seq.empty[String]
  else Seq("-feature", "-language")
}

// Major, minor, and milestone.
def scalaVersionInfo(v: String): (Int, Int, Int, Option[Int]) =
  ("""^(\d+)\.(\d+)\.(\d+)(?:-M(\d+))?""".r findFirstMatchIn v
     map {case util.matching.Regex.Groups(maj, min, pat, mil) =>
       (maj.toInt, min.toInt, pat.toInt,
        Option(mil) map (_.toInt))}
     getOrElse (0, 0, 0, None))

scalacOptions ++= {
  import scala.math.Ordering.Implicits.infixOrderingOps
  val strictInf = scalaVersionInfo(scalaVersion.value) match {
    case (2, 11, 0, Some(mil)) => mil > 5
    case (maj, min, _, _) => (maj -> min) > (2 -> 10)
  }
  if (strictInf) Seq("-Xstrict-inference")
  else Seq.empty[String]
}

javacOptions in (Compile, compile) ++=
  Seq("-encoding", "UTF-8", "-Xlint")

scalazVersion := "7.0.4"

scalacheckVersion := {
  import scala.math.Ordering.Implicits.infixOrderingOps
  scalaVersionInfo(scalaVersion.value) match {
    case (maj, min, pat, _) if (maj, min, pat) < (2, 9, 3) => "1.10.1"
    case (2, 11, 0, Some(mil)) if mil < 7 => "1.10.1"
    case _ => "1.11.1"
  }
}

libraryDependencies ++= Seq(
    "org.scalaz" %% "scalaz-core" % scalazVersion.value,
    "org.scalaz" %% "scalaz-scalacheck-binding" % scalazVersion.value % "test",
    "org.scalacheck" %% "scalacheck" % scalacheckVersion.value % "test")

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
