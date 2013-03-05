package com.cologique.scalabits.circle1.akka.sgarg

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.actorRef2Scala

sealed abstract trait Message
case class OrderPizza extends Message
case class Pizza extends Message
case class Hunger extends Message

class PizzaShop extends Actor {
  def receive = {
    case OrderPizza => {
      println("Pizza Shop: Order received.. making pizza..")
      makePizza()
      println("Pizza Shop: Pizza made.. delivering pizza..")
      sender ! Pizza
    }
    case _ => println("Pizza Shop: huh? ")
  }

  def makePizza() = Thread.sleep(5000)
}

class Customer extends Actor {
  val pizzaShop = context.actorOf(Props[PizzaShop], name = "roundTableActor")

  def receive = {
    case Pizza => {
      println("Customer:   Pizza received.. yumm!!")
      context.system.shutdown
    }
    case Hunger => {
      println("Customer:   Yikes! Hunger strikes!!")
      pizzaShop ! OrderPizza
    }
    case _ => println("Customer:   huh? ")
  }
}

object SimpleAkkaForTell extends App {
  val system = ActorSystem("PizzaSystem")
  val customer = system.actorOf(Props[Customer], name = "me")
  customer ! Hunger
}