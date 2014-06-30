package com.nocandysw.typelevelcourse.scalaz.answers

import scalaz.Id.Id

/** Simpler version of [[scalaz.Leibniz]]. */
sealed abstract class Leib[A, B] extends (A => B) {
  def subst[F[_]](fa: F[A]): F[B]

  // Derived combinators
  final override def apply(a: A): B = subst[Id](a)

  /** Leib is symmetric. */
  final def symm: B Leib A =
    subst[? Leib A](Leib.refl)

  /** Leib is transitive. */
  final def trans[C](bc: B Leib C): A Leib C =
    bc.subst[A Leib ?](this)
}

object Leib {
  /** Leib is reflexive. */
  implicit def refl[A]: A Leib A = new (A Leib A) {
    override def subst[F[_]](fa: F[A]) = fa
  }
}
