// compileopts.sbt: shared scalac/javac options.
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

import CrossVersion.partialVersion

scalacOptions in ThisBuild ++= Seq(
  "-encoding", "UTF-8", "-deprecation", "-unchecked",
  "-Xlint")

scalacOptions in ThisBuild ++= {
  partialVersion(scalaVersion.value) match {
    case Some((2, 9)) => Seq("-Ydependent-method-types")
    case _ =>
      Seq("-Yno-adapted-args", "-feature", "-language",
          "-language:implicitConversions", "-language:higherKinds",
          "-language:existentials", "-language:postfixOps")
  }
}

scalacOptions in ThisBuild ++= {
  import scala.math.Ordering.Implicits.infixOrderingOps
  partialVersion(scalaVersion.value) match {
    case Some((2, 10)) => Seq("-Xdivergence211")
    case Some(x) if x >= ((2, 11)) =>
      Seq("-Ywarn-unused", "-Ywarn-unused-import", "-Ydelambdafy:method")
    case _ => Seq.empty[String]
  }
}

javacOptions in (Compile, compile) in ThisBuild ++=
  Seq("-encoding", "UTF-8", "-Xlint", "-Werror")
