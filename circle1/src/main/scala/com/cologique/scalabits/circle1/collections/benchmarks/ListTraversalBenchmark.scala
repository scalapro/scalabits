package com.cologique.scalabits.circle1.collections.benchmarks

import com.google.caliper.Runner
import com.google.caliper.SimpleBenchmark
import com.google.caliper.Param

/**
 * [info]   size                          benchmark      us linear runtime
 * [info]   1000             JavaArrayListTraversal    2.52 =
 * [info]   1000          ListTraversalUsingForeach    4.24 =
 * [info]   1000         ListTraversalUsingFoldLeft    9.03 =
 * [info]   1000        VectorTraversalUsingForeach    4.07 =
 * [info]   1000       VectorTraversalUsingFoldLeft   10.11 =
 * [info]   1000      VectorTraversalUsingFoldRight   36.32 =
 * [info]   1000    ListBufferTraversalUsingForeach    4.26 =
 * [info]   1000   ListBufferTraversalUsingFoldLeft   10.26 =
 * [info]   1000   ArrayBufferTraversalUsingForeach    1.29 =
 * [info]   1000  ArrayBufferTraversalUsingFoldLeft   15.23 =
 * [info]   1000 ArrayBufferTraversalUsingFoldRight   14.17 =
 * [info]   1000      ArraySeqTraversalUsingForeach    1.64 =
 * [info]   1000     ArraySeqTraversalUsingFoldLeft   14.58 =
 * [info]   1000    ArraySeqTraversalUsingFoldRight   14.15 =
 * [info]   1000         ArrayTraversalUsingForeach    9.81 =
 * [info]   1000        ArrayTraversalUsingFoldLeft   25.59 =
 * [info]   1000       ArrayTraversalUsingFoldRight   24.68 =
 * [info]  10000             JavaArrayListTraversal   28.58 =
 * [info]  10000          ListTraversalUsingForeach   43.77 =
 * [info]  10000         ListTraversalUsingFoldLeft   87.37 =
 * [info]  10000        VectorTraversalUsingForeach   43.39 =
 * [info]  10000       VectorTraversalUsingFoldLeft  105.29 =
 * [info]  10000      VectorTraversalUsingFoldRight  395.01 ==
 * [info]  10000    ListBufferTraversalUsingForeach   44.25 =
 * [info]  10000   ListBufferTraversalUsingFoldLeft  116.97 =
 * [info]  10000   ArrayBufferTraversalUsingForeach   18.01 =
 * [info]  10000  ArrayBufferTraversalUsingFoldLeft  150.12 =
 * [info]  10000 ArrayBufferTraversalUsingFoldRight  145.82 =
 * [info]  10000      ArraySeqTraversalUsingForeach   21.44 =
 * [info]  10000     ArraySeqTraversalUsingFoldLeft  158.67 =
 * [info]  10000    ArraySeqTraversalUsingFoldRight  148.44 =
 * [info]  10000         ArrayTraversalUsingForeach  109.02 =
 * [info]  10000        ArrayTraversalUsingFoldLeft  268.52 =
 * [info]  10000       ArrayTraversalUsingFoldRight  276.81 =
 * [info] 100000             JavaArrayListTraversal  291.85 =
 * [info] 100000          ListTraversalUsingForeach  544.27 ===
 * [info] 100000         ListTraversalUsingFoldLeft 1562.61 ========
 * [info] 100000        VectorTraversalUsingForeach  459.80 ==
 * [info] 100000       VectorTraversalUsingFoldLeft 1515.95 ========
 * [info] 100000      VectorTraversalUsingFoldRight 5427.04 ==============================
 * [info] 100000    ListBufferTraversalUsingForeach  589.23 ===
 * [info] 100000   ListBufferTraversalUsingFoldLeft 1711.29 =========
 * [info] 100000   ArrayBufferTraversalUsingForeach  181.04 =
 * [info] 100000  ArrayBufferTraversalUsingFoldLeft 1111.56 ======
 * [info] 100000 ArrayBufferTraversalUsingFoldRight 1406.32 =======
 * [info] 100000      ArraySeqTraversalUsingForeach  219.62 =
 * [info] 100000     ArraySeqTraversalUsingFoldLeft 1162.13 ======
 * [info] 100000    ArraySeqTraversalUsingFoldRight 1231.09 ======
 * [info] 100000         ArrayTraversalUsingForeach  967.27 =====
 * [info] 100000        ArrayTraversalUsingFoldLeft 1722.35 =========
 * [info] 100000       ArrayTraversalUsingFoldRight 1844.58 ==========
 *
 */

