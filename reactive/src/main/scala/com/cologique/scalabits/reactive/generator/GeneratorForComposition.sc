
object scalabits {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  class IntGenerator(id: String) extends Generator[Int] {
    val rand = new java.util.Random
    def generate = {
      println(id)
      rand.nextInt()
    }
  }

  def pairGenerator[T, U](t: Generator[T], u: Generator[U]) = for {
    x <- t
    y <- u
  } yield (x, y)                                  //> pairGenerator: [T, U](t: Generator[T], u: Generator[U])Generator[(T, U)]

  def pairGenerator2[T, U](t: Generator[T], u: Generator[U]): Generator[(T, U)] = {
    t flatMap {x =>
      u map  { y =>
        (x, y)
      }
    }
  }                                               //> pairGenerator2: [T, U](t: Generator[T], u: Generator[U])Generator[(T, U)]

  def pairGenerator3[T, U](t: Generator[T], u: Generator[U]): Generator[(T, U)] = {
    def inner: (T => Generator[(T, U)]) = { x: T =>
      def augmentToPair: (U => (T, U)) = { y: U =>
        (x, y)
      }
      u map augmentToPair
    }
    t flatMap inner
  }                                               //> pairGenerator3: [T, U](t: Generator[T], u: Generator[U])Generator[(T, U)]


  val firstGen = new IntGenerator("first")        //> firstGen  : scalabits.IntGenerator = scalabits$$anonfun$main$1$IntGenerator$
                                                  //| 1@1d1aed21
  val secondGen = new IntGenerator("second")      //> secondGen  : scalabits.IntGenerator = scalabits$$anonfun$main$1$IntGenerator
                                                  //| $1@52c62074
  val intPairGenerator = pairGenerator2(firstGen, secondGen)
                                                  //> intPairGenerator  : Generator[(Int, Int)] = Generator$$anon$2@71419cf7
  intPairGenerator.generate                       //> first
                                                  //| second
                                                  //| res0: (Int, Int) = (15728405,-1388215725)

}

trait Generator[+T] {
  self =>
  def generate: T

  def map[S](f: T => S): Generator[S] = new Generator[S] {
    def generate = f(self.generate)
  }

  def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
    def generate = f(self.generate).generate
  }
}