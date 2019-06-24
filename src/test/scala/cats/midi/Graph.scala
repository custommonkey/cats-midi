package cats.midi

import java.io.File

import com.cibo.evilplot.plot.ScatterPlot
import com.cibo.evilplot._
import com.cibo.evilplot.plot._
import com.cibo.evilplot.numeric.Point
import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
import com.cibo.evilplot.AwtDrawableOps

object Graph {

  trait Points[A] extends (A => Seq[Point])

  object Points {
    implicit val ee: Points[Seq[Event]] = events =>
      events.map(e => Point(e.timestamp.toDouble, e.msg.getData1().toDouble))
  }

  def apply[A](data: A)(implicit points: Points[A]) =
    ScatterPlot(
      points(data)
    ).xAxis()
      .yAxis()
      .frame()
      .rightLegend()
      .render()
      .write(new File("xxx.png"))
}
