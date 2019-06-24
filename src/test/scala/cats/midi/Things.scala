package cats.midi

import java.util.concurrent.TimeUnit.MILLISECONDS

import cats.implicits._
import cats.midi.Messages._
import cats.midi.types.uByte
import javax.sound.midi.ShortMessage
import org.scalacheck.support.cats._
import org.scalacheck.{Arbitrary, Gen}

import scala.collection.immutable.SortedSet
import scala.concurrent.duration.FiniteDuration

object Things {

  implicit val arbShortMessage: Arbitrary[ShortMessage] =
    Arbitrary[ShortMessage] {
      (
        Gen.oneOf(0xF6, 0xF7, 0xF8, 0xF9, 0xFA, 0xFB, 0xFC, 0xFD, 0xFE, 0xFF, 0xF1, 0xF3, 0xF2),
        Gen.choose(0, 127),
        Gen.choose(0, 127)
      ).mapN(new ShortMessage(_, _, _))
    }

  implicit val arbEvent: Arbitrary[Event] = Arbitrary {
    (arbShortMessage.arbitrary, Arbitrary.arbitrary[Long]).mapN(Event(_, _))
  }

  implicit val arbMidiMessage: Arbitrary[Messages] = Arbitrary {
    Gen
      .listOf(arbEvent.arbitrary)
      .map(l => SortedSet(l: _*))
  }

  implicit val arbDuration: Arbitrary[FiniteDuration] =
    Arbitrary(Gen.posNum[Long].map(FiniteDuration(_, MILLISECONDS)))

  implicit val arbUByte: Arbitrary[uByte] = Arbitrary {
    Gen
      .choose(uByte.MinValue.value, uByte.MaxValue.value)
      .map(uByte.unsafeFrom)
  }

  implicit val arbRanj: Arbitrary[Ranj[uByte]] = Arbitrary {
    for {
      mid   <- Gen.choose(uByte.MinValue.value + 1, uByte.MaxValue.value - 1)
      start <- Gen.choose(uByte.MinValue.value, mid - 1)
      end   <- Gen.choose(mid + 1, uByte.MaxValue.value)
    } yield (uByte.from(start), uByte.from(end)).mapN(Ranj[uByte]).toOption.get
  }

  implicit def arbRamp[A >: Macro](
      implicit arbDuration: Arbitrary[FiniteDuration],
      arbRange: Arbitrary[Ranj[uByte]]
  ): Arbitrary[A] =
    Arbitrary((arbRange.arbitrary, arbDuration.arbitrary).mapN(Ramp(_, _)))

  implicit val arbMacro: Arbitrary[Macro] = arbRamp

}
