package cats.midi

import cats.midi.Messages.Messages
import simulacrum._

@typeclass trait ToMessages[A] {
  def messages(a: A): Messages
}
