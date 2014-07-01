package typelevelcourse.spire.answers

import scala.{specialized => sp}

import spire.math.Integral
import spire.implicits._

object GCD {
  // From an example by Tom Switzer.
  def euclid[A: Integral](x: A, y: A): A =
    if (y == 0) x
    else euclid(y, x % y)

  // A similar Scalaz call.
  def twostrings = {
    import scalaz.std.string._, scalaz.syntax.monoid._
    "a" multiply 2
  }
}
