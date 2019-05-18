package cats.midi

import java.util.concurrent.TimeUnit.MILLISECONDS

import cats.midi.ShortMessages._
import cats.midi.Things.arbRamp
import eu.timepit.refined.auto._
import org.scalacheck.{Arbitrary, Gen, ScalacheckShapeless}
import org.scalatest.prop.PropertyChecks
import org.scalatest.{MustMatchers, WordSpec}

import scala.concurrent.duration.{FiniteDuration, _}

class RampSpec extends WordSpec with MustMatchers with PropertyChecks with ScalacheckShapeless {

  "Ramp" should {
    "generate a ramp of events" in {

      check(Ramp(0, 1, 10.seconds))

      implicit val arbDuration: Arbitrary[FiniteDuration] =
        Arbitrary(Gen.choose[Long](100, 10000).map(FiniteDuration(_, MILLISECONDS)))

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

}
