package cats.midi

import cats.midi.types.uByte
import cats.{Eq, Show}
import eu.timepit.refined.auto._
import javax.sound.midi.ShortMessage
import javax.sound.midi.ShortMessage._

object ShortMessages {
  def noteOn(note: uByte): ShortMessage  = new ShortMessage(NOTE_ON, note, 100)
  def noteOff(note: uByte): ShortMessage = new ShortMessage(NOTE_OFF, note, 0)
  def cc(value: uByte): ShortMessage     = new ShortMessage(CONTROL_CHANGE, value, 0)

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  implicit final class AnyOps[A](self: A) {
    def ===(other: A): Boolean = self == other
  }

  implicit val eqShortMessage: Eq[ShortMessage] = (a, b) =>
    (a.getChannel === b.getChannel) &
      (a.getCommand === b.getCommand) &
      (a.getData1 === b.getData1) &
      (a.getData2 === b.getData2)

  private val status: Int => String = {
    case NOTE_ON        => "NOTE_ON"
    case NOTE_OFF       => "NOTE_OFF"
    case CONTROL_CHANGE => "CONTROL_CHANGE"
    case s              => s.toString
  }

  implicit val showShortMessage: Show[ShortMessage] = m =>
    s"ShortMessage(${status(m.getStatus)}, ${m.getData1}, ${m.getData2})"
}
