package com.nocandysw.typelevelcourse.scalaz.answers

import scalaz.Functor

sealed abstract class IntOrString[A]
final case class IOSInt[A](x: Int) extends IntOrString[A]
final case class IOSString[A](x: String) extends IntOrString[A]

object IntOrString {
  implicit val intOrStringCovariant: Functor[IntOrString] =
    new Functor[IntOrString] {
      def map[A, B](fa: IntOrString[A])(f: A => B): IntOrString[B] =
        fa match {
          case IOSInt(x) => IOSInt(x)
          case IOSString(x) => IOSString(x)
        }
    }
}
