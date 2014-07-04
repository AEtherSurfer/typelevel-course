package typelevelcourse.spire

import spire.math.Integral
import spire.implicits._
import scala.annotation.tailrec

object Factor {
  // Implement this.
  def factor[@specialized (Int, Long) A: Integral](i: A): List[A] = {
    if (i === 1) return Nil
    def sqrt(n: A): A = {
      if ((n+1)*(n+1) > i) n
      else sqrt(n+1)
    }
    val max = sqrt(2)
    @tailrec
    def rec(n: A, r: A, fl: List[A]): List[A] = {
      if (n > max) {
        if (r === 1) fl
        else r :: fl
      } else {
        if (r % n === 0) rec(n, r /~ n , n :: fl)
        else rec(n + 1, r, fl)
      }
    }
    rec(2, i, Nil)
  }
}
