package com.cologique.scalabits.circle1.scalaTest

/**
 * This forms the immutable class which contains one generation of a Game of Life board
 *
 * This board will be able to generate the next generation based on the Game of Life rules.
 * @author Tony Hsieh
 * @param grid an IndexSeq of nested IndexSeq as rows of Char
 */

class Board (val grid: IndexedSeq[IndexedSeq[Char]])  {
  import Board._
  val width = grid(0).size
  val height = grid.size
  val positionCheck = Board.terrainFunction(grid)
  val neighborList = Vector(Pos(-1,-1), Pos(0,-1), Pos(1,-1), Pos(-1,0), Pos(1,0), Pos(-1,1), Pos(0,1), Pos(1,1))

  /**
   * This generates the next generation board based on current board.
   *
   * @return next generation Board Object
   */
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

  /* a simple side-effect printing the string presentation of the current board routine */
  def display() = grid map{row => row map{ cell => print(cell) }; println()}

  /* generates a string representation of the current board */
  override def toString:String = {
    var outString: StringBuffer = new StringBuffer()
    grid map{row => row map{ cell => outString.append(cell) }; outString.append("\n")}
    outString.toString
  }

  /** count up the neighbors (up to 8 adjacent cells) **/
  def neighborCheck(position: Board.Pos): Int =
    neighborList.foldLeft(0)((totalNeighbors, neighborDelta) => totalNeighbors + positionCheck(position + neighborDelta))


  /**
   *  follows the Game of Life Rules
   *    #1 Any live cell with fewer than two live neighbours dies, as if caused by underpopulation.
   *    #2 Any live cell with more than three live neighbours dies, as if by overcrowding.
   *    #3 Any live cell with two or three live neighbors lives on to the next generation.
   *    #4 Any dead cell with exactly three live neighbours becomes a live cell.
   *    @return true if true (now a live cell) or false if now a dead cell
   */
  def isAlive(position: Board.Pos): Boolean = neighborCheck(position) match {
    case 2 => {if (positionCheck(position) == 1) true else false }
    case 3 => true
    case _ => false
  }
}

/**
 * Companion object for Board which contains most of the static functions
 */
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

  /** take in string representation of board and turn it into an IndexSeq of nested IndexSeq as rows of Char **/
  def gridizer(grid:String): IndexedSeq[IndexedSeq[Char]] =
    IndexedSeq(grid.split("\n").map(str => IndexedSeq(str: _*)): _*)


  /**
    * This is currying function that takes an IndexSeq of nested IndexSeq as rows of Char (levelVector) and returns a
    *  convienience function
    *
    *  EXAMPLE:
    *   val inputLevel =
        """-o----
          |-ooo--
          |--oo--
          |--oo--
          |oo---o""".stripMargin
    *   val grid = gridizer(inputLevel);
    *   val positionCheck = Board.terrainFunction(grid)
    *   positionCheck(new Board.Pos(3,3)) // returns 1
    *
    * @param levelVector an IndexSeq of nested IndexSeq as rows of Char
    * @return function which takes a position and returns 1 if a cell is live or 0 if a cell is dead
   */
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
