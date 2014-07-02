package typelevelcourse.monocle.answers

import scalaz.{Applicative, Traverse}
import monocle.Traversal

import typelevelcourse.scalaz.answers.IntAndA

final case class Twin[A](x: A, y: A)

final case class TwinInt(pair: IntAndA[Int])

object Twin {
  implicit def twinTraversal[A, B]: Traversal[Twin[A], Twin[B], A, B] =
    new Traversal[Twin[A], Twin[B], A, B] {
      def multiLift[F[_]: Applicative](from: Twin[A], f: A => F[B])
          : F[Twin[B]] = twinCovariant.traverse(from)(f)
    }

  implicit val twinCovariant: Traverse[Twin] = new Traverse[Twin] {
    def traverseImpl[G[_]: Applicative, A, B]
      (fa: Twin[A])(f: A => G[B]): G[Twin[B]] =
      Applicative[G].apply2(f(fa.x), f(fa.y))(Twin.apply)
  }
}

object TwinInt {
  implicit val twinIntTraversal: Traversal[TwinInt, TwinInt, Int, Int] =
    new Traversal[TwinInt, TwinInt, Int, Int] {
      def multiLift[F[_]: Applicative](from: TwinInt, f: Int => F[Int])
          : F[TwinInt] =
        Applicative[F].apply2(f(from.pair.i), f(from.pair.a)){
          (i, a) => TwinInt(IntAndA(i, a))
        }
    }
}
