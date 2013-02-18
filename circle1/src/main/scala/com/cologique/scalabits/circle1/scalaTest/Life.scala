package com.cologique.scalabits.circle1.scalaTest

object Life extends App {
  val level =
    """-o----
      |-ooo--
      |--oo--
      |--oo--
      |oo---o""".stripMargin

  val iterations = 3

  def lifeTicker(generations:IndexedSeq[Board], iterationsLeft:Int):IndexedSeq[Board] =  {
    if (iterationsLeft == 0) generations
    else {
      val nextBoard: Board = generations.last.tick()
      lifeTicker(generations :+ nextBoard, iterationsLeft - 1)
    }
  }

  val lifeGenerations = lifeTicker(IndexedSeq(new Board(Board.gridizer(level))), iterations)

  lifeGenerations map {x => println(x.toString()) }
}
