package cats.midi

import cats.midi.Macro.{Macro, BrapOps}
import cats.midi.Messages._
import cats.midi.ShortMessages._
import cats.midi.types.uByte

final case class Grid(notes: uByte*) {

  implicit val eventMessages: Macro[uByte] =
    note => Messages(Event(noteOn(note), 0), Event(noteOff(note), 1))

  val messages: Messages =
    notes.foldLeft(Messages()) {
      case (msgs, note) => msgs ++ note.messages.map(_ + msgs.size.toLong)
    }
}

object Grid {
  implicit val gridMacro: Macro[Grid] = _.messages
}
