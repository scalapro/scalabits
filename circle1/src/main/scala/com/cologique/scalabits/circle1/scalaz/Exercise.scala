package com.cologique.scalabits.circle1.scalaz

import scala.collection.mutable.WrappedArray

trait Addable[T] {
  val identity: T
  def +(x: T, y: T): T
}

object Addable {
  implicit object IntAaddable extends Addable[Int] {
    val identity = 0
    def +(x: Int, y: Int) = x + y
  }
  
  implicit object StringAddable extends Addable[String] {
    val identity = ""
    def +(x: String, y: String) = x + y
  }
  
}

trait AddableKind[Container[_]] {
  def identity[Element]: Container[Element]
  def +[Element](x: Container[Element], y: Container[Element]): Container[Element]
}

object AddableKind {
  implicit object ListAddable extends AddableKind[List] {
    def identity[Element] = Nil
    def +[Element](list1: List[Element], list2: List[Element]) = list1 ++ list2
  }
  
  implicit object VectorAddable extends AddableKind[Vector] {
    def identity[Element] = Vector[Element]()
    def +[Element](v1: Vector[Element], v2: Vector[Element]) = v1 ++ v2
  }

  // Attempting to do it for Array or WrappedArray too complicated for now.
}
