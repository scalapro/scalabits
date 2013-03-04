package com.cologique.scalabits.circle1.collections.benchmarks

import com.google.caliper.SimpleBenchmark
import com.google.caliper.Runner
import com.google.caliper.Param
import com.google.common.collect.ImmutableList
import java.util.HashMap
import collection.JavaConversions._

/**
 * [info]    size                      benchmark      us linear runtime
 * [info]    1000           JavaHashMapCoversion     107 =
 * [info]    1000 JavaHashMapAsScalaMapCoversion     122 =
 * [info]    1000         ImmutableMapConversion     228 =
 * [info]    1000          MutuableMapConversion     125 =
 * [info]   10000           JavaHashMapCoversion     869 =
 * [info]   10000 JavaHashMapAsScalaMapCoversion    1280 =
 * [info]   10000         ImmutableMapConversion    3315 =
 * [info]   10000          MutuableMapConversion    1314 =
 * [info]  100000           JavaHashMapCoversion   23777 =
 * [info]  100000 JavaHashMapAsScalaMapCoversion   39113 =
 * [info]  100000         ImmutableMapConversion  122035 =
 * [info]  100000          MutuableMapConversion   45036 =
 * [info] 1000000           JavaHashMapCoversion  912504 =========
 * [info] 1000000 JavaHashMapAsScalaMapCoversion 2103002 =====================
 * [info] 1000000         ImmutableMapConversion 2970971 ==============================
 * [info] 1000000          MutuableMapConversion 1230317 ============
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
        map.put(i, javaMap.get(i))
      }
    }
  }

  def timeJavaHashMapAsScalaMapCoversion(reps: Int) {
    for (i <- 1 to reps) {
      mapAsScalaMap(javaMap).map { case (a, b) => (a, b * 2) }
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
