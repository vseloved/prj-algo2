package task1

import java.lang.Math.{pow, sqrt}

import scala.util.Random
import scalafx.application.{JFXApp, Platform}
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.layout.Pane
import scalafx.scene.shape.{Circle, Line, Rectangle}
import scalafx.scene.text.Text
import scalafx.scene.{Node, Scene}


object TravellingSalesman {

  def find(seq: Seq[(Int, Int)], startElement: () => Int, pointsConsumer: ((Int, Int)) => Unit): (Array[(Int, Int)], Double) = {
    val xs = seq.toArray
    swap(xs, startElement(), 0)
    var totalDistance = 0d

    for (i <- 0 until (xs.length - 1)) {
      var minDistance = Double.MaxValue
      var minIndex = i + 1

      val p1 = xs(i)
      for (j <- minIndex until xs.length) {
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
    pointsConsumer((lastPoint._1, lastPoint._2))
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

import scalafx.scene.paint.Color._

object main {
  val states = Seq((3222, 8618), (3326, 1125), (3444, 9217), (3834, 12129), (3944, 10459), (4145, 7240), (3990, 7531), (3026, 8416), (3344, 8423), (4337, 11611), (3947, 8939), (3946, 8690), (4135, 9336), (3920, 9540), (3811, 8452), (3027, 9111), (4418, 6946), (3858, 7629), (4221, 7130), (4244, 8433), (4457, 9360), (3218, 9010), (3834, 9210), (4635, 1121), (4048, 9641), (3990, 11945), (4312, 7132), (4013, 7446), (3540, 10556), (4239, 7345), (3546, 7838), (4649, 10046), (3957, 8259), (3529, 9730), (4456, 1231), (4015, 7652), (4149, 7124), (3400, 811), (4422, 10020), (3609, 8647), (3016, 9744), (4046, 11153), (4415, 7234), (3732, 7726), (4702, 12254), (3820, 8136), (4304, 8923), (4108, 10449))

  def normValue(min: Double, max: Double, value: Double, scale: Double): Int = (((value - min) / (max - min)) * scale).toInt

  def main(args: Array[String]): Unit = {

    val points = states

    val minX = points.map(p => p._1).min
    val maxX = points.map(p => p._1).max
    val maxY = points.map(p => p._2).max
    val minY = points.map(p => p._2).min
    val r = new Random()
    val vis = new SalesmanVisualisation(850, 650,
      points,
      ObservableBuffer(),
      (x: (Int, Int)) => (normValue(minX, maxX, x._1, 800) + 25, normValue(minY, maxY, x._2, 600) + 25))


    val t = new Runnable {
      override def run(): Unit = {
        vis.main(args)
      }
    }

    new Thread(t).start()
    Thread.sleep(3000)
    TravellingSalesman.find(points, () => r.nextInt(points.length), p => vis.addPoint(p))

  }
}

class SalesmanVisualisation(panelW: Int, panelH: Int,
                            points: Seq[(Int, Int)],
                            steps: ObservableBuffer[(Int, Int)],
                            norm: ((Int, Int)) => ((Int, Int))) extends JFXApp {

  stage = new PrimaryStage {
    title = "Travelling salesman"
    width = panelW
    height = panelH
    scene = new Scene(panelW, panelH) {
      fill = White
      val pane = new Pane()
      root = pane
      val xs = (1 until steps.size).map(i => arrow(i - 1, steps(i - 1), steps(i)))

      val c: Seq[Node] = points
        .map(p => norm(p))
        .map(p => Circle(p._1, p._2, 5, Red)) ++ xs.flatten

      steps.onChange((x, y) => {
        Platform.runLater {
          val xs = (1 until steps.size).map(i => arrow(i - 1, steps(i - 1), steps(i)))

          val c: Seq[Node] = points
            .map(p => norm(p))
            .map(p => Circle(p._1, p._2, 5, Red)) ++ xs.flatten

          pane.children_=(c)
        }
      })

      pane.children_=(c)
    }
  }

  def arrow(name: Int, from: (Int, Int), to: (Int, Int)) = Seq(new Text(from._1 - 5, from._2, name.toString), Line(from._1, from._2, to._1, to._2))

  def addPoint(p: (Int, Int)): Unit = {
    steps.+=(norm(p))
    Thread.sleep(500)
  }

}