object ListTraversalBenchmark extends App {
  Runner.main(classOf[ListTraversalBenchmark], "-Jmemory=-Xmx1G" +: args);
}

class ListTraversalBenchmark extends SimpleBenchmark {
  @Param(Array("1000", "10000", "100000", "100000")) var size: Int = 10000; // set automatically by framework
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
      list.foreach { x =>
        y += x
      }
    }
  }

  def timeListTraversalUsingFoldLeft(reps: Int) {
    for (i <- 1 to reps) {
      list.foldLeft(0)((x, y) => x + y)
    }
  }

  //  def timeListTraversalUsingFoldRight(reps: Int) {
  //    for (i <- 1 to reps) {
  //      list.foldRight(0)((x, y) => x + y)
  //    }
  //  }

  def timeVectorTraversalUsingForeach(reps: Int) {
    for (i <- 1 to reps) {
      var y: Int = 0
      vector.foreach { x =>
        y += x
      }
    }
  }

  def timeVectorTraversalUsingFoldLeft(reps: Int) {
    for (i <- 1 to reps) {
      vector.foldLeft(0)((x, y) => x + y)
    }
  }

  def timeVectorTraversalUsingFoldRight(reps: Int) {
    for (i <- 1 to reps) {
      vector.foldRight(0)((x, y) => x + y)
    }
  }

  def timeListBufferTraversalUsingForeach(reps: Int) {
    for (i <- 1 to reps) {
      var y: Int = 0
      listBuffer.foreach { x =>
        y += x
      }
    }
  }

  def timeListBufferTraversalUsingFoldLeft(reps: Int) {
    for (i <- 1 to reps) {
      listBuffer.foldLeft(0)((x, y) => x + y)
    }
  }

  //  def timeListBufferTraversalUsingFoldRight(reps: Int) {
  //    for (i <- 1 to reps) {
  //      listBuffer.foldRight(0)((x, y) => x + y)
  //    }
  //  }

  def timeArrayBufferTraversalUsingForeach(reps: Int) {
    for (i <- 1 to reps) {
      var y: Int = 0
      arrayBuffer.foreach { x =>
        y += x
      }
    }
  }

  def timeArrayBufferTraversalUsingFoldLeft(reps: Int) {
    for (i <- 1 to reps) {
      arrayBuffer.foldLeft(0)((x, y) => x + y)
    }
  }

  def timeArrayBufferTraversalUsingFoldRight(reps: Int) {
    for (i <- 1 to reps) {
      arrayBuffer.foldRight(0)((x, y) => x + y)
    }
  }

  def timeArraySeqTraversalUsingForeach(reps: Int) {
    for (i <- 1 to reps) {
      var y: Int = 0
      arraySeq.foreach { x =>
        y += x
      }
    }
  }

  def timeArraySeqTraversalUsingFoldLeft(reps: Int) {
    for (i <- 1 to reps) {
      arraySeq.foldLeft(0)((x, y) => x + y)
    }
  }

  def timeArraySeqTraversalUsingFoldRight(reps: Int) {
    for (i <- 1 to reps) {
      arraySeq.foldRight(0)((x, y) => x + y)
    }
  }

  def timeArrayTraversalUsingForeach(reps: Int) {
    for (i <- 1 to reps) {
      var y: Int = 0
      array.foreach { x =>
        y += x
      }
    }
  }

  def timeArrayTraversalUsingFoldLeft(reps: Int) {
    for (i <- 1 to reps) {
      array.foldLeft(0)((x, y) => x + y)
    }
  }

  def timeArrayTraversalUsingFoldRight(reps: Int) {
    for (i <- 1 to reps) {
      array.foldRight(0)((x, y) => x + y)
    }
  }

}
