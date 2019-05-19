package cats.midi

import java.util.concurrent.TimeUnit.MILLISECONDS

import cats.implicits._
import cats.midi.Messages._
import cats.midi.types.uByte
import eu.timepit.refined.scalacheck.arbitraryRefType
import javax.sound.midi.{MidiMessage, ShortMessage}
import org.scalacheck.support.cats._
import org.scalacheck.{Arbitrary, Gen}

import scala.collection.immutable.SortedSet
import scala.concurrent.duration.FiniteDuration

object Things {
  implicit val arbUByte: Arbitrary[uByte] = arbitraryRefType(Gen.choose(0, 127))

  implicit val arbShortMessage: Arbitrary[ShortMessage] =
    Arbitrary[ShortMessage] {
      (Gen.oneOf(0xF6, 0xF7, 0xF8, 0xF9, 0xFA, 0xFB, 0xFC, 0xFD, 0xFE, 0xFF, 0xF1, 0xF3, 0xF2),
       Gen.choose(0, 127),
       Gen.choose(0, 127)).mapN(new ShortMessage(_, _, _))
    }

  implicit val arbEvent: Arbitrary[Event[MidiMessage]] = Arbitrary {
    (arbShortMessage.arbitrary, Arbitrary.arbitrary[Long]).mapN(Event[MidiMessage])
  }

  implicit val arbMidiMessage: Arbitrary[Messages] = Arbitrary {
    Gen
      .listOf(arbEvent.arbitrary)
      .map(l => SortedSet(l: _*))
  }

  implicit val arbDuration: Arbitrary[FiniteDuration] =
    Arbitrary(Gen.posNum[Long].map(FiniteDuration(_, MILLISECONDS)))

  implicit def arbRamp(implicit arbitrary: Arbitrary[FiniteDuration]): Arbitrary[Ramp] = Arbitrary {
    for {
      start    <- arbUByte.arbitrary
      end      <- Gen.choose(start.value, uByte.Max.value).map(uByte.unsafeFrom)
      duration <- arbitrary.arbitrary
    } yield Ramp(start, end, duration)
  }

}
