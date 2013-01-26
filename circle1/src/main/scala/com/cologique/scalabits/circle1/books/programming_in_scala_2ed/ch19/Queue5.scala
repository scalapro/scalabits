package com.cologique.scalabits.circle1.books.programming_in_scala_2ed.ch19

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Buffer

/* 
 * Original source:
 * http://booksites.artima.com/programming_in_scala_2ed/examples/type-parameterization/Queues5.scala
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

object Queues5 {

  trait Queue[T] {
    def head: T
    def tail: Queue[T]
    def enqueue(x: T): Queue[T]
    def isEmpty: Boolean
  }

  object Queue {

    def apply[T](xs: T*): Queue[T] = new QueueImpl[T](xs.toList, Nil)

    // Hiding the default implementation inside the companion object. A good pattern to use?

    private class QueueImpl[T](private val leading: List[T], private val trailing: List[T])
      extends Queue[T] {

      def mirror = leading match {
        case Nil => new QueueImpl(trailing.reverse, Nil)
        case _ => this
      }

      def head: T = mirror.leading.head

      def tail: QueueImpl[T] = {
        val q = mirror
        new QueueImpl(q.leading.tail, q.trailing)
      }

      def enqueue(x: T) = new QueueImpl(leading, x :: trailing)

      def isEmpty = leading.isEmpty && trailing.isEmpty

      override def toString() =
        (leading ::: trailing.reverse) mkString ("Queue(", ", ", ")")
    }
  }

  class NotSoStrangeQueue(elements: Int*) extends Queue[Int] {
    private var queueImpl: Buffer[Int] = ArrayBuffer(elements: _*)

    override def head: Int = queueImpl(0)
    override def tail: Queue[Int] = new NotSoStrangeQueue(queueImpl.tail.toBuffer: _*)
    override def enqueue(x: Int): Queue[Int] = new NotSoStrangeQueue((queueImpl :+ x): _*)
    override def isEmpty: Boolean = queueImpl.isEmpty

    def enqueueStringWrong(s: String) = {
      var anyQueue: Queue[Any] = /* this */ /* type mismatch */ null
      anyQueue.enqueue(s)
    }
  }

  def main(args: Array[String]) {
    val q = Queue[Int]() enqueue 1 enqueue 2
    println(q)

    val q1 = new NotSoStrangeQueue(1, 2)
    q1.enqueueStringWrong("wrong type")
  }
}