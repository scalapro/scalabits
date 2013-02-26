package com.cologique.scalabits.circle1.scalaTest

import org.scalatest.{BeforeAndAfter, FunSpec, GivenWhenThen}
import org.scalatest.matchers.ShouldMatchers
import com.cologique.scalabits.circle1.scalaTest.tags.LifeTest
import com.cologique.scalabits.circle1.scalaTest.tagobjects.{PositionTest, LifeTest}

/*
 This utilizes the FunSpec style from ScalaTest

 To run in SBT console:
  0) start sbt in the root of the project
  1) test: console
  2) import org.scalatest._
  3) import  com.cologique.scalabits.circle1.scalaTest._
  4) run (new ScalaLifeTest)


 Link for other Should/Must matchers - http://www.scalatest.org/user_guide/matchers_quick_reference


 To exclude tests using SBT - stops PositionTest tagged tests
  -- add to build.sbt
 testOptions in Test += Tests.Argument("-l", "com.cologique.scalabits.circle1.tags.PositionTest")

 To include tests using SBT - stops all LifeTest tagged tests and allows PositionTest tagged tests
  -- add to build.sbt
 testOptions in Test += Tests.Argument("-l", "com.cologique.scalabits.circle1.tags.LifeTest", "-n", "com.cologique.scalabits.circle1.tags.PositionTest")

 */


@LifeTest
class ScalaLifeTest extends FunSpec with ShouldMatchers with GivenWhenThen with BeforeAndAfter {

  before {
    // set up for each it() test
  }

  after {
    // clean up after each it() test
  }

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
    it("can accept positive coordinates", PositionTest) {
      val f = fixture
      f.pos_2_1.x should be(2)
      f.pos_2_1.y should be(1)
      f.pos_0_1.x should be(0)
      f.pos_0_1.y should be(1)
    }

    // use pending if there is no code created yet
    it("can accept imaginary coordinates", PositionTest) (pending)
    it("can accept negative imaginary coordinates", PositionTest) (pending)

    // use ignore if there is created test code
    ignore("can accept very tall boards ", PositionTest) {
      val f = fixture
      f.board.height should be (1000)
    }


    it("can accept negative coordinates", PositionTest) {
      val f = fixture
      f.pos_neg1_neg1.x should be (-1)
      f.pos_neg1_neg1.y should be(-1)
    }
    it("can accept positive and negative coordinates", PositionTest) {
      val f = fixture
      f.pos_neg1_1.x should be(-1)
      f.pos_neg1_1.y should be(1)
    }
    it("can be added to another position", PositionTest) {
      val f = fixture
      val add_pos_2_1_and_pos_0_1 = f.pos_2_1 + f.pos_0_1
      add_pos_2_1_and_pos_0_1.x should be(2)
      add_pos_2_1_and_pos_0_1.y should be(2)
    }
  }

  describe("A Board which contains a series of cells - indicated by Position") {
    it("has an initial width and height based on the input grid") {
      val f = fixture
      f.board.width should be(6)
      f.board.height should be(5)
    }
    it("has ability to check positions (1 for live cell / 0 for a dead cell) within its board boundaries") {
      val f = fixture
      val check_terrain_2_1 = f.board.positionCheck(f.pos_2_1)
      val check_terrain_0_1 = f.board.positionCheck(f.pos_0_1)
      check_terrain_2_1 should be(1)
      check_terrain_0_1 should be(0)
    }
    it("will consider positions outside its board boundaries as dead cells (value of 0)") {
      val f = fixture
      f.board.positionCheck(f.pos_neg1_1) should be(0)
      f.board.positionCheck(f.pos_neg1_neg1) should be(0)
      f.board.positionCheck(f.pos_width_height) should be(0)
    }
    it("has ability to count the number of live cells in adjacent positions - total 8 positions") {
      val f = fixture
      val test_0_0 = f.board.neighborCheck(Board.Pos(0,0))
      val test_1_1 = f.board.neighborCheck(Board.Pos(1,1))
      val test_2_2 = f.board.neighborCheck(Board.Pos(2,2))
      val test_3_3 = f.board.neighborCheck(Board.Pos(3,3))
      val test_4_4 = f.board.neighborCheck(Board.Pos(4,4))
      val test_5_4 = f.board.neighborCheck(Board.Pos(5,4))
      test_0_0 should be(2)
      test_1_1 should be(3)
      test_2_2 should be(6)
      test_3_3 should be(3)
      test_4_4 should be(2)
      test_5_4 should be(0)
    }
    it("obeys Game of Life rules - #1 Any live cell with fewer than two live neighbours dies, as if caused by underpopulation.") {
      Given("a board with live and dead cells")
      val f = fixture

      When("a cell is alive")
      f.board.positionCheck(f.live_zero_neighbor) should be (1)
      f.board.positionCheck(f.live_one_neighbor) should be (1)
      f.board.positionCheck(f.live_two_neighbor) should be (1)

      Then("if they have zero neighbors, they will die")
      f.board.isAlive(f.live_zero_neighbor) should be(false)

      And("if they have one neighbor, they will also die")
      f.board.isAlive(f.live_one_neighbor) should be(false)

      And("if they have two neighbors, they will live to the next generation")
      f.board.isAlive(f.live_two_neighbor) should be(true)

      info("end of Rule #1")
    }
    it("obeys Game of Life rules - #2 Any live cell with more than three live neighbours dies, as if by overcrowding.") {
      val f = fixture
      f.board.isAlive(f.live_three_neighbor) should be(true)
      f.board.isAlive(f.live_four_neighbor) should be(false)
    }
    it("obeys Game of Life rules - #3 Any live cell with two or three live neighbors lives on to the next generation.") {
      val f = fixture
      f.board.isAlive(f.live_one_neighbor) should be(false)
      f.board.isAlive(f.live_two_neighbor) should be(true)
      f.board.isAlive(f.live_three_neighbor) should be(true)
      f.board.isAlive(f.live_four_neighbor) should be(false)

    }
    it("obeys Game of Life rules - #4 Any dead cell with exactly three live neighbours becomes a live cell.") {
      val f = fixture
      f.board.isAlive(f.dead_two_neighbor) should be(false)
      f.board.isAlive(f.dead_three_neighbor) should be(true)
      f.board.isAlive(f.dead_four_neighbor) should be(false)

    }
  }

}
