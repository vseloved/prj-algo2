package dynamic_programming

import dynamic_programming.MinimumEditDistance.{applyChanges, calculate}
import dynamic_programming.Operations.{Delete, Insert, NillOp, Replace}
import org.scalatest._

class MinimumEditDistance$Test extends FunSuite with Matchers {

  test("testCalculate") {
    applyChanges("mix", calculate("mix", "max")) should be("max")

    applyChanges("mind", calculate("mind", "min")) should be("min")

    applyChanges("kitten", calculate("kitten", "sitting")) should be("sitting")
  }

  test("applyChanges should properly change string given right sequence of operations") {
    applyChanges("mix", List(NillOp(0), Replace(1, 1, "i", "a"), NillOp(2))) should be("max")

    applyChanges("mind", List(NillOp(0), NillOp(1), NillOp(2), Delete(3))) should be("min")

    applyChanges("kitten", List(Replace(0, 0, "k", "s"), NillOp(1), NillOp(2), NillOp(3), Replace(4, 4, "e", "i"), NillOp(5), Insert(6, "g"))) should be("sitting")
  }

}
