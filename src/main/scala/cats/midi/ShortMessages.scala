/**
  * Copyright Homeaway, Inc 2016-Present. All Rights Reserved.
  * No unauthorized use of this software.
  */
package cats.midi

import cats.midi.types.uByte
import cats.{Eq, Show}
import javax.sound.midi.ShortMessage
import javax.sound.midi.ShortMessage.{NOTE_OFF, NOTE_ON}
import eu.timepit.refined.auto._

object ShortMessages {
  def noteOn(note: uByte) = new ShortMessage(NOTE_ON, note, 100)
  def noteOff(note: uByte) = new ShortMessage(NOTE_OFF, note, 0)

  implicit val eqShortMessage: Eq[ShortMessage] = (a, b) =>
    a.getChannel == b.getChannel & a.getCommand == b.getCommand & a.getData1 == b.getData1 & a.getData2 == b.getData2

  implicit val showShortMessage: Show[ShortMessage] = m =>
    s"ShortMessage(${m.getStatus}, ${m.getData1}, ${m.getData2})"
}
