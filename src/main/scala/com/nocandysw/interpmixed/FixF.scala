// FixF.scala: Functor fixpoint.
// Copyright (C) 2012 Stephen Compall
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.nocandysw.interpmixed

import scalaz.{~>, Functor}
import scalaz.syntax.functor._

case class FixF[F[_]](extract: F[FixF[F]]) extends AnyVal {
  def trans[B[_]](implicit FNat: F ~> B, FF: Functor[F]): FixF[B] = {
    def recur(x: FixF[F]): FixF[B] =
      FixF(FNat(extract map recur))
    recur(this)
  }
}

object FixF {
  /** Lift an `fv` into a FixF.  Usually more convenient than `apply`.
    *
    * scala> import scalaz.std.option._
    * scala> Some(Some(Some(None))): FixF[Option]
    * res1: com.nocandysw.interpmixed.FixF[Option] =
    *   FixF(Some(FixF(Some(FixF(Some(FixF(None)))))))
    *
    * @param fv Functor to be recursively wrapped up.
    * @param ilift The lift recursion, usually myself with different
    *        params.
    * @param F Obviously, works only on functors.
    */
  implicit def lift[F[_], IF](fv: F[IF])(implicit ilift: IF => FixF[F],
					 F: Functor[F]): FixF[F] =
    FixF(fv map ilift)

  /** @todo In scalaz 7.0.0-M3, replace with Functor coproduct */
  private[FixF] implicit
  def fCoproduct[F[_]: Functor, G[_]: Functor
	       ]: Functor[({type E[A] = Either[F[A], G[A]]})#E] =
    new Functor[({type E[A] = Either[F[A], G[A]]})#E] {
      def map[A, B](fa: Either[F[A], G[A]])(f: A => B): Either[F[B], G[B]] =
	fa fold (a => Left(a map f), b => Right(b map f))
    }
}
