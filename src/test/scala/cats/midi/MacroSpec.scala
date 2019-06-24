package cats.midi

import cats.kernel.Eq
import cats.kernel.laws.discipline.CommutativeMonoidTests
import cats.midi.Things._
import cats.tests.CatsSuite

final class MacroSpec extends CatsSuite {

  implicit def equality[A]: Eq[Macro] = Eq.fromUniversalEquals

  checkAll("Macro.CommutativeMonoidLaws", CommutativeMonoidTests[Macro].commutativeMonoid)

}
