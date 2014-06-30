package com.nocandysw.typelevelcourse.scalaz

import scalaz.Functor

// Add a phantom tparam to this class.
sealed abstract class IntOrString
final case class IOSInt(x: Int) extends IntOrString
final case class IOSString(x: String) extends IntOrString

object IntOrString {
  // Then uncomment and implement this, following the types and laws.
  // implicit val intOrStringCovariant: Functor[IntOrString] = ???
}
