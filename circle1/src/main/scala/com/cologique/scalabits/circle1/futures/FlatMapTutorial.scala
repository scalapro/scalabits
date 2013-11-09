package com.cologique.scalabits.circle1.futures

import akka.dispatch.Future
import akka.actor.ActorSystem

object FlatMapTutorial extends App {

  def show(value: Any, description: String) = println(description + " = " + value)

  /*
   * Many monads may be considered as a data structure on a "substrate" type
   * with slots of that type, basically, generalized collections. 
   * We'll call these "slotted monads". This is my (Azad Bolour) terminology for 
   * pedagogical purposes only.
   *
   * For example, List[String] is a slotted monad having an arbitrary number of 
   * substrate slots of type String. And Option[Int] is a slotted monad having at 
   * most one slot of type Int.
   */

  /* 
   * In the context of a slotted monad, a mapping function is a function from 
   * one substrate to another:
   * 
   *     mapper: Substrate1 => Substrate2. 
   * 
   * The two substrates may be the same or different types. The mapping function is 
   * applied to each slot of a slotted monad to produce another slotted monad
   * of the same structure as the original monad, where each slot value is replaced
   * by its mapper-mapped value.
   */

  /**
   * A mapper: Int => Int
   */
  def triple(i: Int) = 3 * i

  /**
   * A monad of substrate type Int.
   */
  val option1 = Option(1)

  println("Simple Maps\n")
  
  /**
   * Application of the mapper.
   */
  val tripleOption1 = option1 map triple
  show(tripleOption1, "Option(1) map triple")

  /*
   * A special case of a slotted monad is one that has no slots.
   * In this case, there are no values to apply the function to, but 
   * the structure of the monad, that is, emptiness, is preserved.
   */
  val none1: Option[Int] = None
  val tripleNone1 = none1 map triple
  show(tripleNone1, "None map triple")
  show(tripleNone1.isEmpty, "None map triple is empty")

  /**
   * Another mapper: length: String => Int
   */
  val option3 = Option("xyz")
  val length3 = option3 map { _.length }
  show(length3, "Option(\"xyz\") map length")

  val none2: Option[String] = None
  val lengthNone2 = none2 map { _.length }
  show(lengthNone2, "None map length")

  /**
   * A mapper applied to a list monad.
   */
  val list1 = List(1, 2, 3, 4)
  val tripleList1 = list1 map triple
  show(tripleList1, "List(1, 2, 3, 4) map triple")

  /*
   * Suppose now that for a mapper: Substrate1 => Substrate2, the range, Substrate2, 
   * is itself a slotted monad. The result of applying the map to a slotted monad
   * is then a nested monad.
   */

  println("\nMonad-Producing Mappers\n")
  
  /**
   * A monad-producing mapper: sqrt: Double => Option(Double)
   */
  def sqrt(n: Int): Option[Double] = {
    val d: Double = n
    val root = math.sqrt(d)
    if (root.isNaN()) None else Some(root)
  }

  /**
   * A monad-producing mapper, mapped on a monad, produces a nested monad.
   */
  val sq1 = option1 map sqrt
  show(sq1, "Option(1) map sqrt")
  val sqNone = none1 map sqrt
  show(sqNone, "None map sqrt")

  /**
   * Another monad-producing mapper: Int => List[Double] -
   * compute all square roots of an integer and return them in a list.
   */
  def allSqrts(n: Int): List[Double] = {
    val d: Double = n
    val root = math.sqrt(d)
    if (root.isNaN) List.empty else List(root, -root)
  }

  /**
   * Nested list for square roots of a set of integers.
   */
  val sqList1 = List(1, -1, 4, -4, 100, -100) map allSqrts
  show(sqList1, "List(1, -1, 4, -4, 100, -100) map allSqrts")

  /**
   * Another list-monad-producing mapper: String => List[String]
   */
  def prefixes(s: String): List[String] = (1 to s.length) map { s.substring(0, _) } toList

  val words = List("the", "quick", "brown", "fox")
  val wordPrefixes = words map prefixes
  show(wordPrefixes, "List(\"the\", \"quick\", \"brown\", \"fox\") map prefixes")

