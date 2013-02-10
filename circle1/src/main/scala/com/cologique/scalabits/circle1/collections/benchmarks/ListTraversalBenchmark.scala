package com.cologique.scalabits.circle1.collections.benchmarks

import com.google.caliper.Runner
import com.google.caliper.SimpleBenchmark
import com.google.caliper.Param

object ListTraversalBenchmark extends App {
  Runner.main(classOf[ListTraversalBenchmark], args);
}

class ListTraversalBenchmark extends SimpleBenchmark {
  @Param(Array("1000", "10000")) var size: Int = 10000; // set automatically by framework
  // , "100000", "1000000"
  var range: Range = null
  var immutableList: List[Int] = null
  var javaList: java.util.ArrayList[Int] = null
  var mutableList: scala.collection.mutable.ListBuffer[Int] = null

  override protected def setUp() {
    range = 0 until size
    immutableList = List(range: _*)
    javaList = {
      val list = new java.util.ArrayList[Int]()
      range.foreach { i =>
        list.add(i)
      }
      list
    }
    mutableList = {
      val list = scala.collection.mutable.ListBuffer[Int]()
      range.foreach(list += _)
      list
    }
  }

  def timeJavaListTraversal(reps: Int) {
    for (i <- 1 to reps) {
      var y: Int = 0
      for (i <- 0 until javaList.size()) {
        y += javaList.get(i)
      }
    }
  }

  def timeImmutableListTraversalUsingForeach(reps: Int) {
    for (i <- 1 to reps) {
      var y: Int = 0
      immutableList.foreach { x =>
        y += x
      }
    }
  }

  def timeMutableListTraversalUsingForeach(reps: Int) {
    for (i <- 1 to reps) {
      var y: Int = 0
      mutableList.foreach { x =>
        y += x
      }
    }
  }

  def timeImmutableListTraversalUsingFoldLeft(reps: Int) {
    for (i <- 1 to reps) {
      immutableList.foldLeft(0)((x, y) => x + y)
    }
  }

  def timeMutableListTraversalUsingFoldLeft(reps: Int) {
    for (i <- 1 to reps) {
      mutableList.foldLeft(0)((x, y) => x + y)
    }
  }

}
