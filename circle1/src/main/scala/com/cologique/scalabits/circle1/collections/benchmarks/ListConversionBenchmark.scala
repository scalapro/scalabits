package com.cologique.scalabits.circle1.collections.benchmarks

import com.google.caliper.Runner
import com.google.caliper.SimpleBenchmark
import com.google.caliper.Param

object ListConversionBenchmark extends App {
  Runner.main(classOf[ListConversionBenchmark], args);
}

class ListConversionBenchmark extends SimpleBenchmark {
  @Param(Array("1000", "10000")) var size: Int = 10000; // set automatically by framework
  //, "100000", "1000000"
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

  def timeJavaListConversion(reps: Int) {
    for (i <- 1 to reps) {
      val list = new java.util.ArrayList[Int]()
      for (i <- 0 until javaList.size()) {
        list.add(javaList.get(i) * 2);
      }
    }
  }

  def timeImmutableListConversionUsingMap(reps: Int) {
    for (i <- 1 to reps) {
      val list = immutableList.map(_ * 2)
    }
  }

  def timeMutableListConversionUsingMap(reps: Int) {
    for (i <- 1 to reps) {
      val list = mutableList.map(_ * 2)
    }
  }
}
