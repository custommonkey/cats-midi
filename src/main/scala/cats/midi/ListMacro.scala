package cats.midi

case class ListMacro(macros: List[Macro]) extends Macro

object ListMacro {
  def apply(macros: Macro*): ListMacro = new ListMacro(macros.toList)
}
