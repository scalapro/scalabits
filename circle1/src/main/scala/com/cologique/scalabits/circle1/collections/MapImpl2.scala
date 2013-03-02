/*package com.cologique.scalabits.circle1.collections

import scala.collection.immutable.Map
import scala.collection.mutable.ArrayBuffer

class MapImpl2[Key, +Value](private val list: ArrayBuffer[(Key, Value)]) extends scala.collection.immutable.Map[Key, Value] {

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
    new MapImpl2(kv +: remove(kv._1))

  override def -(key: Key): Map[Key, Value] = new MapImpl2(remove(key))

}

object MapImpl2 {

  def apply[Key, Value](entries: (Key, Value)*): MapImpl2[Key, Value] = {
    val buffer = ArrayBuffer[(Key, Value)]()
    entries foreach { (entry) => buffer :+ entry }
    new MapImpl2(buffer)
  }

  def main(args: Array[String]) = {
    val map = MapImpl2((1 -> "one"))
    println(map(1))
    val map1 = map - 1
    println(map1)
    val map2 = map + (2 -> "two")
    println(map2)
    val pair = map2.find { (entry) => entry._2.startsWith("t") }
    println(pair)
  }

}*/