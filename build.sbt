name := "TODO-name"

version := "0.1.0-SNAPSHOT"

organization := "com.nocandysw"

scalaVersion := "2.10.0"

scalacOptions ++= Seq("-deprecation", "-unchecked")

scalacOptions <++= scalaVersion map (sv =>
  if (sv startsWith "2.9") Seq.empty[String]
  else Seq("-feature", "-language"))

libraryDependencies ++= Seq(
    "org.scalaz" %% "scalaz-core" % "7.0.0-M7")

resolvers += ("Sonatype Nexus Releases"
  at "https://oss.sonatype.org/content/repositories/releases")

licenses := Seq("Apache License, Version 2.0"
		-> url("http://www.apache.org/licenses/LICENSE-2.0"))
