name := "interp-mixed"

version := "0.1.0-SNAPSHOT"

organization := "com.nocandysw"

scalaVersion := "2.10.0-M6"

scalacOptions ++= Seq("-deprecation", "-unchecked")

scalacOptions <++= scalaVersion map (sv =>
  if (sv startsWith "2.9") Seq.empty[String]
  else Seq("-feature", "-language",
	   "-language:higherKinds",
	   "-language:implicitConversions"))

// XXX %% extends with "_2.10" as of writing; when that's right, go
// back to that
libraryDependencies <++= scalaVersion(sv => Seq(
    "org.scalaz" % ("scalaz_" + sv) % "7.0.0-M2",
    "org.scalaz" % ("scalaz-typelevel_" + sv) % "7.0.0-M2"))

licenses := Seq("Apache License, Version 2.0"
		-> url("http://www.apache.org/licenses/LICENSE-2.0"))
