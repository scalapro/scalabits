package com.cologique.scalabits.circle1.scalaz

// Sample code based in part on Nick Partridge presentation http://vimeo.com/10482466.

object PimpItAll {

  /**
   * Wrap any value creating a pimp for it that can provide additional methods.
   */
  trait IdentityPimp[T] {
    val value: T

    def plus(other: T)(implicit monoid: MyMonoid[T]) = monoid.mappend(value, other)
    def |+|(other: T)(implicit monoid: MyMonoid[T]) = plus(other)
  }

  /**
   * Implicit converter to pimp values.
   */
  implicit def identityPimper[T](t: T): IdentityPimp[T] = new IdentityPimp[T] {
    val value = t
  }                                               //> identityPimper: [T](t: T)com.cologique.scalabits.circle1.scalaz.PimpItAll.Id
                                                  //| entityPimp[T]

  /**
   * To pimp containers, we need to create a different pimper that gets the container kind
   * and the element type as generic parameters.
   */
  
  trait ContainerIdentityPimp[Container[_], Element] {
    val container: Container[Element]

    def sum(implicit monoid: MyMonoid[Element], aggregator: MyAggregator[Container]) =
      aggregator.aggregate(container, monoid)
  }

  /**
   * Implicit converter to pimp containers.
   */
  implicit def containerIdentityPimper[Container[_], Element](c: Container[Element]): ContainerIdentityPimp[Container, Element] =
    new ContainerIdentityPimp[Container, Element] {
      val container = c
  }                                               //> containerIdentityPimper: [Container[_], Element](c: Container[Element])com.
                                                  //| cologique.scalabits.circle1.scalaz.PimpItAll.ContainerIdentityPimp[Containe
                                                  //| r,Element]


  println(3 plus 4)                               //> 7
  println("x" plus "y")                           //> xy
  println(3 |+| 10)                               //> 13
  
  println(List(1, 2).sum)                         //> 3

 
}