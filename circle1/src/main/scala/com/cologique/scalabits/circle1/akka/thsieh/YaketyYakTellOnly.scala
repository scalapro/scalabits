package com.cologique.scalabits.circle1.akka.thsieh

/**
  * User: Tony
  * Date: 3/6/13
  * Time: 11:48 PM
  * 
  *   Lyrics from the Coasters.
  *
  */

import akka.actor._
import akka.routing.RoundRobinRouter
import collection.immutable.VectorBuilder

object YaketyYakTellOnly extends App{

  sealed trait TaskMessage
  case class TaskDefinition (name: String, difficulty: Int) extends TaskMessage
  case class Assignment (task: TaskDefinition) extends TaskMessage
  case class KickOff (workList: IndexedSeq[TaskDefinition]) extends TaskMessage
  case class Results (task: TaskDefinition, message:String)
  case class AllDone (resultsList: IndexedSeq[(TaskDefinition, String)], elapsedTime:Long )

  class Child extends Actor {
    def chore(task: TaskDefinition): String = {
      // fake work
      Thread.sleep(task.difficulty * 200)
      val returnMessage = context.self.hashCode() + ": " + task.name + " Yakety Yak!"
      if (task.name.length % 2 == 0) returnMessage + " Don't talk back!!"
      else returnMessage
    }

    def receive = {
      case Assignment(task) => {
        sender ! Results(task, chore(task))
      }
    }
  }

  // construct the list of tasks of TaskMessage
  val tasks: IndexedSeq[TaskDefinition] = IndexedSeq (
    ("Take out the papers and the trash!", 23),
    ("Get no spendin' cash!", 30),
    ("Scrub that kitchen floor!", 40),
    ("Ain't gonna rock and roll no more!", 41),
    ("Finish cleanin' up your room!", 10),
    ("Let's see that dust fly with that broom!", 11),
    ("Get all that garbage out of sight!", 15),
    ("You just put on your coat and hat!", 35),
    ("Walk yourself to the laundromat!", 36),
    ("Bring in the dog!", 20),
    ("Put out the cat!", 21),
    ("Don't you give me no dirty looks!", 8),
    ("Tell your hoodlum friend outside you ain't got time to take a ride!", 49),
    ("Don't talk back!", 50)
  )  map ( (a) => TaskDefinition(name = a._1, difficulty = a._2))


  class Master(numChildren: Int, listener:ActorRef) extends Actor {
    // create the actors a la Pi.
    // assemble completion by "house" system
    val startTime = System.currentTimeMillis()
    val childRouter = context.actorOf(Props[Child].withRouter(RoundRobinRouter(numChildren)), name = "childRouter")
    val resultBuilder = new VectorBuilder[(TaskDefinition, String)]()
    var numTasks: Int = 0

    def receive = {
      case KickOff(worklist) =>
        numTasks = worklist.length
        for (i <- worklist)
          childRouter !  Assignment(i)
      case Results(task, message) =>
        println("FINISHED @ " + (System.currentTimeMillis() - startTime) / 1000 + " secs mark -  " + message )
        resultBuilder.+=((task, message))
        numTasks -= 1
        if (numTasks == 0) {
          listener ! AllDone(resultBuilder.result(), (System.currentTimeMillis() - startTime) / 1000 )
          context.stop(self)
        }
    }
    val elapsedTime = System.currentTimeMillis() - startTime / 1000
  }
  // listener - hipFatherWhoKnowsWhatCooks
  //  "Your father's hip, he knows what cooks",
  class HipFatherWhoKnowsWhatCooksListener extends Actor {
    def receive = {
      case AllDone(resultsList, elapsedTime) =>

        println("/n** ALL DONE **")
        resultsList map ((a) => println(a._2))
        println("! elapsed time = " + elapsedTime + " secs")

        context.system.shutdown()
    }
  }

  def houseWork(tasklist: IndexedSeq[TaskDefinition], numChildren: Int):Unit = {
    // Create an Akka System
    val system = ActorSystem("HouseSystem")

    println("starting houseWork...")
    // Create Listener
    val hipFatherWhoKnowsWhatCooksListener = system.actorOf(Props[HipFatherWhoKnowsWhatCooksListener], name = "hipfatherwhoknowswhatcookslistener")

    // Create Master
    val master = system.actorOf(Props(new Master(numChildren, hipFatherWhoKnowsWhatCooksListener)), name = "master")
    master ! KickOff(tasklist)
  }

  houseWork(tasklist = tasks, numChildren = 6)

}
