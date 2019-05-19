package cats.midi

import cats.midi.Messages.Messages
import simulacrum._

@typeclass trait Macro[A] {
  @op("messages") def messages(a: A): Messages
}
