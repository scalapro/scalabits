package com.cologique.scalabits.circle1.collections.benchmarks

import com.google.caliper.Runner
import com.google.caliper.SimpleBenchmark
import com.google.caliper.Param
import collection.JavaConversions._

/**
 * [info]    size                                    benchmark       us linear runtime
 * [info]    1000                      JavaArrayListConversion     53.1 =
 * [info]    1000 JavaArrayListAsScalaBufferConversionUsingMap     34.3 =
 * [info]    1000                       ListConversionUsingMap     40.4 =
 * [info]    1000                     VectorConversionUsingMap     30.8 =
 * [info]    1000                 ListBufferConversionUsingMap     57.0 =
 * [info]    1000                ArrayBufferConversionUsingMap     25.6 =
 * [info]    1000                   ArraySeqConversionUsingMap     50.1 =
 * [info]    1000                      ArrayConversionUsingMap     46.2 =
 * [info]   10000                      JavaArrayListConversion    584.2 =
 * [info]   10000 JavaArrayListAsScalaBufferConversionUsingMap    477.3 =
 * [info]   10000                       ListConversionUsingMap    517.2 =
 * [info]   10000                     VectorConversionUsingMap    245.6 =
 * [info]   10000                 ListBufferConversionUsingMap    487.6 =
 * [info]   10000                ArrayBufferConversionUsingMap    263.6 =
 * [info]   10000                   ArraySeqConversionUsingMap    412.9 =
 * [info]   10000                      ArrayConversionUsingMap    386.7 =
 * [info]  100000                      JavaArrayListConversion   7852.7 =
 * [info]  100000 JavaArrayListAsScalaBufferConversionUsingMap   5238.7 =
 * [info]  100000                       ListConversionUsingMap  14143.7 =
 * [info]  100000                     VectorConversionUsingMap   6782.6 =
 * [info]  100000                 ListBufferConversionUsingMap  18688.0 =
 * [info]  100000                ArrayBufferConversionUsingMap   3936.0 =
 * [info]  100000                   ArraySeqConversionUsingMap   6100.8 =
 * [info]  100000                      ArrayConversionUsingMap   4422.4 =
 * [info] 1000000                      JavaArrayListConversion 271360.8 ==============
 * [info] 1000000 JavaArrayListAsScalaBufferConversionUsingMap 140096.8 =======
 * [info] 1000000                       ListConversionUsingMap 509999.0 ===========================
 * [info] 1000000                     VectorConversionUsingMap  94353.6 =====
 * [info] 1000000                 ListBufferConversionUsingMap 565419.0 ==============================
 * [info] 1000000                ArrayBufferConversionUsingMap 169984.3 =========
 * [info] 1000000                   ArraySeqConversionUsingMap 110458.1 =====
 * [info] 1000000                      ArrayConversionUsingMap  44557.1 ==
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

  def timeJavaArrayListAsScalaBufferConversionUsingMap(reps: Int) {
    for (i <- 1 to reps) {
      val scalaBuffer = asScalaBuffer(javaList)
      scalaBuffer.map(_ * 2)
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
