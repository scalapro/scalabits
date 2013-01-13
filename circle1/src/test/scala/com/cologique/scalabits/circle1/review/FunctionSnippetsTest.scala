package com.cologique.scalabits.circle1.review

import org.scalatest._
import scala.collection.mutable.Stack

class FunctionSnippetsTest extends FlatSpec {
  
  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    assert(stack.pop() === 2)
    assert(stack.pop() === 1)
  }

}