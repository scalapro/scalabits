package com.cologique.scalabits.circle1.scalaz

import scalaz._
import Scalaz._

// Sample code based in part on Nick Partridge presentation http://vimeo.com/10482466.

object ScalazDemo1 {

  // Monoid.
  List(1, 2) |+| List(3, 4)                       //> res0: List[Int] = List(1, 2, 3, 4)

  // Non-empty list.
  val one = 1.wrapNel                             //> one  : scalaz.NonEmptyList[Int] = NonEmptyList(1)
  1.wrapNel.toList |+| List(2, 3)                 //> res1: List[Int] = List(1, 2, 3)

  // Is there an object defined for this?
  object nf extends NonEmptyListFunctions

  nf.nel(1, Nil)                                  //> res2: scalaz.NonEmptyList[Int] = NonEmptyList(1)

  val opt = Some(5)                               //> opt  : Some[Int] = Some(5)
  val mapped = opt some { _ * 2 } none 0          //> mapped  : Int = 10
  ~opt                                            //> res3: Int = 5

  ~(None: Option[Int])                            //> res4: Int = 0
  ~(None: Option[String])                         //> res5: String = ""
  ~(None: Option[List[Int]])                      //> res6: List[Int] = List()

  val op1 = some(5)                               //> op1  : Option[Int] = Some(5)

  // Cross product.
  some(4) <|*|> some(10)                          //> res7: Option[(Int, Int)] = Some((4,10))
  some(4) <|*|> (none: Option[String])            //> res8: Option[(Int, String)] = None

  List(3, 4) <|*|> List(5, 6)                     //> res9: List[(Int, Int)] = List((3,5), (3,6), (4,5), (4,6))

  case class X[T](t: T)
  case class Y[T](t: T)

  val x = X[Int](100)                             //> x  : com.cologique.scalabits.circle1.scalaz.ScalazDemo1.X[Int] = X(100)
  val y = Y[Int](200)                             //> y  : com.cologique.scalabits.circle1.scalaz.ScalazDemo1.Y[Int] = Y(200)

  // x <|*|> x
  // x <|*|> y

  // How do you defne cross product of new generic types?

  def even(x: Int): Validation[NonEmptyList[String], Int] =
    if (x % 2 == 0) { x.success } else { ("%d is not even" format x).wrapNel.fail }
                                                  //> even: (x: Int)scalaz.Validation[scalaz.NonEmptyList[String],Int]

  even(2)                                         //> res10: scalaz.Validation[scalaz.NonEmptyList[String],Int] = Success(2)
  even(3)                                         //> res11: scalaz.Validation[scalaz.NonEmptyList[String],Int] = Failure(NonEmpt
                                                  //| yList(3 is not even))

  even(2) <|*|> even(6)                           //> res12: scalaz.Unapply[scalaz.Apply,scalaz.Validation[scalaz.NonEmptyList[St
                                                  //| ring],Int]]{type M[X] = scalaz.Validation[scalaz.NonEmptyList[String],X]; t
                                                  //| ype A = Int}#M[(Int, Int)] = Success((2,6))
  even(2) <|*|> even(3)                           //> res13: scalaz.Unapply[scalaz.Apply,scalaz.Validation[scalaz.NonEmptyList[St
                                                  //| ring],Int]]{type M[X] = scalaz.Validation[scalaz.NonEmptyList[String],X]; t
                                                  //| ype A = Int}#M[(Int, Int)] = Failure(NonEmptyList(3 is not even))
  even(5) <|*|> even(3)                           //> res14: scalaz.Unapply[scalaz.Apply,scalaz.Validation[scalaz.NonEmptyList[St
                                                  //| ring],Int]]{type M[X] = scalaz.Validation[scalaz.NonEmptyList[String],X]; t
                                                  //| ype A = Int}#M[(Int, Int)] = Failure(NonEmptyList(5 is not even, 3 is not e
                                                  //| ven))

  even(2) <|*|> even(4) <|*|> even(6)             //> res15: scalaz.Unapply[scalaz.Apply,scalaz.Validation[scalaz.NonEmptyList[St
                                                  //| ring],Int]]{type M[X] = scalaz.Validation[scalaz.NonEmptyList[String],X]; t
                                                  //| ype A = Int}#M[((Int, Int), Int)] = Success(((2,4),6))
  even(1) <|*|> even(3) <|*|> even(5)             //> res16: scalaz.Unapply[scalaz.Apply,scalaz.Validation[scalaz.NonEmptyList[St
                                                  //| ring],Int]]{type M[X] = scalaz.Validation[scalaz.NonEmptyList[String],X]; t
                                                  //| ype A = Int}#M[((Int, Int), Int)] = Failure(NonEmptyList(1 is not even, 3 i
                                                  //| s not even, 5 is not even))
  
  // TODO. Get right idiom for combining an indefinite number of validation in flat tuples.
  // How do you flatten tuples?

}