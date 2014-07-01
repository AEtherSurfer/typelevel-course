package typelevelcourse.shapeless

import shapeless._, ops.hlist.Selector

// Some types representing configuration.
trait ConfA
trait ConfB
trait ConfC

final case class ==>[L <: HList, A](run: L => A) {
  // Add an appropriate return type and evidence.
  def flatMap[S <: HList, B](f: A => (S ==> B)) =
    ???

  def map[B](f: A => B): L ==> B = ==>(f compose run)
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

  // Uncomment these to test your evidence lookup.

  // example merge
  // steps : (ConfB :: ConfC :: ConfA :: HNil) ==> (Int, String, List[Int])

  // hnil hnil
  // def ex2 = step3(1) flatMap (a => step3(2))
  // ex2 : HNil ==> List[Int]

  // something hnil
  // def ex3 = step1 flatMap (_ => step3(3))
  // ex3 : (ConfA :: HNil) ==> List[Int]

  // hnil something
  // def ex4 = step3(1) flatMap (_ => step2)
  // ex4 : (ConfB :: ConfC :: HNil) ==> String
}
