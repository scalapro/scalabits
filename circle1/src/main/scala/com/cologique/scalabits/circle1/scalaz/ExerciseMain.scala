package com.cologique.scalabits.circle1.scalaz

object ExerciseMain extends App {
  def sum[Element](list: List[Element])(implicit addable: Addable[Element]) = (addable.identity /: list)(addable.+)
  
  println(sum(List(1, 2, 3)))
  println(sum(List("x", "11")))
  
  def csum[Element, Container[_]](list: List[Container[Element]])(implicit addable: AddableKind[Container]) = 
    (addable.identity[Element] /: list)(addable.+)
    
  println(csum(List(Vector(1, 2, 3), Vector(100, 200))))

}