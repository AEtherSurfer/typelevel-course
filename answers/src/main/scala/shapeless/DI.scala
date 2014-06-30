package typelevelcourse.shapeless.answers

import shapeless._

// Some types representing configuration.
trait ConfA
trait ConfB
trait ConfC

final case class ==>[L <: HList, A](run: L => A) {
  def flatMap[S <: HList, B](f: A => (S ==> B))
             (implicit ev: Merge[L, S]): ev.Out ==> B =
    ???

  def map[B](f: A => B): L ==> B = ==>(f compose run)
}

trait Merge[L <: HList, S <: HList] extends DepFn2[L, S] {
  type Out <: HList
  def extractLeft(o: Out): L
  def extractRight(o: Out): S
}

sealed abstract class MergeInstances {
  type Aux[L <: HList, S <: HList, Out0 <: HList] =
    Merge[L, S] { type Out = Out0 }
}

object Merge extends MergeInstances {
  implicit def lid[L <: HList]: Aux[L, HNil, L] = new Merge[L, HNil] {
    type Out = L
    def extractLeft(o: Out): L = o
    def extractRight(o: Out): HNil = HNil
    def apply(l: L, s: HNil): Out = l
  }

  implicit def rid[S <: HList]: Aux[HNil, S, S] = new Merge[HNil, S] {
    type Out = S
    def extractLeft(o: Out): HNil = HNil
    def extractRight(o: Out): S = o
    def apply(l: HNil, s: S): Out = s
  }
}

object Modules {
  def step1: (ConfA :: HNil) ==> Int = ???

  def step2: (ConfB :: ConfC :: HNil) ==> String = ???

  def step3(from1: Int): HNil ==> List[Int] = ???

/*
  def steps = for {
    o <- step1
    t <- step2
    xs <- step3(o)
  } yield (o, t, xs)
 */
}
