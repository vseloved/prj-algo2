package task1

import java.lang.Math.{pow, sqrt}

object TravellingSalesman {

  def find(seq: Seq[(Int, Int)], startElement: () => Int, pointsConsumer: (Int, Int) => Unit): (Array[(Int, Int)], Double) = {
    val xs = seq.toArray
    swap(xs, startElement(), 0)
    var totalDistance = 0d

    for (i <- Range(0, xs.length - 1)) {
      var minDistance = Double.MaxValue
      var minIndex = i + 1

      val p1 = xs(i)
      for (j <- Range(minIndex, xs.length)) {
        val d = distance(p1, xs(j))
        if (d < minDistance) {
          minDistance = d
          minIndex = j
        }
      }

      totalDistance += minDistance
      swap(xs, i + 1, minIndex)
      pointsConsumer(p1._1, p1._2)
    }

    val lastPoint = xs(xs.length - 1)
    pointsConsumer(lastPoint._1, lastPoint._2)
    (xs, totalDistance)
  }

  def distance(p1: (Int, Int), p2: (Int, Int)): Double = {
    sqrt(pow(p1._1 - p2._1, 2) + pow(p1._2 - p2._2, 2))
  }

  def swap[T](xs: Array[T], i1: Int, i2: Int) = {
    val tmp = xs(i1)
    xs(i1) = xs(i2)
    xs(i2) = tmp
  }

}
