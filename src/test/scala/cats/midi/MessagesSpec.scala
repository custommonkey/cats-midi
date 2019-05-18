package cats.midi

import cats.kernel.Eq
import cats.kernel.laws.discipline.CommutativeMonoidTests
import cats.midi.Messages._
import cats.midi.ShortMessages._
import cats.midi.Things._
import cats.tests.CatsSuite
import eu.timepit.refined.auto._

import scala.collection.immutable.SortedSet

final class MessagesSpec extends CatsSuite {

  implicit def equality[A]: Eq[SortedSet[Event[A]]] = Eq.fromUniversalEquals

  checkAll("Messages.CommutativeMonoidLaws", CommutativeMonoidTests[Messages].semigroup)
//  checkAll("Messages.Eq", EqTests[Messages].eqv)

  test("Grid to messages") {

    Grid(0, 1, 3, 4).messages should be {
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
