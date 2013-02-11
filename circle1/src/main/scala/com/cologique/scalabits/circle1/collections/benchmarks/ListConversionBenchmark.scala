package com.cologique.scalabits.circle1.collections.benchmarks

import com.google.caliper.Runner
import com.google.caliper.SimpleBenchmark
import com.google.caliper.Param

/**
 * [info]    size                     benchmark       us linear runtime
 * [info]    1000       JavaArrayListConversion     29.3 =
 * [info]    1000        ListConversionUsingMap     22.7 =
 * [info]    1000      VectorConversionUsingMap     13.2 =
 * [info]    1000  ListBufferConversionUsingMap     23.5 =
 * [info]    1000 ArrayBufferConversionUsingMap     15.5 =
 * [info]    1000    ArraySeqConversionUsingMap     20.6 =
 * [info]    1000       ArrayConversionUsingMap     18.0 =
 * [info]   10000       JavaArrayListConversion    310.1 =
 * [info]   10000        ListConversionUsingMap    261.3 =
 * [info]   10000      VectorConversionUsingMap    147.0 =
 * [info]   10000  ListBufferConversionUsingMap    266.6 =
 * [info]   10000 ArrayBufferConversionUsingMap    185.9 =
 * [info]   10000    ArraySeqConversionUsingMap    271.5 =
 * [info]   10000       ArrayConversionUsingMap    192.9 =
 * [info]  100000       JavaArrayListConversion   4646.5 =
 * [info]  100000        ListConversionUsingMap   8243.2 =
 * [info]  100000      VectorConversionUsingMap   2795.4 =
 * [info]  100000  ListBufferConversionUsingMap   8643.0 =
 * [info]  100000 ArrayBufferConversionUsingMap   2371.4 =
 * [info]  100000    ArraySeqConversionUsingMap   3626.5 =
 * [info]  100000       ArrayConversionUsingMap   2161.6 =
 * [info] 1000000       JavaArrayListConversion 143581.3 ==============
 * [info] 1000000        ListConversionUsingMap 304757.2 =============================
 * [info] 1000000      VectorConversionUsingMap  52584.8 =====
 * [info] 1000000  ListBufferConversionUsingMap 305284.2 ==============================
 * [info] 1000000 ArrayBufferConversionUsingMap  86364.9 ========
 * [info] 1000000    ArraySeqConversionUsingMap  81252.0 =======
 * [info] 1000000       ArrayConversionUsingMap  25200.0 ==
 *
 */

object ListConversionBenchmark extends App {
  Runner.main(classOf[ListConversionBenchmark], "-Jmemory=-Xmx1G" +: args);
}

class ListConversionBenchmark extends SimpleBenchmark {
  @Param(Array("1000", "10000", "100000", "1000000")) var size: Int = 10000; // set automatically by framework
  var range: Range = null
  var list: List[Int] = null
  var vector: Vector[Int] = null
  var javaList: java.util.ArrayList[Int] = null
  var listBuffer: scala.collection.mutable.ListBuffer[Int] = null
  var arrayBuffer: scala.collection.mutable.ArrayBuffer[Int] = null
  var arraySeq: scala.collection.mutable.ArraySeq[Int] = null
  var array: Array[Int] = null

  override protected def setUp() {
    range = 0 until size
    list = List(range: _*)
    vector = Vector(range: _*)
    javaList = {
      val list = new java.util.ArrayList[Int]()
      range.foreach { i =>
        list.add(i)
      }
      list
    }
    listBuffer = scala.collection.mutable.ListBuffer[Int](range: _*)
    arrayBuffer = scala.collection.mutable.ArrayBuffer[Int](range: _*)
    arraySeq = scala.collection.mutable.ArraySeq[Int](range: _*)
    array = range.toArray[Int]
  }

  def timeJavaArrayListConversion(reps: Int) {
    for (i <- 1 to reps) {
      val list = new java.util.ArrayList[Int]()
      for (i <- 0 until javaList.size()) {
        list.add(javaList.get(i) * 2);
      }
    }
  }

  def timeListConversionUsingMap(reps: Int) {
    for (i <- 1 to reps) {
      val list1 = list.map(_ * 2)
    }
  }

  def timeVectorConversionUsingMap(reps: Int) {
    for (i <- 1 to reps) {
      val list = vector.map(_ * 2)
    }
  }

  def timeListBufferConversionUsingMap(reps: Int) {
    for (i <- 1 to reps) {
      val list = listBuffer.map(_ * 2)
    }
  }

  def timeArrayBufferConversionUsingMap(reps: Int) {
    for (i <- 1 to reps) {
      val list = arrayBuffer.map(_ * 2)
    }
  }

  def timeArraySeqConversionUsingMap(reps: Int) {
    for (i <- 1 to reps) {
      val list = arraySeq.map(_ * 2)
    }
  }

  def timeArrayConversionUsingMap(reps: Int) {
    for (i <- 1 to reps) {
      val list = array.map(_ * 2)
    }
  }

}
