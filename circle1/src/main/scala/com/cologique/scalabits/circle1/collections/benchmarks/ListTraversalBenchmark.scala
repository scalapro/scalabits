package com.cologique.scalabits.circle1.collections.benchmarks

import com.google.caliper.Runner
import com.google.caliper.SimpleBenchmark
import com.google.caliper.Param

/**
 * [info]   1000           JavaArrayListTraversal    3.53 =
 * [info]   1000        ListTraversalUsingForeach    6.13 =
 * [info]   1000      VectorTraversalUsingForeach    5.52 =
 * [info]   1000     VectorTraversalUsingFoldLeft   16.85 =
 * [info]   1000  ListBufferTraversalUsingForeach    5.94 =
 * [info]   1000       ListTraversalUsingFoldLeft   15.41 =
 * [info]   1000 ListBufferTraversalUsingFoldLeft   42.99 =
 * [info]  10000           JavaArrayListTraversal   37.64 =
 * [info]  10000        ListTraversalUsingForeach   56.53 =
 * [info]  10000      VectorTraversalUsingForeach   56.17 =
 * [info]  10000     VectorTraversalUsingFoldLeft  164.23 =
 * [info]  10000  ListBufferTraversalUsingForeach   57.24 =
 * [info]  10000       ListTraversalUsingFoldLeft  152.84 =
 * [info]  10000 ListBufferTraversalUsingFoldLeft  175.10 =
 * [info] 100000           JavaArrayListTraversal  431.63 ===
 * [info] 100000        ListTraversalUsingForeach 1219.98 ===========
 * [info] 100000      VectorTraversalUsingForeach  906.34 ========
 * [info] 100000     VectorTraversalUsingFoldLeft 2940.86 ==========================
 * [info] 100000  ListBufferTraversalUsingForeach 1195.44 ==========
 * [info] 100000       ListTraversalUsingFoldLeft 3272.12 =============================
 * [info] 100000 ListBufferTraversalUsingFoldLeft 3284.01 ==============================
 */
object ListTraversalBenchmark extends App {
  Runner.main(classOf[ListTraversalBenchmark], args);
}

class ListTraversalBenchmark extends SimpleBenchmark {
  @Param(Array("1000", "10000", "100000")) var size: Int = 10000; // set automatically by framework
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
