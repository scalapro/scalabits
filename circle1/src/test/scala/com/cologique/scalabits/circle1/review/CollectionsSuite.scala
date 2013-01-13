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
  
  test("zip List[Int] and List[Char]") {
    val keys = List(1,2)
    val vals = List('a','b')
    val map = keys.zip(vals).toMap[Int, Char]
    assert( map.getClass.toString === "class scala.collection.immutable.Map$Map2")
    assert(map === Map(1 -> 'a', 2 -> 'b'))
  }
}