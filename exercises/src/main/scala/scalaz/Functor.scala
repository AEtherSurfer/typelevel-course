package com.nocandysw.typelevelcourse.scalaz

import java.net.URI

import scalaz.{\/, Applicative, Functor, IList, Traverse}

// Add a phantom tparam to this class.
sealed abstract class IntOrString
final case class IOSInt(x: Int) extends IntOrString
final case class IOSString(x: String) extends IntOrString

object IntOrString {
  // Then uncomment and implement this, following the types and laws.
  // implicit val intOrStringCovariant: Functor[IntOrString] = ???
}

// Write the Functor, Foldable, Traverse for this.
final case class IntAndA[A](i: Int, a: A)

object IntAndA {
  implicit val intandaCovariant: Functor[IntAndA] = ???
}

// Add a type parameter to these to replace URI.
final case class Doc(paras: IList[Para])

final case class Para(elts: IList[String \/ URI])

object Doc {
  // Implement this: Functor, Foldable, Traverse
  // implicit val docCovariant: Functor[Doc] = ???
}

object Para {
  // And this
  // implicit val paraCovariant: Functor[Para] = ???
}
