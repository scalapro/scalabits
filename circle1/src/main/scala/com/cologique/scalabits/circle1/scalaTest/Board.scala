package com.cologique.scalabits.circle1.scalaTest

class Board (val grid: IndexedSeq[IndexedSeq[Char]])  {
  import Board._
  val width = grid(0).size
  val height = grid.size
  val positionCheck = Board.terrainFunction(grid)
  val neighborList = Vector(Pos(-1,-1), Pos(0,-1), Pos(1,-1), Pos(-1,0), Pos(1,0), Pos(-1,1), Pos(0,1), Pos(1,1))

  def tick(): Board = {
    // create a duplicate vector
    // go through every position in vector --> Map? <-- unfortunately, it doesn't give a position... only value.
    //  A SOLUTION --> need a for-loop to generate the proper positions within the board dimensions.

    val newBoard =
      for (y <- 0 until height) yield {
        for (x <- 0 until width) yield {
          this.isAlive(Pos(x,y)) match {
            case true => 'o'
            case false => '-'
          }
        }
      }

    new Board(newBoard)
  }

  def display() = grid map{row => row map{ cell => print(cell) }; println()}

  override def toString:String = {
    var outString: StringBuffer = new StringBuffer()
    grid map{row => row map{ cell => outString.append(cell) }; outString.append("\n")}
    outString.toString
  }

  // LOOK INTO A sum() and then retest
  def neighborCheck(position: Board.Pos): Int =
    neighborList.foldLeft(0)((totalNeighbors, neighborDelta) => totalNeighbors + positionCheck(position + neighborDelta))

  def isAlive(position: Board.Pos): Boolean = neighborCheck(position) match {
    case 2 => {if (positionCheck(position) == 1) true else false }
    case 3 => true
    case _ => false
  }
}

object Board {
  case class Pos(x: Int, y: Int) {
    /** The position obtained by changing the `x` coordinate by `d` */
    def dx(d: Int) = copy(x = x + d, y)

    /** The position obtained by changing the `y` coordinate by `d` */
    def dy(d: Int) = copy(x, y = y + d)

    /** add to Pos together **/
    def +(p:Pos):Pos = copy(x = this.x + p.x, y = this.y + p.y)
  }

  //HIDE the Constructor
  //def boardFromString()= {}
  //def boardFromIndexedSeqInIndexSeq()= {}

  def gridizer(grid:String): IndexedSeq[IndexedSeq[Char]] =
    IndexedSeq(grid.split("\n").map(str => IndexedSeq(str: _*)): _*)


  def terrainFunction(levelVector: IndexedSeq[IndexedSeq[Char]]): Pos => Int =
    (p) => {
      if ((p.x >= 0) && (levelVector.isDefinedAt(p.y)) && (levelVector(p.y).isDefinedAt(p.x)))
        levelVector(p.y)(p.x) match {
          case 'o' => 1
          case _ => 0
          //case 'o' => { println("("+p.x+", "+p.y+") - "+ levelVector(p.y)(p.x)); 1 }
          //case _ => { println("("+p.x+", "+p.y+") - "+ levelVector(p.y)(p.x)); 0 }
        }
      else 0
    }
}