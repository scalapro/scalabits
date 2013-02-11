package com.cologique.scalabits.circle1.collections.benchmarks

import com.google.caliper.SimpleBenchmark
import com.google.caliper.Runner
import com.google.caliper.Param
import com.google.common.collect.ImmutableList

/**
 * Note: ArraySeq one at a time construction takes too long!!
 * 
 * Sample output
 * [info]    size                     benchmark       us linear runtime
 * [info]    1000         JavaArrayListCreation     26.7 =
 * [info]    1000   ListCreationWithConstructor     21.2 =
 * [info]    1000 VectorCreationWithConstructor     12.3 =
 * [info]    1000                  ListCreation     22.4 =
 * [info]    1000                VectorCreation    116.8 =
 * [info]    1000            ListBufferCreation     32.3 =
 * [info]    1000           ArrayBufferCreation     22.3 =
 * [info]    1000              ArraySeqCreationWithConstructor    349.9 =
 * [info]    1000                 ArrayCreation     23.8 =
 * [info]   10000         JavaArrayListCreation    263.2 =
 * [info]   10000   ListCreationWithConstructor    267.4 =
 * [info]   10000 VectorCreationWithConstructor    226.1 =
 * [info]   10000                  ListCreation    300.0 =
 * [info]   10000                VectorCreation   1760.3 =
 * [info]   10000            ListBufferCreation    385.6 =
 * [info]   10000           ArrayBufferCreation    269.4 =
 * [info]   10000 ArraySeqCreatiWithConstructor   3404.7 =
 * [info]   10000                 ArrayCreation    290.7 =
 * [info]  100000         JavaArrayListCreation   3947.2 =
 * [info]  100000   ListCreationWithConstructor   9838.9 =
 * [info]  100000 VectorCreationWithConstructor   1936.7 =
 * [info]  100000                  ListCreation   5707.3 =
 * [info]  100000                VectorCreation  22308.3 =
 * [info]  100000            ListBufferCreation  10051.8 =
 * [info]  100000           ArrayBufferCreation   6216.9 =
 * [info]  100000 ArraySeqCreatiWithConstructor  33589.4 ==
 * [info]  100000                 ArrayCreation   3556.6 =
 * [info] 1000000         JavaArrayListCreation 138582.7 ==========
 * [info] 1000000   ListCreationWithConstructor 333289.5 ========================
 * [info] 1000000 VectorCreationWithConstructor  53612.2 ====
 * [info] 1000000                  ListCreation 259977.3 ===================
 * [info] 1000000                VectorCreation 322746.8 ========================
 * [info] 1000000            ListBufferCreation 401133.7 ==============================
 * [info] 1000000           ArrayBufferCreation 135614.8 ==========
 * [info] 1000000 ArraySeqCreatiWithConstructor 319123.0 =======================
 * [info] 1000000                 ArrayCreation 112924.1 ========
 */
object ListCreationBenchmark extends App {
  Runner.main(classOf[ListCreationBenchmark], "-Jmemory=-Xmx512M" +: args);
}

class ListCreationBenchmark extends SimpleBenchmark {
  @Param(Array("1000", "10000", "100000", "1000000")) var size: Int = 10000
  var range = 0 until size

  override protected def setUp() {
    range = 0 until size
  }

  def timeJavaArrayListCreation(reps: Int) {
    for (i <- 1 to reps) {
      val collection = new java.util.ArrayList[Int]()
      for (i <- 0 until size) {
        collection.add(i)
      }
    }
  }

  def timeListCreationWithConstructor(reps: Int) {
    for (i <- 1 to reps) {
      val collection = List(range: _*)
    }
  }

  def timeVectorCreationWithConstructor(reps: Int) {
    for (i <- 1 to reps) {
      val collection = Vector(range: _*)
    }
  }

  def timeListCreation(reps: Int) {
    for (i <- 1 to reps) {
      var collection = List[Int]()
      range.foreach { a =>
        collection = a :: collection
      }
    }
  }

  def timeVectorCreation(reps: Int) {
    for (i <- 1 to reps) {
      var collection = Vector[Int]()
      range.foreach { a =>
        collection = a +: collection
      }
    }
  }

  def timeListBufferCreation(reps: Int) {
    for (i <- 1 to reps) {
      val collection = scala.collection.mutable.ListBuffer[Int]()
      range.foreach(collection += _)
    }
  }

  def timeArrayBufferCreation(reps: Int) {
    for (i <- 1 to reps) {
      val collection = scala.collection.mutable.ArrayBuffer[Int]()
      range.foreach(collection += _)
    }
  }

  def timeArraySeqCreationWithConstructor(reps: Int) {
    for (i <- 1 to reps) {
      var collection = scala.collection.mutable.ArraySeq[Int](range:_*)
    }
  }
  
  def timeArrayCreation(reps: Int) {
    for (i <- 1 to reps) {
      val collection = scala.collection.mutable.ArrayBuffer[Int]()
      range.foreach(collection += _)
    }
  }
}
