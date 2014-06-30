package typelevelcourse.scalaz.answers

import java.net.URI

import scalaz.{\/, Applicative, Foldable, Functor, IList, State, Traverse}

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

// Write the traversal for this.
final case class IntAndA[A](i: Int, a: A)

object IntAndA {
  implicit val intandaCovariant: Traverse[IntAndA] = new Traverse[IntAndA] {
    def traverseImpl[G[_], A, B](fa: IntAndA[A])(f: A => G[B])
                    (implicit G: Applicative[G]) =
      G.map(f(fa.a))(IntAndA(fa.i, _))
  }
}

final case class Doc[A](paras: IList[Para[A]])

final case class Para[A](elts: IList[String \/ A])

object Doc {
  implicit val docCovariant: Traverse[Doc] = new Traverse[Doc] {
    override def map[A, B](fa: Doc[A])(f: A => B) =
      Doc(fa.paras.map(para => Functor[Para].map(para)(f)))

    def traverseImpl[G[_], A, B](fa: Doc[A])(f: A => G[B])
                    (implicit G: Applicative[G]) =
      G.map(Traverse[IList].traverse(fa.paras)(para =>
              Traverse[Para].traverse(para)(f)))(Doc.apply)
  }
}

object Para {
  implicit val paraCovariant: Traverse[Para] = new Traverse[Para] {
    override def map[A, B](fa: Para[A])(f: A => B) =
      Para(fa.elts.map(elt => elt.map(f)))

    def traverseImpl[G[_], A, B](fa: Para[A])(f: A => G[B])
                    (implicit G: Applicative[G]) =
      G.map(Traverse[IList].traverse(fa.elts)(elt =>
        Traverse[String \/ ?].traverse(elt)(f)))(Para.apply)
  }

  def urls[A](p: Para[A]): List[A] = Foldable[Para].toList(p)

  def getURIContents(u: URI): String = ???

  def extractAndFetch(p: Para[URI]): State[Map[URI, String], Para[String]] =
    Traverse[Para].traverseU(p){uri =>
      State{m: Map[URI, String] =>
        m get uri map ((m, _)) getOrElse {
          val uc = getURIContents(uri)
          (m updated (uri, uc), uc)
        }
      }
    }

  // Then, update extractAndFetch to work with arbitrary Traverse[F],
  // not just Para.
}
