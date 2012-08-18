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
  implicit def lift[F[_], IF](fv: F[IF])(implicit ilift: IF => FixF[F],
					 F: Functor[F]): FixF[F] =
    FixF(fv map ilift)
}
