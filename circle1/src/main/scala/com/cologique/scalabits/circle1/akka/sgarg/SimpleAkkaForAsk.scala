package com.cologique.scalabits.circle1.akka.sgarg

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.util.Timeout
import akka.pattern.ask

class ImpatientCustomer extends Actor {
  val pizzaShop = context.actorOf(Props[PizzaShop], name = "roundTableActor")

  def receive = {
    case Hunger => {
      println("Customer:   Yikes! Hunger strikes!!")
      implicit val timeout = Timeout(10000)
      val pizza = pizzaShop ? OrderPizza
      pizza.onSuccess {
        case Pizza => println("Customer:   Pizza received.. yumm!!")
      }
      while (!pizza.isCompleted) {
        Thread.sleep(1000)
        println("Customer:  Is it done yet?")
      }
      context.system.shutdown
    }
    case _ => println("Customer:   huh?")
  }
}

object SimpleAkkaForAsk extends App {
  val system = ActorSystem("PizzaSystem")
  val customer = system.actorOf(Props[ImpatientCustomer], name = "me")
  customer ! Hunger
}