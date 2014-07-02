package typelevelcourse.shapeless.answers

import shapeless._, ops.hlist.Selector

// Some types representing configuration.
trait ConfA
trait ConfB
trait ConfC

final case class ==>[L <: HList, A](run: L => A) {
  def flatMap[S <: HList, B](f: A => (S ==> B))
             (implicit ev: Merge[L, S]): ev.Out ==> B =
    ==>(e => f(run(ev extractLeft e)).run(ev extractRight e))

  def map[B](f: A => B): L ==> B = ==>(f compose run)
}

trait Merge[L <: HList, S <: HList] {
  type Out <: HList
  def extractLeft(o: Out): L
  def extractRight(o: Out): S
}

object Merge extends MergeInstances {
  // The vacuous, base case.
  implicit def likewise[L <: HList]: Aux[L, L, L] = new Merge[L, L] {
    type Out = L
    def extractLeft(o: Out): L = o
    def extractRight(o: Out): L = o
  }
}

sealed abstract class MergeInstances extends MergeInstances0 {
  // Nonvacuous base case 1: right is empty.
  implicit def lid[L <: HList]: Aux[L, HNil, L] = new Merge[L, HNil] {
    type Out = L
    def extractLeft(o: Out): L = o
    def extractRight(o: Out): HNil = HNil
  }

  // Nonvacuous base case 2: left is empty.
  implicit def rid[S <: HList]: Aux[HNil, S, S] = new Merge[HNil, S] {
    type Out = S
    def extractLeft(o: Out): HNil = HNil
    def extractRight(o: Out): S = o
  }
}

sealed abstract class MergeInstances0 extends MergeInstances1 {
  // The case where H is a member of the left and right.  Drop it from
  // the right and recur.
  implicit def dropLeft[L <: HList, H, T <: HList]
    (implicit sel: Selector.Aux[L, H], rec: Merge[L, T])
      : Aux[L, H :: T, rec.Out] = new Merge[L, H :: T] {
    type Out = rec.Out
    def extractLeft(o: Out): L = rec extractLeft o
    def extractRight(o: Out): H :: T = 
      sel(extractLeft(o)) :: (rec extractRight o)
  }
}

sealed abstract class MergeInstances1 {
  type Aux[L <: HList, S <: HList, Out0 <: HList] =
    Merge[L, S] { type Out = Out0 }

  // The case where H is *not* a member of the left and right.  Cons
  // it on the left and recur.
  implicit def succ[L <: HList, H, T <: HList]
    (implicit rec: Merge[L, T])
      : Aux[L, H :: T, H :: rec.Out] = new Merge[L, H :: T] {
    type Out = H :: rec.Out
    def extractLeft(o: Out): L = rec extractLeft o.tail
    def extractRight(o: Out): H :: T = 
      o.head :: (rec extractRight o.tail)
  }
}

object Modules {
  def step1: (ConfA :: HNil) ==> Int = ???

  def step2: (ConfB :: ConfC :: HNil) ==> String = ???

  def step3(from1: Int): HNil ==> List[Int] = ???

  def steps = for {
    o <- step1
    t <- step2
    xs <- step3(o)
  } yield (o, t, xs)

  // example merge
  steps : (ConfB :: ConfC :: ConfA :: HNil) ==> (Int, String, List[Int])

  // hnil hnil
  def ex2 = step3(1) flatMap (a => step3(2))
  ex2 : HNil ==> List[Int]

  // something hnil
  def ex3 = step1 flatMap (_ => step3(3))
  ex3 : (ConfA :: HNil) ==> List[Int]

  // hnil something
  def ex4 = step3(1) flatMap (_ => step2)
  ex4 : (ConfB :: ConfC :: HNil) ==> String
}
