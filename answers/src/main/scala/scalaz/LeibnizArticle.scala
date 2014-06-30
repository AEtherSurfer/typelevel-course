package test

final case class XList[A](xs: List[A]) {
  def sum(implicit ev: A =:= Int): Int =
    xs.foldLeft(0)(_ + _)

  def sum2(implicit ev: A Leib Int): Int =
    ev.subst[List](xs).foldLeft(0)(_ + _)
}

sealed abstract class Leib[A, B] {
  def subst[F[_]](fa: F[A]): F[B]
}

object Leib {
  implicit def refl[A]: Leib[A, A] = new Leib[A, A] {
    override def subst[F[_]](fa: F[A]): F[A] = fa
  }

  def lift[F[_], A, B](ab: A Leib B): F[A] Leib F[B] =
    ab.subst[Lambda[X => (F[A] Leib F[X])]](Leib.refl[F[A]])

  import scalaz.Leibniz, Leibniz.===

  def toScalaz[A, B](ab: A Leib B): A === B =
    ab.subst[A === ?](Leibniz.refl)

  def toLeib[A, B](ab: A === B): A Leib B =
    ab.subst[A Leib ?](Leib.refl)
}
