package com.cologique.scalabits.circle1.scalaTest

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class ScalaLifeTest extends FunSpec with ShouldMatchers {

  def fixture =
    new {
      val level =
        """-o----
          |-ooo--
          |--oo--
          |--oo--
          |oo---o""".stripMargin

      val board = new Board(Board.gridizer(level))

      val pos_2_1 = Board.Pos(2,1)
      val pos_0_1 = Board.Pos(0,1)
      val pos_neg1_neg1 = Board.Pos(-1,-1)
      val pos_neg1_1 = Board.Pos(-1,1)
      val pos_width_height = Board.Pos(board.width,board.height)

      val live_zero_neighbor = Board.Pos(5,4)
      val live_one_neighbor = Board.Pos(0,4)
      val live_two_neighbor = Board.Pos(1,4)
      val live_three_neighbor = Board.Pos(3,1)
      val live_four_neighbor = Board.Pos(2,3)

      val dead_two_neighbor = Board.Pos(0,0)
      val dead_three_neighbor = Board.Pos(4,2)
      val dead_four_neighbor = Board.Pos(2,0)

    }

  describe("A Position specifies a cell location ") {
    it("can accept positive coordinates") {
      val f = fixture
      assert(f.pos_2_1.x == 2)
      assert(f.pos_2_1.y == 1)
      assert(f.pos_0_1.x == 0)
      assert(f.pos_0_1.y == 1)
    }

    // ignored... because of (pending)
    it("can accept blah blah negative coordinates") (pending)

    it("can accept negative coordinates") {
      val f = fixture
      assert(f.pos_neg1_neg1.x == -1)
      assert(f.pos_neg1_neg1.y == -1)
    }
    it("can accept positive and negative coordinates") {
      val f = fixture
      assert(f.pos_neg1_1.x == -1)
      assert(f.pos_neg1_1.y == 1)
    }
    it("can be added to another position") {
      val f = fixture
      val add_pos_2_1_and_pos_0_1 = f.pos_2_1 + f.pos_0_1
      assert(add_pos_2_1_and_pos_0_1.x == 2)
      assert(add_pos_2_1_and_pos_0_1.y == 2)
    }
  }

  describe("A Board which contains a series of cells - indicated by Position") {
    it("has an initial width and height based on the input grid") {
      val f = fixture
      assert(f.board.width == 6)
      assert(f.board.height == 5)
    }
    it("has ability to check positions (1 for live cell / 0 for a dead cell) within its board boundaries") {
      val f = fixture
      val check_terrain_2_1 = f.board.positionCheck(f.pos_2_1)
      val check_terrain_0_1 = f.board.positionCheck(f.pos_0_1)
      assert(check_terrain_2_1 == 1)
      assert(check_terrain_0_1 == 0)
    }
    it("will consider positions outside its board boundaries as dead cells (value of 0)") {
      val f = fixture
      assert(f.board.positionCheck(f.pos_neg1_1) == 0)
      assert(f.board.positionCheck(f.pos_neg1_neg1) == 0)
      assert(f.board.positionCheck(f.pos_width_height) == 0)
    }
    it("has ability to count the number of live cells in adjacent positions - total 8 positions") {
      val f = fixture
      val test_0_0 = f.board.neighborCheck(Board.Pos(0,0))
      val test_1_1 = f.board.neighborCheck(Board.Pos(1,1))
      val test_2_2 = f.board.neighborCheck(Board.Pos(2,2))
      val test_3_3 = f.board.neighborCheck(Board.Pos(3,3))
      val test_4_4 = f.board.neighborCheck(Board.Pos(4,4))
      val test_5_4 = f.board.neighborCheck(Board.Pos(5,4))
      assert(test_0_0 == 2)
      assert(test_1_1 == 3)
      assert(test_2_2 == 6)
      assert(test_3_3 == 3)
      assert(test_4_4 == 2)
      assert(test_5_4 == 0)
    }
    it("obeys Game of Life rules - #1 Any live cell with fewer than two live neighbours dies, as if caused by underpopulation.") {
      val f = fixture
      assert(f.board.isAlive(f.live_zero_neighbor) == false)
      assert(f.board.isAlive(f.live_one_neighbor) == false)
      assert(f.board.isAlive(f.live_two_neighbor) == true)
    }
    it("obeys Game of Life rules - #2 Any live cell with more than three live neighbours dies, as if by overcrowding.") {
      val f = fixture
      assert(f.board.isAlive(f.live_three_neighbor) == true)
      assert(f.board.isAlive(f.live_four_neighbor) == false)
    }
    it("obeys Game of Life rules - #3 Any live cell with two or three live neighbours lives on to the next generation.") {
      val f = fixture
      assert(f.board.isAlive(f.live_one_neighbor) == false)
      assert(f.board.isAlive(f.live_two_neighbor) == true)
      assert(f.board.isAlive(f.live_three_neighbor) == true)
      assert(f.board.isAlive(f.live_four_neighbor) == false)

    }
    it("obeys Game of Life rules - #4 Any dead cell with exactly three live neighbours becomes a live cell.") {
      val f = fixture
      assert(f.board.isAlive(f.dead_two_neighbor) == false)
      assert(f.board.isAlive(f.dead_three_neighbor) == true)
      assert(f.board.isAlive(f.dead_four_neighbor) == false)

    }
  }

}
