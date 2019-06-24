package cats.midi

import cats.Monoid
import cats.kernel.Eq
import cats.kernel.laws.discipline.{CommutativeMonoidTests, MonoidTests}
import cats.midi.Messages._
import cats.midi.ShortMessages._
import cats.midi.Things._
import cats.tests.CatsSuite
import eu.timepit.refined.auto._

import scala.collection.immutable.SortedSet

final class MessagesSpec extends CatsSuite {

  implicit def equality[A]: Eq[SortedSet[Event]] = Eq.fromUniversalEquals

  checkAll("Messages.MonoidLaws", MonoidTests[Messages].monoid)
  checkAll("Messages.CommunativeMonoidLats", CommutativeMonoidTests[Messages].commutativeMonoid)

  test("...") {

    implicit class Stuff[A](a: A) {
      def ~(b: A)(implicit m: Monoid[A]): A = m.combine(a, b) // Think this should shift
    }

    ((Event(noteOn(0), 1) ++ Event(noteOff(0), 2)) ~ (Event(noteOn(0), 1) ++ Event(noteOff(0), 2))) should be {
      Messages(
        Event(noteOn(0), 1),
        Event(noteOff(0), 2),
        Event(noteOn(1), 3),
        Event(noteOff(1), 4)
      )
    }
  }
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
