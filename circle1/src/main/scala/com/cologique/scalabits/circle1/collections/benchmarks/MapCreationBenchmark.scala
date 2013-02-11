package com.cologique.scalabits.circle1.collections.benchmarks

import com.google.caliper.SimpleBenchmark
import com.google.caliper.Runner
import com.google.caliper.Param
import com.google.common.collect.ImmutableList

/**
 * [info]    size           benchmark        us linear runtime
 * [info]    1000 JavaHashMapCreation      84.0 =
 * [info]    1000         MapCreation     262.6 =
 * [info]    1000 MutuableMapCreation     128.2 =
 * [info]   10000 JavaHashMapCreation     804.2 =
 * [info]   10000         MapCreation    3867.9 =
 * [info]   10000 MutuableMapCreation    1155.9 =
 * [info]  100000 JavaHashMapCreation   23860.0 =
 * [info]  100000         MapCreation  168009.1 =
 * [info]  100000 MutuableMapCreation   30736.2 =
 * [info] 1000000 JavaHashMapCreation  491668.0 =
 * [info] 1000000         MapCreation 8202531.5 ==============================
 * [info] 1000000 MutuableMapCreation  775827.5 ==
 */
object MapCreationBenchmark extends App {
  Runner.main(classOf[MapCreationBenchmark], args);
}

class MapCreationBenchmark extends SimpleBenchmark {
  @Param(Array("1000", "10000", "100000", "1000000")) var size: Int = 10000
  var range = 0 until size

  override protected def setUp() {
    range = 0 until size
  }

  def timeJavaHashMapCreation(reps: Int) {
    for (i <- 1 to reps) {
      val map = new java.util.HashMap[Int, Int]()
      for (i <- 0 until size) {
        map.put(i, i)
      }
    }
  }

  def timeMapCreation(reps: Int) {
    for (i <- 1 to reps) {
      var map = Map[Int, Int]()
      range.foreach { a =>
        map += a -> a 
      }
    }
  }

  def timeMutuableMapCreation(reps: Int) {
    for (i <- 1 to reps) {
      var map = scala.collection.mutable.HashMap.empty[Int,Int]
      range.foreach { a =>
        map += a -> a 
      }
    }
  }
}
