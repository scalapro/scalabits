package com.cologique.scalabits.circle1.akka.azad

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.InvalidMessageException
import akka.actor.Props
import akka.dispatch.Future
import akka.pattern.ask

object ActorHolder extends App {

  implicit val system = ActorSystem("MySystem")
  val client = system.actorOf(Props[Client], name = "client")
  client ! RunMessage
  system.shutdown

  sealed trait MyMessage
  case object RunMessage extends MyMessage
  case class TellPrintMessage(content: String) extends MyMessage
  case class TellEchoMessage(content: String) extends MyMessage
  case class AskEchoMessage(content: String) extends MyMessage

  class Server extends Actor {
    def receive = {
      case TellPrintMessage(content) => println(content)
      case TellEchoMessage(content) => sender ! TellEchoMessage(content)
      case AskEchoMessage(content) => sender ! AskEchoMessage(content)
    }
  }

  class Client extends Actor {
    def receive = {
      case RunMessage => {
        println("running")
        fireAndForget
        tellToEcho
        askToEcho
      }
      case TellEchoMessage(echoed) => println("tell echo received: " + echoed)
    }

    def fireAndForget = {
      val worker = context.actorOf(Props[Server], name = "fireAndForgetActor")
      worker ! TellPrintMessage("fire and forget")
    }

    def tellToEcho = {
      val worker = context.actorOf(Props[Server], name = "tellToEchoActor")
      worker ! TellEchoMessage("telling to echo")
    }

    def askToEcho: Future[Any] = {
      val worker = context.actorOf(Props[Server], name = "askToEchoActor")
      val echoFuture = worker.ask(AskEchoMessage("asking to echo"))(5000)
      echoFuture.onSuccess {
        case AskEchoMessage(content) => println("ask echo received: " + content)
      }
    }
  }

}