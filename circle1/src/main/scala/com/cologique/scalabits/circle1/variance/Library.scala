package com.cologique.scalabits.circle1.variance

abstract class Publication(val title : String) {
   def getTitle() : String
   def render( _render : Publication => String) : String = {
     _render(this)
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
  def renderAsHTML(pub : Publication) = { "<h1>" + pub.getTitle() + "</h1>" }
  def renderAsXml(pub : Publication) = { "<title>" + pub.getTitle() + "</title>" }
  
  books.foreach{ e => println(e.render(renderAsHTML))  }
  val pubs : List[Publication] = books.toList ::: magazines.toList
  
  println("=== pubs in HTML ==")
  pubs.foreach{ e => println(e.render(renderAsHTML)) }
  println("=== pubs in Xml ==")
  pubs.foreach{ e => println(e.render(renderAsXml)) }
}