package com.cologique.scalabits.circle1.variance

abstract class Publication(val title : String) {
   def getTitle() : String
   def render( format : String => String) : String = {
     format(getTitle())
   }
}

class Book(title : String) extends Publication(title) {
  def getTitle() = title
}
class Magazine(title : String, val issueNum : Int) extends Publication(title) {
  def getTitle() = title + ' ' + issueNum
}

object Library extends App {
  val books = Set( new Book("Scala in Action"), new Book("Heart of Darkness") )
  val magazines = Set( new Magazine("Popular Mechanics", 1) )
  def formatAsHTML(input : String) = { "<h1>" + input + "</h1>" }
  def formatAsXml(input : String) = { "<title>" + input + "</title>" }
  
  books.foreach{ e => println(e.render(formatAsHTML))  }
  val pubs : List[Publication] = books.toList ::: magazines.toList
  
  println("=== pubs ==")
  pubs.foreach{ e => println(e.render(formatAsHTML))  }
  
}