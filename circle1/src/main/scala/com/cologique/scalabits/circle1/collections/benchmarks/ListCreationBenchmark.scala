package com.cologique.scalabits.circle1.collections.benchmarks

import com.google.caliper.SimpleBenchmark
import com.google.caliper.Runner
import com.google.caliper.Param
import com.google.common.collect.ImmutableList

/**
 * Sample output
 * [info]   size                     benchmark      us linear runtime
 * [info]   1000         JavaArrayListCreation    38.8 =
 * [info]   1000   ListCreationWithConstructor    35.9 =
 * [info]   1000 VectorCreationWithConstructor    20.9 =
 * [info]   1000                  ListCreation    35.8 =
 * [info]   1000                VectorCreation   164.1 =
 * [info]   1000            ListBufferCreation   103.0 =
 * [info]  10000         JavaArrayListCreation   373.6 =
 * [info]  10000   ListCreationWithConstructor   376.9 =
 * [info]  10000 VectorCreationWithConstructor   189.5 =
 * [info]  10000                  ListCreation   414.5 =
 * [info]  10000                VectorCreation  1726.5 =
 * [info]  10000            ListBufferCreation   492.0 =
 * [info] 100000         JavaArrayListCreation  6567.9 =====
 * [info] 100000   ListCreationWithConstructor 10477.8 ========
 * [info] 100000 VectorCreationWithConstructor  2618.1 ==
 * [info] 100000                  ListCreation  8897.6 =======
 * [info] 100000                VectorCreation 37969.2 ==============================
 * [info] 100000            ListBufferCreation 15825.4 ============
 */
object ListCreationBenchmark extends App {
  Runner.main(classOf[ListCreationBenchmark], args);
}

class ListCreationBenchmark extends SimpleBenchmark {
  @Param(Array("1000", "10000", "100000")) var size: Int = 10000
  var range = 0 until size

  override protected def setUp() {
    range = 0 until size
  }

  def timeJavaArrayListCreation(reps: Int) {
    for (i <- 1 to reps) {
      val list = new java.util.ArrayList[Int]()
      for (i <- 0 until size) {
        list.add(i)
      }
    }
  }

  def timeListCreationWithConstructor(reps: Int) {
    for (i <- 1 to reps) {
      val list = List(range: _*)
    }
  }

  def timeVectorCreationWithConstructor(reps: Int) {
    for (i <- 1 to reps) {
      val list = Vector(range: _*)
    }
  }

  def timeListCreation(reps: Int) {
    for (i <- 1 to reps) {
      var list = List[Int]()
      range.foreach { a =>
        list = a :: list
      }
    }
  }

  def timeVectorCreation(reps: Int) {
    for (i <- 1 to reps) {
      var list = Vector[Int]()
      range.foreach { a =>
        list = a +: list
      }
    }
  }

  def timeListBufferCreation(reps: Int) {
    for (i <- 1 to reps) {
      val list = scala.collection.mutable.ListBuffer[Int]()
      range.foreach(list += _)
    }
  }

}
