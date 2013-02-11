package com.cologique.scalabits.circle1.collections

object AwardsExample extends App {
  
  abstract class AbstractEmployee(val name : String) { 
    override def toString() : String = getClass + " " + name 
  }
  class SalariedEmployee(name : String) extends AbstractEmployee(name) {}
  class Manager(name : String) extends AbstractEmployee(name) {}
  class Award(val recipient : AbstractEmployee) {}
  
  
  val list = List( new Award(new SalariedEmployee("John"))
                          ,new Award(new Manager("Bob"))
                   		)
                   		
  def printAward( award: Award) { println( "getAward(): " + award)}
  
  val award1 = list(0)
  printAward( award1 )
  
  val emp2 = list(1).recipient
  println("emp2: " + emp2)
  
  
  

}