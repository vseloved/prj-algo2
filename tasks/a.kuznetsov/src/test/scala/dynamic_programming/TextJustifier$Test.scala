package dynamic_programming

import dynamic_programming.TextJustifier.justify
import org.scalatest.{FunSuite, Matchers}

class TextJustifier$Test extends FunSuite with Matchers {

  test("testJustify base test") {
    val text = "aaa bb c ddd ee fff"
    val width = 6

    val result = Seq(
      Seq("aaa", "bb"),
      Seq("c", "ddd"),
      Seq("ee", "fff"))

    justify(text, width) should be(result)

  }

  test("testJustify should spread words in text by lines, given width") {
    val text = "If you are reading this Quick-start Guide you are probably already know how to use Scala in your project and probably have your favorite IDE or build setup to program in Scala."
    val width = 15

    val justified = justify(text, width)
    print(TextJustifier.toString(justified))
  }

}
