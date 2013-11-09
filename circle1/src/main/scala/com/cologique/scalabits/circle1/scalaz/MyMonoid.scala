package com.cologique.scalabits.circle1.scalaz

trait MyMonoid[A] {
  def mappend(x: A, y: A): A
  def mzero: A
}

/**
 * Put implementations of Monoid for various types as implicits in the companion object.
 * This makes them available as actual parameters to method that have an implicit Monoid
 * formal parameter.
 */
object MyMonoid {

  implicit object MyIntMonoid extends MyMonoid[Int] {
    def mappend(x: Int, y: Int) = x + y
    def mzero: Int = 0
  }

  implicit object MyStringMonoid extends MyMonoid[String] {
    def mappend(x: String, y: String) = x + y
    def mzero: String = ""
  }
}

object Main {

  def main(args: Array[String]): Unit = {
    def plus(x: Int, y: Int)(implicit monoid: MyMonoid[Int]) = monoid.mappend(x, y)
    val sum = plus(4, 5)
    println(sum)
  }
}

