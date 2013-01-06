package com.cologique.scalabits.circle1.review

/**
 * Pattern matching examples.
 */
object PatternMatching extends App {

  val chars = "+-1234X"

  // Watch out for non-exhaustive matches not caught by the compiler. 
  
  chars.foreach { ch: Char =>
    val sign = ch match {
      case '+' => 1
      case '-' => -1
      // Guard goes with the case. Not with the result.
      case _ if (Character.isDigit(ch)) => Character.digit(ch, 10)
      case _ => 0 // Forgetting to default here causes *runtime* MatchError.
    }
    println(ch + " => " + sign)
  }

  // Matching list elements.

  val list = List(1, 2, 3)
  list match {
    case List(el1, el2, el3) => println(el3)
    case _ => println
  }

  // Matching Cons.
  
  list match {
    case head :: neck :: rest => println(rest)
    case _ => println
  }

  list match {
    case _ :: _ :: chest :: Nil => println(chest)
    case _ => println
  }

  list match {
    case head :: tail => println(tail)
    case _ => println
  }
  
  // Patterns in assignments.
  // Works if the companion object has an unapply.
  val List(_, _, x3, _*) = list 
  println(x3)
  
  // Matching against constants. 
  // Need so-called "stable identifier": Pascal variable name.

  val One = 1
  val one = 1

  list match {
    case Nil => println
    case One :: _ => println("head is 1")
  }

  2 :: list match {
    case Nil => println
    case one :: _ => println("head is 1 - wrong")
  }

  // Extracting the matched value.

  def makeList = List(1, 2, 3, 4)

  makeList match {
    case list @ 1 :: _ => {
      println("matched: " + list)
    }
    case list @ _ => println("not matched")
  }

  // Match Array.

  val array = Array(1, 2, 3, 4)
  array match {
    case a @ Array(1, rest @ _*) => {
      println(Array.unapplySeq(a))
      println(rest)
    }
    case _ => println("no match")
  }
  
  

}