package com.cologique.scalabits.circle1.scalaz

// Sample code based in part on Nick Partridge presentation http://vimeo.com/10482466

object TowardsTypeClass {

	object IntMonoid {
		def mappend(x: Int, y: Int): Int = x + y
		def mzero: Int = 0
	}
  
  def sum(xs: List[Int]): Int = xs.foldLeft(0){(x, y) => x + y}
                                                  //> sum: (xs: List[Int])Int
  def sum1(xs: List[Int]): Int = xs.foldLeft(0){_ + _}
                                                  //> sum1: (xs: List[Int])Int
                                                 
  def sum2(xs: List[Int]): Int = xs.foldLeft(IntMonoid.mzero)(IntMonoid.mappend)
                                                  //> sum2: (xs: List[Int])Int
  println(sum2(List(1, 10, 100, 1000)))           //> 1111
                                                  
  trait Monoid[A] {
  	def mappend(x: A, y: A): A
  	def mzero: A
  }
  
  object IntMonoid extends Monoid[Int] {
    def mappend(x: Int, y: Int) = x + y
    def mzero: Int = 0
  }
    
  def sum3(xs: List[Int]): Int = xs.foldLeft(IntMonoid.mzero)(IntMonoid.mappend)
                                                  //> sum3: (xs: List[Int])Int
  
  println(sum3(List(1, 10, 100, 1000)))           //> 1111
  
  def sum4(xs: List[Int])(monoid: Monoid[Int]): Int = xs.foldLeft(monoid.mzero)(monoid.mappend)
                                                  //> sum4: (xs: List[Int])(monoid: com.cologique.scalabits.circle1.scalaz.Toward
                                                  //| sTypeClass.Monoid[Int])Int
  println(sum4(List(1, 10, 100, 1000))(IntMonoid))//> 1111
  
  def sum5[T](xs: List[T])(monoid: Monoid[T]): T = xs.foldLeft(monoid.mzero)(monoid.mappend)
                                                  //> sum5: [T](xs: List[T])(monoid: com.cologique.scalabits.circle1.scalaz.Towar
                                                  //| dsTypeClass.Monoid[T])T
  println(sum5(List(1, 10, 100, 1000))(IntMonoid))//> 1111
                                                  
  def sum6[T](xs: List[T])(implicit monoid: Monoid[T]): T = xs.foldLeft(monoid.mzero)(monoid.mappend)
                                                  //> sum6: [T](xs: List[T])(implicit monoid: com.cologique.scalabits.circle1.sca
                                                  //| laz.TowardsTypeClass.Monoid[T])T
  implicit val anIntMonoid = IntMonoid            //> anIntMonoid  : com.cologique.scalabits.circle1.scalaz.TowardsTypeClass.IntM
                                                  //| onoid.type = com.cologique.scalabits.circle1.scalaz.TowardsTypeClass$$anonf
                                                  //| un$main$1$IntMonoid$2$@5e176f
  println(sum6(List(1, 10, 100, 1000)))           //> 1111
  
  
}