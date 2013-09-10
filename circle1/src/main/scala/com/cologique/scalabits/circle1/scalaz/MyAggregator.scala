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
   
