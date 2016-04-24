/*
 * Copyright 2015 Merlijn Boogerd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mboogerd.ccrdt.lattice.linearsum

import algebra.Eq
import algebra.lattice.{BoundedJoinSemilattice, JoinSemilattice}
import com.github.mboogerd.ccrdt.lattice.LatticeSyntax._

/**
  *
  */
object LinearSumInstances extends LinearSumSyntax {

  class LinearSumJoinSemilattice[S: JoinSemilattice, T: JoinSemilattice] extends JoinSemilattice[S :⊕: T] {
    override def join(lhs: S :⊕: T, rhs: S :⊕: T): S :⊕: T = (lhs, rhs) match {
      case (Left(s1), Left(s2)) ⇒ Left[S, T](s1 ⊔ s2)
      case (Right(t1), Right(t2)) ⇒ Right[S, T](t1 ⊔ t2)
      case (_, r@Right(_)) ⇒ r
      case (r@Right(_), _) ⇒ r
    }
  }

  class LinearSumBoundedJoinSemilattice[S: BoundedJoinSemilattice, T: JoinSemilattice] extends LinearSumJoinSemilattice[S, T] with BoundedJoinSemilattice[S :⊕: T] {
    override def zero: S :⊕: T = Left[S, T](implicitly[BoundedJoinSemilattice[S]].zero)
  }


  class LinearSumEq[S: Eq, T: Eq] extends Eq[S :⊕: T] {
    override def eqv(x: S :⊕: T, y: S :⊕: T): Boolean = (x, y) match {
      case (Left(s1), Left(s2)) ⇒ implicitly[Eq[S]].eqv(s1, s2)
      case (Right(t1), Right(t2)) ⇒ implicitly[Eq[T]].eqv(t1, t2)
      case _ ⇒ false
    }
  }
}
