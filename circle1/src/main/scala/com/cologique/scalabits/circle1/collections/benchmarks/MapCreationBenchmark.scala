package com.cologique.scalabits.circle1.collections.benchmarks

import com.google.caliper.SimpleBenchmark
import com.google.caliper.Runner
import com.google.caliper.Param
import com.google.common.collect.ImmutableList

object MapCreationBenchmark extends App {
  Runner.main(classOf[MapCreationBenchmark], args);
}

class MapCreationBenchmark extends SimpleBenchmark {
  @Param(Array("1000", "10000", "100000")) var size: Int = 10000
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
