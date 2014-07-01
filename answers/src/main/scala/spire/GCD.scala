package typelevelcourse.spire.answers

import spire.math.Integral
import spire.implicits._

object GCD {
  // From an example by Tom Switzer.
  def euclid[A: Integral](x: A, y: A): A =
    if (y == 0) x
    else euclid(y, x % y)
}
