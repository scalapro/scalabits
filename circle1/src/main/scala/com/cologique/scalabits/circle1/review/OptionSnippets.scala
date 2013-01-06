package com.cologique.scalabits.circle1.review

object OptionSnippets extends App {

  def double(x: Int) = 2 * x
  def increment(x: Int) = x + 1

  val table = Map(1 -> 100, 2 -> 200)

  val keys = List(1, 2, 3, 4)
  
  // Compose table lookup with function application propagating Options.
  def lookupThenApply(f: Int => Int)(key: Int): Option[Int] = table.get(key) map f

  // Transform table values for given keys using options to indicate existence.
  val maybeDoubleValues = keys map lookupThenApply(increment)
  println(maybeDoubleValues)

  // Flattening a sequence of options removes Somes and Nones.
  val realDoubleValues = keys flatMap lookupThenApply(double)
  println(realDoubleValues)

}