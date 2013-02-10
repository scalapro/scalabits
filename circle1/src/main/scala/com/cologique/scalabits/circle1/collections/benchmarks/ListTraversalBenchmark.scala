package com.cologique.scalabits.circle1.collections.benchmarks

import com.google.caliper.Runner
import com.google.caliper.SimpleBenchmark
import com.google.caliper.Param

object ListTraversalBenchmark extends App {
  Runner.main(classOf[ListTraversalBenchmark], args);
}

class ListTraversalBenchmark extends SimpleBenchmark {
  @Param(Array("1000", "10000", "100000", "1000000")) var size: Int = 10000; // set automatically by framework
  var range: Range = null
  var immutableList: List[Int] = null
  var immutableVector: Vector[Int] = null
  var javaList: java.util.ArrayList[Int] = null
  var mutableList: scala.collection.mutable.ListBuffer[Int] = null

  override protected def setUp() {
    range = 0 until size
    immutableList = List(range: _*)
    immutableVector = Vector(range: _*)
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

  def timeJavaArrayListTraversal(reps: Int) {
    for (i <- 1 to reps) {
      var y: Int = 0
      for (i <- 0 until javaList.size()) {
        y += javaList.get(i)
      }
    }
  }

  def timeListTraversalUsingForeach(reps: Int) {
    for (i <- 1 to reps) {
      var y: Int = 0
      immutableList.foreach { x =>
        y += x
      }
    }
  }

  def timeVectorTraversalUsingForeach(reps: Int) {
    for (i <- 1 to reps) {
      var y: Int = 0
      immutableVector.foreach { x =>
        y += x
      }
    }
  }

  def timeVectorTraversalUsingFoldLeft(reps: Int) {
    for (i <- 1 to reps) {
      immutableVector.foldLeft(0)((x, y) => x + y)
    }
  }

  def timeListBufferTraversalUsingForeach(reps: Int) {
    for (i <- 1 to reps) {
      var y: Int = 0
      mutableList.foreach { x =>
        y += x
      }
    }
  }

  def timeListTraversalUsingFoldLeft(reps: Int) {
    for (i <- 1 to reps) {
      immutableList.foldLeft(0)((x, y) => x + y)
    }
  }

  def timeListBufferTraversalUsingFoldLeft(reps: Int) {
    for (i <- 1 to reps) {
      mutableList.foldLeft(0)((x, y) => x + y)
    }
  }

}
