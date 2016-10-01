package dynamic_programming

import dynamic_programming.Operations._

object MinimumEditDistance {

  def calculate(w1: String, w2: String): List[Operation] = {
    val a = w1.split("")
    val b = w2.split("")

    val cost = Array.fill(a.length + 1, b.length + 1)(0)

    for (i <- 0 until a.length + 1) cost(i)(0) = i
    for (j <- 0 until b.length + 1) cost(0)(j) = j


    cost(1)(1) = 1

    for (i <- 1 until a.length + 1) {
      for (j <- 1 until b.length + 1) {
        if (a(i - 1) == b(j - 1)) {
          cost(i)(j) = cost(i - 1)(j - 1)
        } else {
          cost(i)(j) = 1 + minCostOp(cost, i, j)
        }
      }
    }

    var result = List[Operation]()
    var i = a.length
    var j = b.length

    while (i > 0 && j > 0) {
      var op: Operation = NillOp(i - 1)
      val min = minCostOp(cost, i, j)

      if (min == cost(i - 1)(j - 1)) {
        if (cost(i)(j) != cost(i - 1)(j - 1)) {
          op = Replace(i, j, a(i - 1), b(j - 1))
        }
        i -= 1
        j -= 1
      } else if (min == cost(i - 1)(j)) {
        op = Delete(i - 1)
        i -= 1
      } else if (min == cost(i)(j - 1)) {
        op = Insert(i, b(j - 1))
        j -= 1
      }

      result = List(op) ++ result
    }
    result
  }

  def min(a: Int, b: Int, c: Int) = Math.min(a, Math.min(b, c))

  def minCostOp(cost: Array[Array[Int]], i: Int, j: Int) = min(cost(i - 1)(j), cost(i)(j - 1), cost(i - 1)(j - 1))

  def applyChanges(s: String, transformations: List[Operation]): String =
    transformations
      .foldLeft("")((acc: String, op: Operation) =>
        op match {
          case NillOp(x) => acc + s(x)
          case Replace(_, _, _, to) => acc + to
          case Insert(_, x) => acc + x
          case Delete(_) => acc
        })
}

object Operations {

  trait Operation

  case class NillOp(index: Int) extends Operation

  case class Insert(index: Int, letter: String) extends Operation

  case class Replace(i: Int, j: Int, from: String, to: String) extends Operation

  case class Delete(index: Int) extends Operation

}

