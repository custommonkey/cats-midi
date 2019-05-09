package cats.midi

import cats.kernel.Eq
import cats.kernel.laws.discipline.CommutativeMonoidTests
import cats.midi.Brap._
import cats.midi.Messages._
import cats.midi.ShortMessages._
import cats.tests.CatsSuite
import eu.timepit.refined.auto._
import javax.sound.midi.{MidiMessage, ShortMessage}
import org.scalacheck.{Arbitrary, Gen}

import scala.collection.immutable.SortedSet

class MessagesSpec extends CatsSuite {

  implicit val arbShortMessage: Arbitrary[ShortMessage] =
    Arbitrary[ShortMessage] {
      for {
        status <- Gen.oneOf(0xF6, 0xF7, 0xF8, 0xF9, 0xFA, 0xFB, 0xFC, 0xFD,
          0xFE, 0xFF, 0xF1, 0xF3, 0xF2)
        data1 <- Gen.choose(0, 127)
        data2 <- Gen.choose(0, 127)
      } yield new ShortMessage(status, data1, data2)
    }

  implicit val arbEvent: Arbitrary[Event[MidiMessage]] = Arbitrary {
    for {
      msg <- arbShortMessage.arbitrary
      timestamp <- Arbitrary.arbitrary[Long]
    } yield Event(msg, timestamp)
  }

  implicit val arbMidiMessage: Arbitrary[Messages] = Arbitrary {
    Gen
      .listOf(arbEvent.arbitrary)
      .map(l => SortedSet(l: _*))
  }

  implicit def eq[A]: Eq[SortedSet[Event[A]]] = Eq.fromUniversalEquals

  checkAll("Messages.CommutativeMonoidLaws",
           CommutativeMonoidTests[Messages].semigroup)
//  checkAll("Messages.Eq", EqTests[Messages].eqv)

  test("Grid to messages") {

    Grid(0, 1, 3, 4).messages should === {
      Messages(
        Event(noteOn(0), 0),
        Event(noteOff(0), 1),
        Event(noteOn(1), 2),
        Event(noteOff(1), 3),
        Event(noteOn(3), 4),
        Event(noteOff(3), 5),
        Event(noteOn(4), 6),
        Event(noteOff(4), 7)
      )
    }

  }

}
