package com.cologique.scalabits.circle1.review

object FunctionSnippets extends App {
  
  case class Point(val x: Double = 0, y: Double = 0, z: Double = 0) {
    def onMyPlane(that: Point) = z == that.z
  }
  
  // Default arguments and named arguments.
  println(Point)
  println(Point(1, 2))
  println(Point(z = 100, x = 5))
  
  val special = Point(0, 0, 0)
  
  val pointList = List(Point(100, 200, 0), Point(2, 1, 100))
  
  val coplanar1 = pointList.filter (special.onMyPlane)
  println(coplanar1)
  
  // Close a method w.r.t. to an object.
  val onSpecialsPlane = special.onMyPlane _
  val coplanar2 = pointList.filter(onSpecialsPlane)
  println(coplanar2)
  
}