  /*
   * So far so good.
   * 
   * Now there are a variety of applications where we need to be able to compose
   * monad-producing mappers. 
   * 
   * Consider the functions:
   * 
   * 	find: Criteria => List[Person]
   * 	children: Person => List[Person]
   * 
   * These are list monad producers and the output of the first is compatible with 
   * the input of the second. The natural composition of these monod producers is 
   * a function that takes a criteria and returns the children of all persons
   * satisfying that criteria. The composition is similar to a relational join.
   * 
   * This is where Scala's flatMap comes in (in monad terminology, this is a "bind"
   * operation).
   * 
   * flatMap flattens the result of applying a monad-producing mapper to a monad.
   * So the result is no longer a nested monad but an ordinary monad. And this makes
   * it possible to pipe the result into another monad-producing mapper in a 
   * composition pipeline.
   */

  // To keep things simple, let's continue with our simple integer and string examples above.

  println("\nFlattening of Nested Monads\n")
  
  /**
   * flatMap produces a normal monad - not a nested one.
   */
  val sqrt1 = option1 flatMap sqrt
  show(sqrt1, "Option(1) flatMap sqrt)")

  /**
   * flatMap correctly propagates the None option.
   */
  val sqrtNone = none1 flatMap sqrt
  show(sqrtNone, "None flatMap sqrt")

  /**
   * flatMap produces the None option when the monad producer returns None.
   */
  val sqrtMinus1 = Option(-1) flatMap sqrt
  show(sqrtMinus1, "Option(-1) flatMap sqrt")

  /*
   * Another monad-producing mapper: String => Option[String]
   */
  def lookup(word: String): Option[String] = {
    words.find(_ == word)
  }

  println("lookup(word: String) = List(\"the\", \"quick\", \"brown\", \"fox\").find(_ == word)")

  val foxOption = Option("fox")
  val nestedFoxOption = foxOption map lookup
  show(nestedFoxOption, "Option(\"fox\") map lookup")

  val flatFoxOption = foxOption flatMap lookup
  show(flatFoxOption, "Option(\"fox\") flatMap lookup")

  /*
   * With this machinery, we can now set up a compositional pipeline of monad producers.
   */

  println("\nMonadic Pipelines\n")
  /*
   * Getting back to the prefixes list-monad-producing function.
   */

  show(words, "words")
  
  // Tiny pipeline.

  val flatWordPrefixes = words flatMap prefixes
  show(flatWordPrefixes, "1-step pipeline: \n  words flatMap prefixes")

  // Two-element pipeline.

  val pipeResult = words flatMap prefixes flatMap { (s: String) => List(s, s + s) }
  show(pipeResult, "2-step pipeline: \n  words flatMap prefixes flatMap { (s: String) => List(s, s + s) }")

  /*
   * We could also mix in maps in such a pipeline. A map takes a monad and produces
   * another, so it can be mixed in such a pipeline as long as its substrate 
   * types match what is input into it and what it outputs into.
   */

  val pipeResult2 = words flatMap prefixes map { _.length }
  show(pipeResult2, "pipeline of flatMap and map: \n  words flatMap prefixes map { _.length }")

  /**
   * An integer monad producer.
   */
  def plusOrMinus(i: Int) = List(i, -i)

  val pipeResult3 = words flatMap prefixes map { _.length } flatMap plusOrMinus
  show(pipeResult3, "flatMaps and maps mixed in a pipeline: \n  words flatMap prefixes map { _.length } flatMap plusOrMinus")

  /*
  * The Scala for comprehension is syntactic sugar for a combination of 
  * flatMap and map.
  * 
  * We'll first incrementally refactor our tiny prefixes pipeline to 
  * look more like a for comprehension.
  */

  println("Towards For Comprehensions")

  // First, the tiny pipeline.

  val flatPrefixes = words flatMap prefixes
  show(flatPrefixes, "- 1-step pipeline: \n  words flatMap prefixes")

  // Next nest flatMap's monad-producing function.

  val flatPrefixes1 = words flatMap { word => prefixes(word) }
  show(flatPrefixes1, "- nest the monad-producer in pipeline: \n  words flatMap { word => prefixes(word) }")
  assert(flatPrefixes1 == flatPrefixes)

  // Next add a final nested mapper.
  // In this case the final mapper is the identity function, and, of course, superfluous.

  val flatPrefixes2 = words flatMap { word => prefixes(word) map { prefix => prefix } }
  show(flatPrefixes2, "- add a final mapper: \n  words flatMap { word => prefixes(word) map { prefix => prefix } }")
  assert(flatPrefixes2 == flatPrefixes)

  /*
   * This final structure is isomorphic to a for comprehension. In fact, the meaning
   * of a for comprehension is defined as such a nested pipeline.
   */
  val forComprehensionPrefixes =
    for (
      word <- words;
      prefix <- prefixes(word)
    ) yield (prefix)

