package cats.midi

import cats.midi.Brap.{Brap, BrapOps}
import cats.midi.Messages._
import cats.midi.ShortMessages._
import cats.midi.types.uByte

object Grid {

  implicit val eventMessages: Brap[uByte] =
    note => Messages(Event(noteOn(note), 0), Event(noteOff(note), 1))

  implicit val gridMessages: Brap[Grid] =
    grid =>
      grid.notes.foldLeft(Messages()) {
        case (msgs, note) => msgs ++ note.messages.map(_ + msgs.size.toLong)
    }

}

case class Grid(notes: uByte*)
