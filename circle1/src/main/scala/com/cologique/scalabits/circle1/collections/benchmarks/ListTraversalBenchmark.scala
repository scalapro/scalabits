package com.cologique.scalabits.circle1.collections.benchmarks

import com.google.caliper.Runner
import com.google.caliper.SimpleBenchmark
import com.google.caliper.Param
import collection.JavaConversions._

/**
[info]   1000                            JavaArrayListTraversal    2.54 =
[info]   1000  JavaArrayListAsScalaBufferConversionUsingForeach    8.03 =
[info]   1000 JavaArrayListAsScalaBufferConversionUsingFoldLeft   12.97 =
[info]   1000                         ListTraversalUsingForeach    4.29 =
[info]   1000                        ListTraversalUsingFoldLeft    8.64 =
[info]   1000                       VectorTraversalUsingForeach    3.99 =
[info]   1000                      VectorTraversalUsingFoldLeft    9.80 =
[info]   1000                     VectorTraversalUsingFoldRight   36.25 =
[info]   1000                   ListBufferTraversalUsingForeach    4.26 =
[info]   1000                  ListBufferTraversalUsingFoldLeft   10.26 =
[info]   1000                  ArrayBufferTraversalUsingForeach    1.29 =
[info]   1000                 ArrayBufferTraversalUsingFoldLeft   14.65 =
[info]   1000                ArrayBufferTraversalUsingFoldRight   14.24 =
[info]   1000                     ArraySeqTraversalUsingForeach    1.65 =
[info]   1000                    ArraySeqTraversalUsingFoldLeft   14.69 =
[info]   1000                   ArraySeqTraversalUsingFoldRight   15.66 =
[info]   1000                        ArrayTraversalUsingForeach   10.32 =
[info]   1000                       ArrayTraversalUsingFoldLeft   27.07 =
[info]   1000                      ArrayTraversalUsingFoldRight   25.29 =
[info]  10000                            JavaArrayListTraversal   28.86 =
[info]  10000  JavaArrayListAsScalaBufferConversionUsingForeach   97.12 =
[info]  10000 JavaArrayListAsScalaBufferConversionUsingFoldLeft  159.05 =
[info]  10000                         ListTraversalUsingForeach   50.20 =
[info]  10000                        ListTraversalUsingFoldLeft  117.15 =
[info]  10000                       VectorTraversalUsingForeach   49.64 =
[info]  10000                      VectorTraversalUsingFoldLeft  125.91 =
[info]  10000                     VectorTraversalUsingFoldRight  470.66 ==
[info]  10000                   ListBufferTraversalUsingForeach   54.53 =
[info]  10000                  ListBufferTraversalUsingFoldLeft  134.14 =
[info]  10000                  ArrayBufferTraversalUsingForeach   19.37 =
[info]  10000                 ArrayBufferTraversalUsingFoldLeft  168.37 =
[info]  10000                ArrayBufferTraversalUsingFoldRight  166.91 =
[info]  10000                     ArraySeqTraversalUsingForeach   22.63 =
[info]  10000                    ArraySeqTraversalUsingFoldLeft  172.50 =
[info]  10000                   ArraySeqTraversalUsingFoldRight  176.44 =
[info]  10000                        ArrayTraversalUsingForeach  126.12 =
[info]  10000                       ArrayTraversalUsingFoldLeft  311.50 =
[info]  10000                      ArrayTraversalUsingFoldRight  338.31 =
[info] 100000                            JavaArrayListTraversal  376.68 =
[info] 100000  JavaArrayListAsScalaBufferConversionUsingForeach  949.41 ====
[info] 100000 JavaArrayListAsScalaBufferConversionUsingFoldLeft 1540.31 =======
[info] 100000                         ListTraversalUsingForeach  624.68 ===
[info] 100000                        ListTraversalUsingFoldLeft 1939.15 =========
[info] 100000                       VectorTraversalUsingForeach  460.68 ==
[info] 100000                      VectorTraversalUsingFoldLeft 1458.22 =======
[info] 100000                     VectorTraversalUsingFoldRight 5913.89 ==============================
[info] 100000                   ListBufferTraversalUsingForeach  797.77 ====
[info] 100000                  ListBufferTraversalUsingFoldLeft 3452.80 =================
[info] 100000                  ArrayBufferTraversalUsingForeach  252.98 =
[info] 100000                 ArrayBufferTraversalUsingFoldLeft 1616.59 ========
[info] 100000                ArrayBufferTraversalUsingFoldRight 2508.90 ============
[info] 100000                     ArraySeqTraversalUsingForeach  396.59 ==
[info] 100000                    ArraySeqTraversalUsingFoldLeft 1819.33 =========
[info] 100000                   ArraySeqTraversalUsingFoldRight 1645.15 ========
[info] 100000                        ArrayTraversalUsingForeach 1360.20 ======
[info] 100000                       ArrayTraversalUsingFoldLeft 2635.88 =============
[info] 100000                      ArrayTraversalUsingFoldRight 3225.95 ================
 *
 */

object ListTraversalBenchmark extends App {
  Runner.main(classOf[ListTraversalBenchmark], "-Jmemory=-Xmx1G" +: args);
}

class ListTraversalBenchmark extends SimpleBenchmark {
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

  def timeJavaArrayListTraversal(reps: Int) {
    for (i <- 1 to reps) {
      var y: Int = 0
      for (i <- 0 until javaList.size()) {
        y += javaList.get(i)
      }
    }
  }

  def timeJavaArrayListAsScalaBufferConversionUsingForeach(reps: Int) {
    for (i <- 1 to reps) {
      val scalaBuffer = asScalaBuffer(javaList)
      var y: Int = 0
      scalaBuffer.foreach { x =>
        y += x
      }
    }
  }

  def timeJavaArrayListAsScalaBufferConversionUsingFoldLeft(reps: Int) {
    for (i <- 1 to reps) {
      val scalaBuffer = asScalaBuffer(javaList)
      scalaBuffer.foldLeft(0)((x, y) => x + y)
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
