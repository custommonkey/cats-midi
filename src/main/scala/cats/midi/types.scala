package cats.midi

import eu.timepit.refined.W
import eu.timepit.refined.api.{Max, Min, Refined, RefinedTypeOps}
import eu.timepit.refined.auto._
import eu.timepit.refined.numeric.Interval.Closed

object types {

  val uByteMin: Min[uByte] = Min.instance(0)
  val uByteMax: Max[uByte] = Max.instance(127)

  type uByte = Int Refined Closed[W.`0`.T, W.`127`.T]

  object uByte extends RefinedTypeOps.Numeric[uByte, Int]

}
