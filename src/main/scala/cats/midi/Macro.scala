package cats.midi

import cats.midi.Messages.Messages

object Macro {

  trait Macro[A] extends (A => Messages)

  implicit class BrapOps[A](a: A) {
    def messages(implicit brap: Macro[A]): Messages = brap(a)
  }
}
