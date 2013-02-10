package com.cologique.scalabits.circle1.collections.benchmarks

import com.google.caliper.SimpleBenchmark
import com.google.caliper.Runner
import com.google.caliper.Param
import com.google.common.collect.ImmutableList

object ListCreationBenchmark extends App {
  Runner.main(classOf[ListCreationBenchmark], args);
}

class ListCreationBenchmark extends SimpleBenchmark {
  @Param(Array("1000", "10000", "100000", "1000000")) var size: Int = 10000
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
