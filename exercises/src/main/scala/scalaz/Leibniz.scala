package typelevelcourse.scalaz

import scalaz.Id.Id

/** Simpler version of [[scalaz.Leibniz]]. */
sealed abstract class Leib[A, B] extends (A => B) {
  def subst[F[_]](fa: F[A]): F[B]

  // Derived combinators
  final override def apply(a: A): B = subst[Id](a)

  final def lift[F[_]]: F[A] Leib F[B] =
    subst[Lambda[X => F[A] Leib F[X]]](Leib.refl)

  // Define symm and trans.

  /** Leib is symmetric. */
  final def symm: B Leib A = ???

  /** Leib is transitive. */
  final def trans[C](bc: B Leib C): A Leib C = ???
}

object Leib {
  // Define this.

  /** Leib is reflexive. */
  implicit def refl[A]: A Leib A = new (A Leib A) {
    override def subst[F[_]](fa: F[A]) = ???
  }
}
