package cats.midi

import cats.kernel.CommutativeMonoid

trait Macro

object Macro {
  implicit val rampMonoid: CommutativeMonoid[Macro] = new CommutativeMonoid[Macro] {
    override def empty: Macro = ListMacro(Nil)
    override def combine(x: Macro, y: Macro): Macro =
      (x, y) match {
        case (ListMacro(Nil), b)          => b
        case (a, ListMacro(Nil))          => a
        case (ListMacro(a), ListMacro(b)) => ListMacro(a ++ b)
        case (ListMacro(a), b)            => ListMacro(a :+ b)
        case (a, ListMacro(b))            => ListMacro(a +: b)
        case (a, b)                       => ListMacro(a, b)
      }
  }
}
