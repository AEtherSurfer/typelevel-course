package typelevelcourse.monocle

import scalaz.{Applicative, Traverse}
import monocle.Traversal

import typelevelcourse.scalaz.IntAndA

final case class Twin[A](x: A, y: A)

final case class TwinInt(pair: IntAndA[Int])

object Twin {
  // Write the traversal for the parameter in Twin.
  implicit def twinTraversal[A, B]: Traversal[Twin[A], Twin[B], A, B] =
    new Traversal[Twin[A], Twin[B], A, B] {
      def multiLift[F[_]: Applicative](from: Twin[A], f: A => F[B])
          : F[Twin[B]] = ???
    }

  // Write a Traverse instance using the traversal.
  implicit val twinCovariant: Traverse[Twin] = new Traverse[Twin] {
    def traverseImpl[G[_]: Applicative, A, B]
      (fa: Twin[A])(f: A => G[B]): G[Twin[B]] =
      ???
  }

  // Now, flip the implementations around.
}

object TwinInt {
  // Implement this.
  implicit val twinIntTraversal: Traversal[TwinInt, TwinInt, Int, Int] =
    new Traversal[TwinInt, TwinInt, Int, Int] {
      def multiLift[F[_]: Applicative](from: TwinInt, f: Int => F[Int])
          : F[TwinInt] = ???
    }

  // Change the implementation of twinIntTraversal to not use the f
  // argument.  Can you do this in twinCovariant or twinTraversal?
}
