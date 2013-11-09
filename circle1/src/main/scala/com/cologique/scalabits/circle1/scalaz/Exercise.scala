package com.cologique.scalabits.circle1.scalaz

import scala.collection.mutable.WrappedArray

/*
 * A generic concept that is represented by a set of related functions 
 * on a generic data structure is embodied in a trait just methods.
 * This allows us to write generic algorithms based only on the
 * concept of addability.
 */
trait Addable[T] {
  def identity: T
  def +(x: T, y: T): T
}

/*
 * An example of a generic algorithm using the generic notion of addability.
 */
object Client {
  def summer[T](list: List[T])(adder: Addable[T]) = (adder.identity /: list)(adder.+)
}

/*
 * The notion of addability is inherent in Int, Double, String, etc. But the 
 * corresponding classes are out of our control. So we need to explicitly
 * provide an adder to our generic algorithm, when we actually use it
 * to do something concrete.
 */

/*
 * Since all we have are functions, a concrete instance of this
 * generic concept does not need to be a class - it will just have 
 * static functions - so it can just be an object in Scala.
 */
case object DoubleAddable extends Addable[Double] {
  def identity = 0.0
  def +(x: Double, y: Double) = x + y
}

object Test1 {
  def sum(list: List[Double]) = Client.summer(list)(DoubleAddable)
}

/*
 * If an implementation like DoubleAddable is used a lot and it is fairly
 * obvious from the context that an implementation of the generic notion
 * is needed in a call, then it reduces clutter if the compiler could 
 * supply the correct implementation of the generic notion in a given
 * context. That is where implicits come in
 */
object Client2 {
  implicit val DoubleAdder = DoubleAddable
  
  def summer[T](list: List[T])(implicit adder: Addable[T]) = (adder.identity /: list)(adder.+)
}

object Test2 {
  import Client2._
  val sum = summer(List(1.0, 2.1))
}

/*
 * Arguably it is clear from context what adder should be used for doubles
 * in summing a list of doubles, so having to always include it as a parameter
 * is just noise.
 */

/*
 * Next even the import statement to get the needed implementation into
 * context is noise. It would be nice if there were a defined place for 
 * such implementations that the compiler just knows about, and searches
 * for the right implementation there. That defined place in Scala is
 * the companion object of the generic trait defining the generic concept.
 */
object Addable {
  implicit object IntAaddable extends Addable[Int] {
    def identity = 0
    def +(x: Int, y: Int) = x + y
  }
  
  implicit object StringAddable extends Addable[String] {
    def identity = ""
    def +(x: String, y: String) = x + y
  }
  
  implicit object IntListAddable extends Addable[List[Int]] {
    def identity = Nil
    def +(list1: List[Int], list2: List[Int]) = list1 ++ list2
  }
}

/*
 * Unfortunately the idea of addability as embodied in the above generic trait
 * does not extend to generic collections or data structures that themselves have 
 * generic parameters. As shown above, the actual type may be a concrete list,
 * like List[Int], but not a generic List, since in Scala objects must be concrete.
 * To capture the idea of additivity for generic collections, we need the generic
 * parameter of the trait to be a "kind", that is, something that can take a 
 * generic type as a parameter. A generic kind is represented in Scala as 
 * Kind[_].
 */

trait AddableKind[Container[_]] {
  def identity[Element]: Container[Element]
  def +[Element](x: Container[Element], y: Container[Element]): Container[Element]
}

object AddableKind {
  implicit object ListAddable extends AddableKind[List] {
    def identity[Element] = Nil
    def +[Element](list1: List[Element], list2: List[Element]) = list1 ++ list2
  }
  
  implicit object VectorAddable extends AddableKind[Vector] {
    def identity[Element] = Vector[Element]()
    def +[Element](v1: Vector[Element], v2: Vector[Element]) = v1 ++ v2
  }

  // Attempting to do it for Array or WrappedArray too complicated for now.
}
