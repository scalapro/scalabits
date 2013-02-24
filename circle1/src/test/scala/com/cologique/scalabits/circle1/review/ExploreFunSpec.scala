package com.cologique.scalabits.circle1.review

import scala.collection.mutable.ListBuffer

import org.scalatest._

class ExploreFunSpec extends FunSpec {
  
  def fixture =
    new {
      val builder = new StringBuilder("ScalaTest is ")
      val buffer = new ListBuffer[String]
    }
  
  describe("A Set") {
    describe("when empty") {
      it("should have size 0") (pending)
 //     {
//        assert(Set.empty.size === 0)
//      }
      it("should produce NoSuchElementException when head is invoked") {
        intercept[NoSuchElementException] {
          Set.empty.head
        }
      }
    }
    describe("when has one element") {
      it("should have size 1") {
        assert(Set('a').size === 1)
      }
      it("should have a head element should be 'a'") {
        assert(Set('a').head === 'a')
      }
    }
  }

}