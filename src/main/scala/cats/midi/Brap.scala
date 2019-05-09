package cats.midi

import cats.midi.Messages.Messages

object Brap {

  trait Brap[A] extends (A => Messages)

  implicit class BrapOps[A](a: A) {
    def messages(implicit brap: Brap[A]): Messages = brap(a)
  }
}
