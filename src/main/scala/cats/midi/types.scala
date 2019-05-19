package cats.midi

import eu.timepit.refined.W
import eu.timepit.refined.api.{Refined, RefinedTypeOps}
import eu.timepit.refined.auto._
import eu.timepit.refined.numeric.Interval.Closed

object types {

  type uByte = Int Refined Closed[W.`0`.T, W.`127`.T]

  object uByte extends RefinedTypeOps[uByte, Int] {
    val Max: uByte = 127
  }

}
