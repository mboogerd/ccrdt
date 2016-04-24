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

package com.github.mboogerd.ccrdt.lattice.lexographic

import algebra.lattice.{BoundedJoinSemilattice, JoinSemilattice}

trait LexProductSyntax {

  type :⊠:[S, T] = LexProductLattice[S, T]

  implicit def laticeLexProductOps[T: JoinSemilattice](t: T): LexProductOps[T] = new LexProductOps[T](t)
}

final class LexProductOps[T: JoinSemilattice](right: T) {
  def lexproduct[S: JoinSemilattice](left: S): S :⊠: T = new :⊠:(left, right)
  def :⊠:[S: JoinSemilattice](left: S): S :⊠: T = lexproduct(left)
}