package com.cologique.scalabits.circle1.collections

object Puzzler_2013_02_01 {

  class CovariantCell[+A](val content: A)

  object CovariantCell {
    def apply[A](content: A) = new CovariantCell(content)
  }

  class InvariantCell[A](val content: A)

  object InvariantCell {
    def apply[A](content: A) = new InvariantCell(content)
  }

  class CovariantClient[+A](val cell: CovariantCell[A])

  object CovariantClient {
    def apply[A](cell: CovariantCell[A]) = new CovariantClient(cell)
  }
  
  class InvariantClient[A](val cell: InvariantCell[A])

  object InvariantClient {
    def apply[A](cell: InvariantCell[A]) = new InvariantClient(cell)
  }
  
  /*
   * The following does not compile. The compiler reports:
   * 
   * covariant type A occurs in invariant position in type => 
   * com.cologique.scalabits.circle1.collections.Puzzler1.InvariantCell[A] of value cell
   * 
   * Compare this with CovariantClient above which does compile.
   * 
   * Q - What can go wrong here? Give a specific counter-example where allowing this
   * would subvert the type system.
   * 
   * Why does the problem not exist with CovaraintClient?
   */  
  // class CovariantClient1[+A](val cell: InvariantCell[A])
  
  class InvariantClient1[A](val cell: CovariantCell[A]) {
  }
  
  object InvariantClient1 {
    def apply[A](cell: CovariantCell[A]) = new InvariantClient1(cell)
  }
  
  def main(args: Array[String]) = {
    val covariantCell = CovariantCell(1)
    println(covariantCell.content)
    val invariantCell = InvariantCell(1)
    println(invariantCell.content)
    
    val covariantClient = CovariantClient(covariantCell)
    println(covariantClient.cell.content)
    val invariantClient = InvariantClient(invariantCell)
    println(invariantClient.cell.content)
    
    val invariantClient1 = InvariantClient1(covariantCell)
    println(invariantClient1.cell.content)
    
  }
  
}
