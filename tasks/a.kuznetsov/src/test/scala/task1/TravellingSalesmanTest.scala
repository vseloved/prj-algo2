package task1

import org.scalatest._
import util.Benchmark._

import scala.util.Random

class TravellingSalesmanTest extends FunSuite with Matchers {

  val printPoints = (x: Any, y: Any) => println(s"$x:$y")

  val random = new Random()

  test("Path finder should sort points from the first given") {
    val asserted = List((1, 1), (2, 2), (2, 5), (6, 2), (8, 3), (8, 5), (7, 6), (11, 5))
    val input = List((1, 1), (7, 6), (11, 5), (8, 3), (2, 5), (6, 2), (8, 5), (2, 2))

    val result = TravellingSalesman.find(input, () => 0, printPoints)._1
    result should be(asserted.toArray)
  }

  test(" it should behave properly with overlapping points in") {
    val result = TravellingSalesman.find(Seq((1, 0), (3, 1), (1, 0)), () => 0, printPoints)._1

    result should be(Array((1, 0), (1, 0), (3, 1)))
  }

  test(" it should complete in n log n time in") {

    val testData = Range(1, 500).map((x: Int) => randomPoints(x * 20))

    loadTest(
      testData.map(x => x.size.toString),
      testData,
      (x: Seq[(Int, Int)]) => TravellingSalesman.find(x, () => 0, (x, y) => ()))
  }

  private def randomPoints(n: Int) = Range(0, n).map(_ => (random.nextInt(1000), random.nextInt(1000)))


}