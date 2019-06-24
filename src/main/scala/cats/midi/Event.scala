package cats.midi

import cats.Show
import cats.implicits._
import cats.kernel.Eq
import cats.midi.Messages.Messages
import cats.midi.ShortMessages._
import javax.sound.midi.ShortMessage
import javax.sound.midi.ShortMessage._

final case class Event(msg: ShortMessage, timestamp: Long) {

  private val status: Int => String = {
    case ACTIVE_SENSING        => "ACTIVE_SENSING"
    case CHANNEL_PRESSURE      => "CHANNEL_PRESSURE"
    case CONTINUE              => "CONTINUE"
    case CONTROL_CHANGE        => "CONTROL_CHANGE"
    case END_OF_EXCLUSIVE      => "END_OF_EXCLUSIVE"
    case MIDI_TIME_CODE        => "MIDI_TIME_CODE"
    case NOTE_OFF              => "NOTE_OFF"
    case NOTE_ON               => "NOTE_ON"
    case PITCH_BEND            => "PITCH_BEND"
    case POLY_PRESSURE         => "POLY_PRESSURE"
    case PROGRAM_CHANGE        => "PROGRAM_CHANGE"
    case SONG_POSITION_POINTER => "SONG_POSITION_POINTER"
    case SONG_SELECT           => "SONG_SELECT"
    case START                 => "START"
    case STOP                  => "STOP"
    case SYSTEM_RESET          => "SYSTEM_RESET"
    case TIMING_CLOCK          => "TIMING_CLOCK"
    case TUNE_REQUEST          => "TUNE_REQUEST"
    case s                     => s.toString
  }

  implicit val showShortMessage: Show[ShortMessage] = m =>
    s"${status(m.getStatus)}, ${m.getData1}, ${m.getData2}"

  override def toString: String = show"Event($msg, $timestamp)"

  override def equals(obj: Any): Boolean =
    obj match {
      case e: Event => Eq[ShortMessage].eqv(msg, e.msg)
      case _        => false
    }

  def +(timestamp: Long): Event =
    copy(timestamp = this.timestamp + timestamp)
  def ++(e: Event): Messages = Messages(this, e)
}

object Event {

  implicit def order: Ordering[Event] =
    (x: Event, y: Event) => implicitly[Ordering[Long]].compare(x.timestamp, y.timestamp)

  implicit def equality[A]: Eq[Event] = Eq.fromUniversalEquals

}
