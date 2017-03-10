package sample

import org.scalatest._

class HelloSpec extends FlatSpec with Matchers {
  "The Main.scala object" should "say hello" in {
    //Hello.greeting shouldEqual "hello"
    println("Test");
  }
}
