package com.cologique.scalabits.circle1.collections.benchmarks

import com.google.caliper.SimpleBenchmark
import com.google.caliper.Runner
import com.google.caliper.Param
import com.google.common.collect.ImmutableList
import java.util.HashMap
import collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Buffer

/**
 * [info]                      benchmark    size    ns linear runtime
 * [info] JavaListToScalaBufferCoversion    1000 12.71 ============================
 * [info] JavaListToScalaBufferCoversion   10000 13.32 ==============================
 * [info] JavaListToScalaBufferCoversion  100000 12.43 ===========================
 * [info] JavaListToScalaBufferCoversion 1000000  9.96 ======================
 * [info] ScalaBufferToJavaListCoversion    1000  5.71 ============
 * [info] ScalaBufferToJavaListCoversion   10000  5.74 ============
 * [info] ScalaBufferToJavaListCoversion  100000  5.11 ===========
 * [info] ScalaBufferToJavaListCoversion 1000000  5.09 ===========
 */
object CollectionsConversionBenchmark extends App {
  Runner.main(classOf[CollectionsConversionBenchmark], args);
}

class CollectionsConversionBenchmark extends SimpleBenchmark {
  @Param(Array("1000", "10000", "100000", "1000000")) var size: Int = 10000
  var range = 0 until size
  var vector: Vector[Int] = null
  var javaList: java.util.ArrayList[Int] = null

  override protected def setUp() {
    range = 0 until size
    javaList = {
      val list = new java.util.ArrayList[Int]()
      range.foreach { i =>
        list.add(i)
      }
      list
    }
    vector = Vector(range: _*)
  }

  def timeJavaListToScalaBufferCoversion(reps: Int) {
    for (i <- 1 to reps) {
      val x: Buffer[Int] = asScalaBuffer(javaList)
      if(x.size != javaList.size())
    	  println(x.size())
    }
  }

  def timeScalaBufferToJavaListCoversion(reps: Int) {
    for (i <- 1 to reps) {
      val x: java.util.List[Int] = seqAsJavaList(vector)
      if(x.size != vector.size())
    	  println(x.size())
    }
  }
}
