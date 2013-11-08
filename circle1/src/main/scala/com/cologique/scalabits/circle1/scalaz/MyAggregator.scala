package com.cologique.scalabits.circle1.scalaz

trait MyAggregator[Container[_]] {
  def aggregate[Element](container: Container[Element], monoid: MyMonoid[Element]): Element
}

object MyAggregator {
  implicit object ListAggregator extends MyAggregator[List] {
    def aggregate[Element](list: List[Element], monoid: MyMonoid[Element]): Element =
      list.foldLeft(monoid.mzero)(monoid.mappend)
  }

  implicit object ArrayAggregator extends MyAggregator[Array] {
    def aggregate[Element](array: Array[Element], monoid: MyMonoid[Element]): Element =
      array.foldLeft(monoid.mzero)(monoid.mappend)
  }
}

object AggMain {

  def main(args: Array[String]) {
    val list1 = List(1, 2, 3, 4)
    val list2 = List(5, 6)
    
    def sum[Container[_], Element](list: Container[Element])(implicit aggregator: MyAggregator[Container], monoid: MyMonoid[Element]) =
      aggregator.aggregate(list, monoid)

    val s = sum(list1)
    println(s)
  }
}

/*
 * Design note. In any solution, we would have to have a generic kind for the container, 
 * and a generic type for the element of the container. But would it not make sense
 * to have both of these generic concepts be parameters of the trait? In the above
 * solution, the generic kind of the container is a parameter of the trait, 
 * and the generic type of the container's element is a parameter of the aggregate
 * function.
 * 
 * Suppose we made the element type a generic type parameter of the trait, like this:
 * 
 * 	trait MyAggregator[Container[], Element] { ... }
 * 
 * Then in the companion object we need to define implicit objects that extend 
 * the trait, giving concrete types for both the Container and the Element. In other
 * words, we would have to define things like:
 * 
 * 	implicit object MyIntListAggregator extends MyAggregator[List, Int] { ... }
 *  
 * So we would have to repeat such definitions for each combination of container 
 * and element type. But the definition of the aggregate function is generic in
 * the element type, so we would be redundantly repeating it for every element type
 * we need to support here. 
 * 
 * The problem is that in Scala the companion object can't take a generic type 
 * parameter. We cannot do the following in Scala:
 * 
 * 	implicit object MyListAggregator[Element] extends MyAgrregator[List, Element]
 *  
 * If we could do that, then we could define the aggregate function generically
 * for the implicit objects just as in the above solution.
 */
   
