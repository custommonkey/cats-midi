package cats.midi

import java.io.File
import java.util.concurrent.TimeUnit.MILLISECONDS

import cats.midi.Points.ops._
import cats.midi.ShortMessages._
import cats.midi.Things.arbRamp
import com.cibo.evilplot._
import com.cibo.evilplot.plot._
import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
import eu.timepit.refined.auto._
import javax.imageio.ImageIO
import org.scalacheck.{Arbitrary, Gen, ScalacheckShapeless}
import org.scalatest.prop.PropertyChecks
import org.scalatest.{MustMatchers, WordSpec}

import scala.concurrent.duration.{FiniteDuration, _}

class RampSpec extends WordSpec with MustMatchers with PropertyChecks with ScalacheckShapeless {

  implicit val arbDuration: Arbitrary[FiniteDuration] =
    Arbitrary(Gen.choose[Long](100, 10000).map(FiniteDuration(_, MILLISECONDS)))

  "Ramp" should {
    "generate a ramp of events" in {

      check(Ramp(0, 1, 10.seconds))

      forAll { ramp: Ramp =>
        check(ramp)
      }
    }

    def check(ramp: Ramp) = {
      val messages = ramp.messages
      messages.head must be(Event(cc(ramp.start), 0))
      messages must have size (ramp.end.value - ramp.start.value + 1).toLong
      messages.last must be(Event(cc(ramp.end), ramp.duration.toMillis))
    }
  }

  val xxx = Arbitrary.arbitrary[Ramp].sample.get

  val data = xxx.points

  val img = ScatterPlot(data)
    .xAxis()
    .yAxis()
    .frame()
    .xLabel("x")
    .yLabel("y")
    .render()
    .asBufferedImage

  ImageIO.write(img, "png", new File("out.png"))

}
