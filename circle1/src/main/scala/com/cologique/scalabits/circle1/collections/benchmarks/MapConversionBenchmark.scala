package com.cologique.scalabits.circle1.collections.benchmarks

import com.google.caliper.SimpleBenchmark
import com.google.caliper.Runner
import com.google.caliper.Param
import com.google.common.collect.ImmutableList
import java.util.HashMap

/**
 * [info]    size             benchmark      us linear runtime
 * [info]    1000  JavaHashMapCoversion     104 =
 * [info]    1000         MapConversion     226 =
 * [info]    1000 MutuableMapConversion     160 =
 * [info]   10000  JavaHashMapCoversion    1078 =
 * [info]   10000         MapConversion    3251 =
 * [info]   10000 MutuableMapConversion    1421 =
 * [info]  100000  JavaHashMapCoversion   29875 =
 * [info]  100000         MapConversion  105295 ==
 * [info]  100000 MutuableMapConversion   44888 =
 * [info] 1000000  JavaHashMapCoversion  497962 ============
 * [info] 1000000         MapConversion 1211846 ==============================
 * [info] 1000000 MutuableMapConversion  658148 ================
 */

object MapConversionBenchmark extends App {
  Runner.main(classOf[MapConversionBenchmark], "-Jmemory=-Xmx512M" +: args);
}

class MapConversionBenchmark extends SimpleBenchmark {
  @Param(Array("1000", "10000", "100000", "1000000")) var size: Int = 10000
  var range = 0 until size

  var javaMap: HashMap[Int, Int] = null
  var immutableMap: Map[Int, Int] = null
  var mutableMap: scala.collection.mutable.HashMap[Int, Int] = null

  override protected def setUp() {
    range = 0 until size
    javaMap = {
      val map = new java.util.HashMap[Int, Int]()
      for (i <- 0 until size) {
        map.put(i, i)
      }
      map
    }
    immutableMap = {
      var map = Map[Int, Int]()
      range.foreach { a =>
        map += a -> a
      }
      map
    }
    mutableMap = {
      var map = scala.collection.mutable.HashMap.empty[Int, Int]
      range.foreach { a =>
        map += a -> a
      }
      map
    }
  }

  def timeJavaHashMapCoversion(reps: Int) {
    for (i <- 1 to reps) {
      val map = new java.util.HashMap[Int, Int]()
      for (i <- 0 until size) {
        map.put(i, i)
      }
    }
  }

  def timeImmutableMapConversion(reps: Int) {
    for (i <- 1 to reps) {
      immutableMap.map { case (a, b) => (a, b * 2) }
    }
  }

  def timeMutuableMapConversion(reps: Int) {
    for (i <- 1 to reps) {
      mutableMap.map { case (a, b) => (a, b * 2) }
    }
  }
}
