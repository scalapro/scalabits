package com.cologique.scalabits.circle1.review
import scala.collection.mutable.ListBuffer

object Puzzler extends App {
  
  def fixture =
    new {
      val builder = new StringBuilder("ScalaTest is ")
      val buffer = new ListBuffer[String]
    }

  // What is the type of this variable and why?
  val strange = 1 + null
  
  val f1 = fixture
  f1.builder.append("a")
  val f2 = fixture
  println( f1.builder.toString() )
  
}