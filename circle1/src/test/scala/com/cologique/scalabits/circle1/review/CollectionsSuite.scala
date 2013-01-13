package com.cologique.scalabits.circle1.review
import org.scalatest.FunSuite

class CollectionsSuite extends FunSuite {
  test("List") {

    val list = List()
    assert(list.size === 0)
   
    val listOfOneInt = List(1)
    assert(listOfOneInt.iterator.getClass.toString() === "class scala.collection.LinearSeqLike$$anon$1" )
    val yieldedListOfOne = for( i <- listOfOneInt) yield i
    assert( yieldedListOfOne === List(1))
  }
}