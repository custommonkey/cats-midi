package cats.midi

import java.io.File
import java.util.concurrent.TimeUnit.MILLISECONDS

import cats.midi.Points.ops._
import cats.midi.ShortMessages._
import cats.midi.Things._
import com.cibo.evilplot._
import com.cibo.evilplot.numeric.Point
import com.cibo.evilplot.plot._
import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
import eu.timepit.refined.auto._
import org.scalacheck.{Arbitrary, Gen, ScalacheckShapeless}
import org.scalatest.prop.PropertyChecks
import org.scalatest.{MustMatchers, PropSpec}
import cats.implicits._
import cats.midi.Messages._

import scala.concurrent.duration.FiniteDuration

class RampSpec extends PropSpec with MustMatchers with PropertyChecks with ScalacheckShapeless {

  implicit val arbDuration: Arbitrary[FiniteDuration] =
    Arbitrary(Gen.choose[Long](100, 10000).map(FiniteDuration(_, MILLISECONDS)))

  property("start with first event") {
    forAll { ramp: Ramp =>
      ramp.messages.head must be(Event(cc(ramp.range.start), 0))
    }
  }

  property("end with last event") {
    forAll { ramp: Ramp =>
      ramp.messages.last must be(Event(cc(ramp.range.end), ramp.duration.toMillis))
    }
  }

  property("have correct size") {
    forAll { ramp: Ramp =>
      ramp.messages must have size (ramp.range.end.value - ramp.range.start.value + 1).toLong
    }
  }

  plot({
    for {
      a <- Arbitrary.arbitrary[Ramp]
      b <- Arbitrary.arbitrary[Ramp]
    } yield a.messages |+| b.messages
  }.sample.get.points)

  def plot(points: Seq[Point]) =
    ScatterPlot(points)
      .xAxis()
      .yAxis()
      .frame()
      .xLabel("x")
      .yLabel("y")
      .render()
      .write(new File("out.png"))

}
