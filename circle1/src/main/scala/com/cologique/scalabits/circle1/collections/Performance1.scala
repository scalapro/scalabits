package com.cologique.scalabits.circle1.collections

import scala.collection.mutable.ArrayBuffer

object Timing {

  /**
   * Computational complexity. The string is the symbolic name of the complexity measure.
   * The function is the 'Order' of complexity based on the size of the problem space.
   */
  type Complexity = (String, Double => Double)

  def constant(n: Double): Double = 1
  def logN(n: Double) = math.log(n)
  def linear(n: Double) = n

  // URGENT Q. Why is _ needed for passing functions in some places and not in others?

  val LogN = ("logN", logN _)
  val Linear = ("linear", linear _)
  val Constant = ("constant", constant _)

  /**
   * Perform and time a repeated operation and print out timing information.
   *
   * @param name The symbolic name of the operation to print.
   * @param looper - The function to call and time.
   * 	This function is assumed to internally include 'iterations' loops of the named operation.
   * @param iterations The number of loops executed internally by the looper.
   * @param complexity The supposed computational complexity of the operation internally repeated by the looper.
   */
  def time(name: String, size: Int, looper: => Any, iterations: Int, complexity: Complexity) {
    val start = System.nanoTime
    looper
    val time = System.nanoTime - start
    printf("%-21s(size: %10d, iters: %10d) ", name, size, iterations)
    printf("%12d total nanos - ", time)
    printf("per-operation-time/%-11s", complexity._1 + ":")
    printf("%10.5f", time / (iterations * complexity._2(size)))
    println
  }
}

object Performance1 {

  // Shorthands.
  def time = Timing.time _
  type Complexity = Timing.Complexity
  val LogN = Timing.LogN
  val Linear = Timing.Linear
  val Constant = Timing.Constant

  abstract class CollectionExerciser[Collection](val size: Int, val iterations: Int) {

    val collection = build(size)
    val indices = randomIndices

    // Abstract methods provided by concrete sub-classes.

    def build(size: Int): Collection
    def get(coll: Collection, index: Int): Int
    def update(coll: Collection, index: Int, value: Int): Collection
    def append(coll: Collection, value: Int): Collection
    def prepend(coll: Collection, value: Int): Collection

    // Concrete methods.

    def loopGet(complexity: Complexity) = {
      val loop = {
        var s = 0
        indices foreach { s += get(collection, _) }
        s
      }
      time("get", size, loop, iterations, complexity)
    }

    def loopUpdate(complexity: Complexity) = {
      var c = collection
      val loop = {
        indices foreach { i => c = update(c, i, i) }
        c
      }
      time("update", size, loop, iterations, complexity)
    }

    def loopPrepend(complexity: Complexity) = {
      var c = collection
      val loop = {
        indices foreach { i => c = prepend(c, i) }
        c
      }
      time("prepend", size, loop, iterations, complexity)
    }

    def loopAppend(complexity: Complexity) = {
      var c = collection
      val loop = {
        indices foreach { i => c = append(c, i) }
        c
      }
      time("append", size, loop, iterations, complexity)
    }

    /**
     * Generate and store a random set of collection indexes for repeatedly accessing
     * collection elements in timing tests.
     * 
     * Separates the generation of random indexes into collections from timed
     * operations on collections. Removes the overhead of random number generation
     * from timing measurements.
     */
    def randomIndices = {
      val indices = new Array[Int](iterations)
      (0 until iterations) foreach { indices(_) = (math.random * size).toInt }
      indices
    }
  }

  class ArrayExerciser(size: Int, iterations: Int) extends CollectionExerciser[Array[Int]](size, iterations) {
    def build(size: Int) = new Array[Int](size)
    def get(array: Array[Int], index: Int) = array(index)
    def update(array: Array[Int], index: Int, value: Int) = { array(index) = value; array }
    def append(array: Array[Int], value: Int) = throw new UnsupportedOperationException()
    def prepend(array: Array[Int], value: Int) = throw new UnsupportedOperationException()
  }

  object ArrayExerciser {
    def apply(size: Int, iterations: Int) = new ArrayExerciser(size, iterations)
  }

