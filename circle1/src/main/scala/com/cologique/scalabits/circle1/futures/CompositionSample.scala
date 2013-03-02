package com.cologique.scalabits.circle1.futures

// Originally obtained from: 
// http://blog.knoldus.com/2013/01/12/akka-futures-in-scala-with-a-simple-example/

import akka.dispatch.Future
import akka.actor.ActorSystem

object SumApplicationWithFutures extends App {

  implicit val system = ActorSystem("future")

  val startTime = System.currentTimeMillis

  val future1 = Future(timeTakingIdentityFunction(1))
  val future2 = Future(timeTakingIdentityFunction(2))
  val future3 = Future(timeTakingIdentityFunction(3))

  val future = for {
    x <- future1
    y <- future2
    z <- future3
  } yield (x + y + z)

  future onSuccess {
    case sum =>
      val elapsedTime = ((System.currentTimeMillis - startTime) / 1000.0)
      println("Sum of 1, 2 and 3 is " + sum + " calculated in " + elapsedTime + " seconds")
      shutdown
  }
  
  def shutdown = system.shutdown

  def timeTakingIdentityFunction(number: Int) = {
    // we sleep for 3 seconds and return number
    Thread.sleep(3000)
    number
  }
}
