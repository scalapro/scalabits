package com.cologique.scalabits.circle1.scalaz

// Sample code based in part on Nick Partridge presentation http://vimeo.com/10482466

/*
 * Generalization over containers.
 *
 * To fix ideas, consider a generic class with a single type parameter as a contanier of objects
 * of the generic type parameter. We need to generailize the notion of a sum of all the slots
 * of a container. We already have a general notion of a monoid for summing two things, and
 * for providing an idenetity (zero) element. The thing we want to generalize is the idea of
 * iterative summation, or aggregation for different containers. This would be a generic
 * function which takes a container type as a parameter and aggregates the slots of the
 * container by using a given monoid. It should work for different container types, e.g., List,
 * Vector, Array, etc. So the "parameter" to this function is a generic type, that is, it
 * takes on the values List, Vector, etc. Such a parameter is called a kind, and its type
 * is represented as Container[_].
 */
object KindedTypeClass {

  trait Monoid[A] {
  	def mappend(x: A, y: A): A
  	def mzero: A
  }
  
  object Monoid {
    implicit object IntMonoid extends Monoid[Int] {
      def mappend(x: Int, y: Int) = x + y
      def mzero: Int = 0
    }
    
    implicit object StringMonoid extends Monoid[String] {
      def mappend(x: String, y: String) = x + y
      def mzero: String = ""
    }
  }
  
  trait Aggregator[Container[_]] {
    def aggregate[Element](container: Container[Element], monoid: Monoid[Element]): Element
  }
  
  object Aggregator {
    implicit object ListAggregator extends Aggregator[List] {
      def aggregate[Element](list: List[Element], monoid: Monoid[Element]): Element =
        list.foldLeft(monoid.mzero)(monoid.mappend)
    }
    
    implicit object ArrayAggregator extends Aggregator[Array] {
      def aggregate[Element](array: Array[Element], monoid: Monoid[Element]): Element =
        array.foldLeft(monoid.mzero)(monoid.mappend)
    }
  }
   
  // Scala allows one implicit parameter list at the end.
  def aggregate[T](xs: List[T])(implicit monoid: Monoid[T], aggregator: Aggregator[List]): T =
    aggregator.aggregate(xs, monoid)              //> aggregate: [T](xs: List[T])(implicit monoid: com.cologique.scalabits.circle
                                                  //| 1.scalaz.KindedTypeClass.Monoid[T], implicit aggregator: com.cologique.scal
                                                  //| abits.circle1.scalaz.KindedTypeClass.Aggregator[List])T
 
  println(aggregate(List(1, 2, 3, 4)))            //> 10
  
  def aggregateKind[Container[_], Element](elements: Container[Element])(implicit aggregator: Aggregator[Container], monoid: Monoid[Element]): Element =
    aggregator.aggregate(elements, monoid)        //> aggregateKind: [Container[_], Element](elements: Container[Element])(implic
                                                  //| it aggregator: com.cologique.scalabits.circle1.scalaz.KindedTypeClass.Aggre
                                                  //| gator[Container], implicit monoid: com.cologique.scalabits.circle1.scalaz.K
                                                  //| indedTypeClass.Monoid[Element])Element
  println(aggregateKind(List(1, 10, 100, 1000)))  //> 1111
  println(aggregateKind(Array(1, 10, 100, 1000))) //> 1111
  
  println(aggregateKind(Array("a", "bb", "ccc", "dddd")))
                                                  //> abbcccdddd
  
  {
    implicit val multMonoid = new Monoid[Int] {
      def mappend(x: Int, y: Int) = x * y
      def mzero: Int = 1
    }
    
    /*
     * To pass some of the parameters of the implicit parameter list explicitly and others implicitly,
     * use the implicitly built-in function for those parameters to be picked up implicitly. It is defined as:
     * implicitly[T](implicit t: T): T = t // That is just pick up an implicit value of the given type.
     */
                                                
  	println(aggregateKind(List(1, 2, 3))(implicitly[Aggregator[List]], multMonoid))
  }                                               //> 6
}