  class ArrayBufferExerciser(size: Int, iterations: Int) extends CollectionExerciser[ArrayBuffer[Int]](size, iterations) {
    def build(size: Int) = ArrayBuffer[Int]((0 until size): _*)

    // URGENT. Why does the following throw an index out of bounds exception? But the next line does not?
    // def build(size: Int) = {val buf = ArrayBuffer[Int](); (0 until size) foreach { buf += 0 }; buf}
    // def build(size: Int) = { val buf = ArrayBuffer[Int](); (0 until size) foreach { buf += _ }; buf }

    def get(buf: ArrayBuffer[Int], index: Int) = buf(index)
    def update(buf: ArrayBuffer[Int], index: Int, value: Int) = { buf(index) = value; buf }
    def append(buf: ArrayBuffer[Int], value: Int) = { buf += value; buf }
    def prepend(buf: ArrayBuffer[Int], value: Int) = { value +=: buf; buf }
  }

  object ArrayBufferExerciser {
    def apply(size: Int, iterations: Int) = new ArrayBufferExerciser(size, iterations)
  }

  class VectorExerciser(size: Int, iterations: Int) extends CollectionExerciser[Vector[Int]](size, iterations) {
    def build(size: Int) = Vector((0 until size): _*)
    def get(vector: Vector[Int], index: Int) = vector(index)
    def update(vector: Vector[Int], index: Int, value: Int) = vector.updated(index, value)
    def append(vector: Vector[Int], value: Int) = vector :+ value
    def prepend(vector: Vector[Int], value: Int) = value +: vector
  }

  object VectorExerciser {
    def apply(size: Int, iterations: Int) = new VectorExerciser(size, iterations)
  }

  class ListExerciser(size: Int, iterations: Int) extends CollectionExerciser[List[Int]](size, iterations) {
    def build(size: Int) = (0 until size).toList
    def get(list: List[Int], index: Int) = list(index)
    def update(list: List[Int], index: Int, value: Int) = list.updated(index, value)
    def append(list: List[Int], value: Int) = list :+ value
    def prepend(list: List[Int], value: Int) = value :: list
  }

  object ListExerciser {
    def apply(size: Int, iterations: Int) = new ListExerciser(size, iterations)
  }

  def printHeader(what: String) = {
    println
    println(what)
    println(what map { _ => '-' })
  }

  def main(args: Array[String]) = {

    val Iterations = 10000
    val sizes = Stream.iterate(1000, 6)(_ * 2)

    printHeader("Array")
    sizes foreach { size => ArrayExerciser(size, Iterations).loopGet(Constant) }; println("-")
    sizes foreach { size => ArrayExerciser(size, Iterations).loopUpdate(Constant) }

    printHeader("ArrayBuffer")
    sizes foreach { size => ArrayBufferExerciser(size, Iterations).loopGet(Constant) }; println("-")
    sizes foreach { size => ArrayBufferExerciser(size, Iterations).loopUpdate(Constant) }; println("-")
    sizes foreach { size => ArrayBufferExerciser(size, Iterations).loopAppend(Constant) }; println("-")
    sizes foreach { size => ArrayBufferExerciser(size, Iterations).loopPrepend(Linear) }

    printHeader("Vector")
    sizes foreach { size => VectorExerciser(size, Iterations).loopGet(LogN) }; println("-")
    sizes foreach { size => VectorExerciser(size, Iterations).loopUpdate(LogN) }; println("-")
    sizes foreach { size => VectorExerciser(size, Iterations).loopAppend(LogN) }; println("-")
    sizes foreach { size => VectorExerciser(size, Iterations).loopPrepend(LogN) }

    printHeader("List")
    sizes foreach { size => ListExerciser(size, Iterations).loopGet(Linear) }; println("-")
    sizes foreach { size => ListExerciser(size, Iterations).loopUpdate(Linear) }; println("-")
    sizes foreach { size => ListExerciser(size, Iterations).loopAppend(Linear) }; println("-")
    sizes foreach { size => ListExerciser(size, Iterations).loopPrepend(Constant) }
  }

}
