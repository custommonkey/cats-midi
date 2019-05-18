package cats.midi

import cats.midi.Messages._
import cats.midi.types.uByte
import eu.timepit.refined.auto._
import eu.timepit.refined.scalacheck.arbitraryRefType
import javax.sound.midi.ShortMessage.{NOTE_OFF, NOTE_ON}
import javax.sound.midi.{MidiMessage, ShortMessage}
import org.scalacheck.{Arbitrary, Gen, ScalacheckShapeless}
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, WordSpec}

class GridSpec extends WordSpec with ScalacheckShapeless with PropertyChecks with Matchers {

  implicit val arbUbyte: Arbitrary[uByte] = arbitraryRefType(Gen.chooseNum(0, 127))

  "Grid" should {
    "do something" in {
      forAll { grid: Grid =>
        val expected =
          grid.notes.flatMap { i =>
            Messages(Event[MidiMessage](new ShortMessage(NOTE_ON, i, 100), 0),
                     Event[MidiMessage](new ShortMessage(NOTE_OFF, i, 0), 1))
          }

        println(expected)

        grid.messages should have size expected.size.toLong
        grid.messages should contain theSameElementsInOrderAs Messages(expected: _*)
      }

    }
  }

}
