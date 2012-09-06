// Tree.scala: Mixable language ADTs.
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

import scalaz.{Applicative, Traverse}
import scalaz.syntax.applicative._

/** The mixable languages and their ADTs. */
object Languages {
  type FCValue = Any

  /** The language of the dynamic environment. */
  sealed trait Environment[+E]
  case class Bind[E](n: String, v: FCValue, body: E) extends Environment[E]
  case class Ref(n: String) extends Environment[Nothing]

  object Environment {
    implicit object environmentInstance extends Traverse[Environment] {
      def traverseImpl[G[_]: Applicative,A,B
		     ](fa: Environment[A])(f: A => G[B]): G[Environment[B]] = fa match {
	case Bind(n, v, b) => f(b) map (Bind(n, v, _))
	case Ref(n) => (Ref(n): Environment[B]).point[G]
      }
    }
  }

  /** The language of simple arithmetic. */
  sealed trait Arithmetic[+E]
  case class Add[E](l: E, r: E) extends Arithmetic[E]
  case class Sub[E](l: E, r: E) extends Arithmetic[E]
  case class Mul[E](l: E, r: E) extends Arithmetic[E]
  case class Div[E](l: E, r: E) extends Arithmetic[E]

  object Arithmetic {
    implicit object arithmeticInstance extends Traverse[Arithmetic] {
      def traverseImpl[G[_]: Applicative,A,B
		     ](fa: Arithmetic[A])(f: A => G[B]): G[Arithmetic[B]] = fa match {
	case Add(l, r) => ^(f(l), f(r))(Add.apply)
	case Sub(l, r) => ^(f(l), f(r))(Sub.apply)
	case Mul(l, r) => ^(f(l), f(r))(Mul.apply)
	case Div(l, r) => ^(f(l), f(r))(Div.apply)
      }
    }
  }

  /** The language of primitives. */
  sealed trait Literal[+E]
  case class Lit(v: FCValue) extends Literal[Nothing]
  case class Bottom(err: String) extends Literal[Nothing]

  object Literal {
    implicit object environmentInstance extends Traverse[Literal] {
      def traverseImpl[G[_]: Applicative,A,B
		     ](fa: Literal[A])(f: A => G[B]): G[Literal[B]] =
	fa.asInstanceOf[Literal[B]].point[G]
    }
  }
}
