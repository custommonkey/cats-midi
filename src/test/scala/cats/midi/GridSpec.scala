package cats.midi

import eu.timepit.refined.auto._
import javax.sound.midi.ShortMessage
import javax.sound.midi.ShortMessage.{NOTE_OFF, NOTE_ON}
import org.scalacheck.ScalacheckShapeless
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, WordSpec}
import cats.midi.Things.arbUByte

class GridSpec extends WordSpec with ScalacheckShapeless with PropertyChecks with Matchers {

  "Grid" should {
    "do something" in {
      forAll { grid: Grid =>
        val expected =
          grid.notes.zipWithIndex.flatMap {
            case (i, a) =>
              Messages(
                Event(new ShortMessage(NOTE_ON, i, 100), a.toLong * 2),
                Event(new ShortMessage(NOTE_OFF, i, 0), (a.toLong * 2) + 1)
              )
          }

        grid.messages should have size expected.size.toLong
        grid.messages should contain theSameElementsInOrderAs Messages(expected: _*)
      }

    }
  }

}
