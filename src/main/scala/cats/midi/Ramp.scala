package cats.midi

import cats.midi.Messages._
import cats.midi.ShortMessages._
import cats.midi.types.uByte

import scala.concurrent.duration.FiniteDuration

case class Ranj[A](start: A, end: A)

final case class Ramp(range: Ranj[uByte], duration: FiniteDuration) extends Macro {

  val messages: Messages = {
    val ccc = duration.toMillis / (range.end.value - range.start.value)

    Messages((range.start.value to range.end.value).map { i =>
      Event(cc(uByte.unsafeFrom(i)), ccc * (i - range.start.value))
    }: _*)
  }

}

object Ramp {
  implicit val rampMacro: ToMessages[Ramp] = _.messages
}
