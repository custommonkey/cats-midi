package cats.midi

import cats.Show
import cats.kernel.Eq
import cats.implicits._

final case class Event[A: Show: Eq](msg: A, timestamp: Long) {
  override def toString: String = show"Event($msg, $timestamp)"

  override def equals(obj: Any): Boolean =
    obj match {
      case e: Event[A] => Eq[A].eqv(msg, e.msg)
      case _           => false
    }

  def +(timestamp: Long): Event[A] =
    copy[A](timestamp = this.timestamp + timestamp)
}

object Event {

  implicit def order[B]: Ordering[Event[B]] =
    (x: Event[B], y: Event[B]) => implicitly[Ordering[Long]].compare(x.timestamp, y.timestamp)

  implicit def equality[A]: Eq[Event[A]] = Eq.fromUniversalEquals

}
