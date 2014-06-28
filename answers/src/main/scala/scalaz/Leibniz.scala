package com.nocandysw.typelevelcourse.scalaz

import scalaz.Id.Id

/** Simpler version of [[scalaz.Leibniz]]. */
sealed abstract class ===[A, B] extends (A => B) {
  def subst[F[_]](fa: F[A]): F[B]

  // Derived combinators
  final override def apply(a: A): B = subst[Id](a)

  /** === is symmetric. */
  final def symm: (B === A) = ???

  /** === is transitive. */
  final def trans[C](bc: (B === C)): (A === C) = ???
}

object === {
  /** === is reflexive. */
  implicit def refl[A]: (A === A) = new (A === A) {
    override def subst[F[_]](fa: F[A]) = fa
  }
}
