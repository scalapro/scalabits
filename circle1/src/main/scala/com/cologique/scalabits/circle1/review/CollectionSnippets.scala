package com.cologique.scalabits.circle1.review

import scala.io.Source

object CollectionSnippets extends App {
  // Set.apply is defined in GenericCompanion according to Eclipse.
  // TODO. Trace it in the API documentation.
  // Supposedly the default immutable Set is a hash set. 
  // TODO. Trace use of hash set in the implementation code for collections.
  val set = Set(1, 2, 3)

  // Map.apply is defined in GenericMapFactory according to Eclipse.
  val map = Map(1 -> "one", 2 -> "two")
  // Max on maps gets entry with highest key.
  println(map.max)

  // List has its own apply.
  val list = List(1, 2, 3)

  val biggerSet = set + (100, 200, 300)
  val biggerMap = map + (5 -> "five", 10 -> "10")
  // Cannot do the same with list.

  val set2 = Set(2, 3, 4)
  val union = set | set2
  val intersection = set & set2
  val diff = set &~ set2
  val s = set + 1

  // Colon operator invoked via method notation. 
  val appended = list.:+(1000) // Not efficient for lists.
  println(appended)

  val appended1 = list :+ 1000 // Not efficient for lists.
  println(appended1)

  val l2 = 1000 +: list // Efficient.
  val l3 = 1000 :: list

  val vector = Vector(1, 2, 3)
  val v1 = 1000 +: vector
  println(v1)
  // val v2 = 1000 :: vector // Wrong! :: not defined for vector.

  // Colon operators associate on the colon side.
  val l4 = 1000 +: 2000 +: list
  println(l4)

  val v4 = 1000 +: 2000 +: vector
  println(v4)

  val v5 = v4 :+ 10 // Efficient for vectors.

  /**
   * The lazy stream of mapped lines plus the source,
   * So the source may be closed when we are done.
   */
  def mapFileLines[A](filename: String, f: String => A): (Source, Stream[A]) = {
    val source = Source.fromFile("lines.txt")
    val lines = source.getLines.toStream
    val output = lines map f
    (source, output)
  }

  // Puzzler. What will be printed when?
  println("step 1")
  val (source, units) = mapFileLines("lines.txt", { println(_) })
  println("step 2")
  val units1 = units.take(2).force
  println("step 3")
  val units2 = units.force
  println("end")
  source.close()
  // What if we skip step 2?
  
  def from(n: Int): Stream[Int] = n #:: from(n + 1)
  
  println(from(2).take(5))
  println(from(2).take(5).force)
  
  val powers = (0 until 10).view.map(1 + _)
  println(powers)
  println(powers.force)

}