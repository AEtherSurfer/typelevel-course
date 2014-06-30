package typelevelcourse.shapeless

import shapeless._, ops.hlist.Prepend

object HL {
  // Define a couple of hlists

  // Add them together with ++

  // Which value do you get when you ".select[X]" for some X type that
  // appears twice?  What about one that doesn't appear?

  // implement it.  You'll have to add implicit evidence and a result
  // type
  def appendHL[A <: HList, B <: HList](a: A, b: B) = ???

  // implement it.  You'll have to add implicit evidence, another type
  // param, and a result type
  def appendHL3[A <: HList, B <: HList, C <: HList, I <: HList]
    (a: A, b: B, c: C) = ???
}