  show(forComprehensionPrefixes, "- transform to for comprehension: \n  for (word <- words; prefix <- prefixes(word)) yield (prefix)")

  assert(forComprehensionPrefixes == flatPrefixes)

  /*
   * Our original pipelines were not nested. They looked similar to unix pipes.
   * The for comprehension pipeline, on the other hand, is nested. This allows the 
   * values traversed by earlier flatMaps to be available to the later flatMaps,
   * and to the final mapper (in closures). So thanks to closure, a nested pipeline
   * affords us significantly more expressive power than a "flat" pipeline.
   * 
   * Note that the final mapper is the argument to yield.
   */

  println("\nNested Pipelines\n")
  
  // Nested pipeline using closed variables from outer scopes.

  val wordPrefixPairs = words flatMap { word => prefixes(word) map { prefix => (word, prefix) } }
  show(wordPrefixPairs, "nested pipeline with closure in final mapper: \n  words flatMap { word => prefixes(word) map { prefix => (word, prefix) } }")

  // Here is another example where nesting comes in handy.

  val opt16 = Option(16)

  // This does not have to be nested.
  show(opt16 flatMap { (number: Int) => sqrt(number) }, "Option(16) flatMap { (number: Int) => sqrt(number) }")

  // But this does have to be nested.
  val fourthRt = opt16 flatMap {
    (number: Int) =>
      sqrt(number) flatMap
        { (root: Double) =>
          sqrt(root.toInt) map
            { (fourthRoot: Double) => ("4'th root of " + number + " = " + fourthRoot) }
        }
  }
  println(fourthRt)

  /*
   * Futures are another example of a slotted monad. 
   * 
   * Just as the flatMap function for Option propagates the notion of emptiness
   * in a pipeline of option-producing function composition, the flatMap function of 
   * Future propagates the notion of incompleteness in a pipeline of future-producing 
   * function composition.
   */

  // Let's start with independent options first.

  val optionsTupleByFlatMap =
    Option(1) flatMap { (x1: Int) =>
      Option(2) flatMap { (x2: Int) =>
        Option(3) map { (x3: Int) =>
          (x1, x2, x3)
        }
      }
    }

  val optionsTupleByForComprehension = for (
    x1 <- Option(1);
    x2 <- Option(2);
    x3 <- Option(3)
  ) yield (x1, x2, x3)

  assert(optionsTupleByFlatMap == optionsTupleByForComprehension)

  // Here are the isomorphic constructions for independent futures.
  
  println("\nNested Pipelines of Futures\n")

  implicit val system = ActorSystem("future")
  def shutdownActorSystem = system.shutdown

  val futuresTupleByFlatMap =
    Future(1) flatMap { (x1: Int) =>
      Future(2) flatMap { (x2: Int) =>
        Future(3) map { (x3: Int) =>
          (x1, x2, x3)
        }
      }
    }

  futuresTupleByFlatMap onSuccess {
    case (x1, x2, x3) => println("sum via flatMap futures: " + (x1 + x2 + x3))
  }

  val futuresTupleByForComprehension = for (
    x1 <- Future(1);
    x2 <- Future(2);
    x3 <- Future(3)
  ) yield (x1, x2, x3)

  futuresTupleByForComprehension onSuccess {
    case (x1, x2, x3) => println("sum via for comprehension futures: " + (x1 + x2 + x3))
  }

  /*
   * Now let's actually compose futures by using future-producing functions.
   */

  /**
   * A future-producing function.
   */
  def slowDouble(num: Int): Future[Int] = {
    def double: Int = {
      Thread.sleep(200)
      return 2 * num
    }
    return Future(double)
  }

  val startTime1 = System.currentTimeMillis
  val eightTimesFutureByFlatMap = Future(5) flatMap slowDouble flatMap slowDouble flatMap slowDouble

  eightTimesFutureByFlatMap onSuccess {
    case q: Int => println("8 * 5 (by flatMap futures = " + q + " - computed in: " + (System.currentTimeMillis - startTime1) + " millis")
  }

  val startTime2 = System.currentTimeMillis

  val eightTimesFutureByForComprehension = for (
    x1 <- Future(5);
    x2 <- slowDouble(x1);
    x3 <- slowDouble(x2);
    x4 <- slowDouble(x3)
  ) yield (x4)

  eightTimesFutureByForComprehension onSuccess {
    case q: Int => println("8 * 5 (by for comprehension futures) = " + q + " - computed in: " + (System.currentTimeMillis - startTime2) + " millis")
  }

  shutdownActorSystem

}
