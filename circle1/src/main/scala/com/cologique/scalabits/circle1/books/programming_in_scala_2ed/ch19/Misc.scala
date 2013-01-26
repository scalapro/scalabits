package com.cologique.scalabits.circle1.books.programming_in_scala_2ed.ch19

/* 
 * Original source:
 * http://booksites.artima.com/programming_in_scala_2ed/examples/type-parameterization/Misc.scala
 * 
 * Modifications to original source, if any, pursuant to group study.
 */

/*
 * Copyright (C) 2007-2010 Artima, Inc. All rights reserved.
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
 *
 * Example code from:
 *
 * Programming in Scala, Second Edition
 * by Martin Odersky, Lex Spoon, Bill Venners
 *
 * http://booksites.artima.com/programming_in_scala_2ed
 */

object Misc {

  class Cell[T](init: T) {

    private[this] var current = init

    def get = current
    def set(x: T) { current = x } // Prevents it from being covariant.
  }

  // Redacted from the standard library.

  trait OutputChannel[-T] {
    def write(x: T)
  }

  // Redacted from the standard library.

  trait Function1[-S, +T] {
    def apply(x: S): T
  }

  class StringChannel[-T] extends OutputChannel[T] {
    private var buffer: StringBuffer = new StringBuffer()

    def write(x: T) = buffer.append(x.toString)
  }

  def main(args: Array[String]) = {

    val intOutputChannel: OutputChannel[Int] = new StringChannel[Int]()
    val anyOutputChannel: OutputChannel[Any] = new StringChannel[Any]

    // val anyOutputChannel1: OutputChannel[Any] = intOutputChannel // Object expects Int. Any not OK.
    val intOutputChannel1: OutputChannel[Int] = anyOutputChannel // OK. Object expects Any. Int OK.

    val isLargeList: ((List[Int]) => Boolean) = _.length > 100
    val isLargeSeq: ((Seq[Int]) => Boolean) = _.length > 100

    var isLargeList1: ((Seq[Int]) => Boolean) = (s => false)
    var isLargeSeq1: ((Seq[Int]) => Boolean) = (s => false)

    // isLargeSeq1 = isLargeList // Object expects List[Int]. Seq[Int] may be Vector. Not OK.
    isLargeList1 = isLargeSeq // Object expects Seq[Int]. List[Int] is a Seq[Int]. OK.

    val stringArray = Array("some", "strings")
    // val anyrefArray : Array[AnyRef] = stringArray // No good. Scala arrays are invariant.
    val anyArray = stringArray.asInstanceOf[Array[Any]]
    // anyArray(0) = 2 // Runtime ArrayStoreException.
  }

}