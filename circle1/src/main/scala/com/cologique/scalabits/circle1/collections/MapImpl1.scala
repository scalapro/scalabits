package com.cologique.scalabits.circle1.collections

import scala.collection.immutable.Map
import scala.collection.mutable.ArrayBuffer

class MapImpl1[Key, +Value](private val list: List[(Key, Value)]) extends scala.collection.immutable.Map[Key, Value] {

  override def get(key: Key): Option[Value] = list find ((entry) => entry._1 == key) map (_._2)

  override def iterator: Iterator[(Key, Value)] = list.iterator
  
  private def remove(key: Key) = {
    val index = list.indexWhere((entry: (Key, Value)) => entry._1 == key)
    index match {
      case -1 => list
      case _ => list.take(index) ++ list.drop(index + 1)
    }
  }

  override def +[SuperValue >: Value](kv: (Key, SuperValue)): Map[Key, SuperValue] = 
    new MapImpl1(kv :: remove(kv._1))

  override def -(key: Key): Map[Key, Value] = new MapImpl1(remove(key))

}

object MapImpl1 {

  def apply[Key, Value](entries: (Key, Value)*): MapImpl1[Key, Value] = {
    new MapImpl1(entries.toList)
  }
  
  def main(args: Array[String]) = {
    val map = MapImpl1((1 -> "one"))
    println(map(1))
    val map1 = map - 1
    println(map1)
    val map2 = map + (2 -> "two")
    println(map2)
    val pair = map2.find {(entry) => entry._2.startsWith("t")}
    println(pair)
  }

}