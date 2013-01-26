package com.cologique.scalabits.circle1.books.programming_in_scala_2ed.ch19

// TODO. Hide this implementation inside a companion object of a trait?

class CovariantQueue[+T] private (
  private[this] var leading: List[T],
  private[this] var trailing: List[T]) {

  private def mirror() =
    if (leading.isEmpty) {
      // TODO. Why is this more efficient than swap and reverse?
      while (!trailing.isEmpty) {
        leading = trailing.head :: leading
        trailing = trailing.tail
      }
    }

  def head: T = {
    mirror()
    leading.head
  }

  def tail: CovariantQueue[T] = {
    mirror()
    new CovariantQueue(leading.tail, trailing)
  }

  def enqueue[U >: T](x: U) =
    new CovariantQueue[U](leading, x :: trailing)
}
