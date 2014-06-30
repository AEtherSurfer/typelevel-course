package typelevelcourse.shapeless.answers

import shapeless._, ops.hlist.Prepend

object HL {
  val lista = 1 :: "Something" :: HNil
  val listb = "Something else" :: 2 :: HNil

  val listab = lista ++ listb

  val chosen = listab.select[String]
  // could not find implicit value for parameter selector...
  // val missing = listab.select[List[String]]

  def appendHL[A <: HList, B <: HList](a: A, b: B)
              (implicit prepend: Prepend[A, B]): prepend.Out =
    a ++ b

  def appendHL3[A <: HList, B <: HList, C <: HList, I <: HList](a: A, b: B, c: C)
               (implicit p1: Prepend.Aux[A, B, I],
                p2: Prepend[I, C]): p2.Out =
    a ++ b ++ c
}
