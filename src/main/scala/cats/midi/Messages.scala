package cats.midi

import cats.kernel.{CommutativeMonoid, Monoid}

import scala.collection.immutable.SortedSet
import simulacrum.typeclass
import simulacrum.op

object Messages {

  type Messages = SortedSet[Event]

  def apply(m: Event*): Messages = SortedSet(m: _*)

  implicit val messagesMonoid: Monoid[Messages] =
    new Monoid[Messages] {
      override val empty: Messages = SortedSet()
      override def combine(x: Messages, y: Messages): Messages = {
        val xxx = x.last.timestamp
        x ++ y.map(_ + xxx)
      }
    }

  implicit val messagesCommutativeMonoid: CommutativeMonoid[Messages] =
    new CommutativeMonoid[Messages] {
      override val empty: Messages                             = SortedSet()
      override def combine(x: Messages, y: Messages): Messages = x ++ y
    }

  implicit val shift: Shift[Messages] = new Shift[Messages] {
    def shift(a: Messages): Messages = ???
  }
}

@typeclass trait Shift[A] {
  @op("!!") def shift(a: A): A
}
