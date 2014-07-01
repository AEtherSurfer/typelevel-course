package typelevelcourse.spire.answers

import spire.math.Integral
import spire.implicits._

object Factor {
  private[this]
  def factorOdds[A: Integral](i: A, p: A, xs: List[A]): List[A] =
    if (p >= i) i :: xs
    else if (i % p === 0) factorOdds(i /~ p, p, p :: xs)
    else factorOdds(i, p + 2, xs)

  private[this]
  def factorEvens[A: Integral](i: A, xs: List[A]): (A, List[A]) =
    if (i % 2 === 0) factorEvens(i /~ 2, 2 :: xs)
    else (i, xs)

  def factor[A: Integral](i: A): List[A] = {
    val (i2, ev) = factorEvens(i, Nil)
    factorOdds(i2, 3, ev)
  }
}
