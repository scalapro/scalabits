package com.cologique.scalabits.circle1.review

trait Visibility {

  // Package private constructor.
  case class X private[review] (val x: Int) {
    def makeY(y: Int) = Y(y)
    // Internal class-specific method.
    private def double = 2 * x
    def doubleThat(that: X) = that.double
  }

  // Enclosure-private constructor.
  case class Y private[Visibility] (val y: Int) {
    // Internal instance-specific method.
    private[this] def double = 2 * y
    // def doubleThat(that: Y) = that.double // Won't compile.
    def quadruple = 2 * double
  }

  private val q = Y(1).quadruple
}

object Client extends Visibility with App {

  val x = X(1)
  println(x)
  // val y = Y(1) // Won't compile.
  val y = x.makeY(2)
  // val d = y.double // Won't compile.
  val q = y.quadruple
  println(q)
}