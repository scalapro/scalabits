package com.cologique.scalabits.circle1.scalaz

// Sample code based in part on Nick Partridge presentation http://vimeo.com/10482466

object FirstTypeClass {

  trait Monoid[A] {
  	def mappend(x: A, y: A): A
  	def mzero: A
  }
  
  /**
   * Put implementations of Monoid for various types as implicits in the companion object.
   * This makes them available as implicits Monoid parameters in methods.
   */
  object Monoid {
    implicit object IntMonoid extends Monoid[Int] {
      def mappend(x: Int, y: Int) = x + y
      def mzero: Int = 0
    }
    
    implicit object TheStringMonoid extends Monoid[String] {
      def mappend(x: String, y: String) = x + y
      def mzero: String = ""
    }
  }
  
  def summ[T](xs: List[T])(implicit monoid: Monoid[T]): T = xs.foldLeft(monoid.mzero)(monoid.mappend)
                                                  //> summ: [T](xs: List[T])(implicit monoid: com.cologique.scalabits.circle1.scal
                                                  //| az.FirstTypeClass.Monoid[T])T
                                                  
  // The implicit parameter is sought in the current context and in the companion object of the parameter's type.
  println(summ(List(1, 10, 100, 1000)))           //> 1111
  
  println(summ(List("a", "bb", "ccc", "dddd")))   //> abbcccdddd
  
  {
    // An implicit in the current context overrides an implicit in the type's companion object.
    implicit val multMonoid = new Monoid[Int] {
      def mappend(x: Int, y: Int) = x * y
      def mzero: Int = 1
    }
    println(summ(List(1, 10, 100, 1000)))
  
  }                                               //> 1000000
  
}