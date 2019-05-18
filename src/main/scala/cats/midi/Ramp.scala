/**
  * Copyright Homeaway, Inc 2016-Present. All Rights Reserved.
  * No unauthorized use of this software.
  */
package cats.midi

import cats.midi.Macro.Macro
import cats.midi.Messages._
import cats.midi.ShortMessages._
import cats.midi.types.uByte
import javax.sound.midi.MidiMessage

import scala.concurrent.duration.FiniteDuration

final case class Ramp(start: uByte, end: uByte, duration: FiniteDuration) {

  val messages: Messages = {

    val ccc = duration.toMillis / (end.value - start.value)

    Messages((start.value to end.value).map { i =>
      Event[MidiMessage](cc(uByte.unsafeFrom(i)), ccc * (i - start.value))
    }: _*)
  }

}

object Ramp {
  implicit val rampMacro: Macro[Ramp] = _.messages
}
