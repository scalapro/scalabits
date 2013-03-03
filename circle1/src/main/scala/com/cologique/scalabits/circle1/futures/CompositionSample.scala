package com.cologique.scalabits.circle1.futures

// Originally obtained from: 
// http://blog.knoldus.com/2013/01/12/akka-futures-in-scala-with-a-simple-example/

// Based on Akka 2.0. 
// http://doc.akka.io/api/akka/2.0/#akka.dispatch.Future

import akka.dispatch.Future
import akka.actor.ActorSystem

object SumApplicationWithFutures extends App {

  implicit val system = ActorSystem("future")

  val startTime = System.currentTimeMillis

  /* 
   * The code below uses akka.dispatch.Future's companion apply function to construct futures.
   * 
   * object akka.dispatch.Future:
   * 
   *     def apply [T] (body: ⇒ T)(implicit executor: ExecutionContext): Future[T] 
   * 
   * The important thing to note is that the parameter "body" is annotated with a "=>"
   * meaning it is a call-by-name parameter. 
   * 
   * So when we construct a future as Future(block) the block is not evaluated immediately, 
   * but is substituted in the body of apply, which presumably gives it to a dispatcher 
   * to be executed asynchronously, and then returns immediately.
   */

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
