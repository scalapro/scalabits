package com.cologique.scalabits.circle1.scalaz

import MyMonoid.MyIntMonoid

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
    
    import MyMonoid._
    
    def sum[Container[_], Element](list: Container[Element])(implicit aggregator: MyAggregator[Container], monoid: MyMonoid[Element]) =
      aggregator.aggregate(list, monoid)
      
    val s = sum(list1)
    println(s)
  }
}
   
