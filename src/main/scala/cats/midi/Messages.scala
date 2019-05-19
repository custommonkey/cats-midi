/**
  * Copyright Homeaway, Inc 2016-Present. All Rights Reserved.
  * No unauthorized use of this software.
  */
package cats.midi

import cats.kernel.CommutativeMonoid
import cats.midi.ShortMessages._
import cats.{Eq, Show}
import javax.sound.midi.{MidiMessage, ShortMessage}

import scala.collection.immutable.SortedSet

object Messages {

  type Messages = SortedSet[Event[MidiMessage]] //TODO: Should this be parametric

  def apply(m: Event[MidiMessage]*): Messages = SortedSet(m: _*)

  implicit val messagesMonoid: CommutativeMonoid[Messages] =
    new CommutativeMonoid[Messages] {
      override val empty: Messages                             = SortedSet()
      override def combine(x: Messages, y: Messages): Messages = x ++ y
    }

  implicit val eqMidiMessage: Eq[MidiMessage] = {
    case (a: ShortMessage, b: ShortMessage) => Eq[ShortMessage].eqv(a, b)
    case _                                  => false
  }

  implicit val showMidiMessage: Show[MidiMessage] = {
    case m: ShortMessage => Show[ShortMessage].show(m)
  }
}
