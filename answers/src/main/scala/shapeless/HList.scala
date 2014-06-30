package com.nocandysw.typelevelcourse.shapeless.answers

import shapeless._

object HL {
  val lista = 1 :: "Something" :: HNil
  val listb = "Something else" :: 2 :: HNil

  val listab = lista ++ listb

  val chosen = listab.select[String]
  // could not find implicit value for parameter selector...
  // val missing = listab.select[List[String]]
}
