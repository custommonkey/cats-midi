/**
  * Copyright Homeaway, Inc 2016-Present. All Rights Reserved.
  * No unauthorized use of this software.
  */
package cats.midi

import eu.timepit.refined.api.Refined
import eu.timepit.refined.W
import eu.timepit.refined.numeric.Interval.Closed

object types {

  type uByte = Int Refined Closed[W.`0`.T, W.`127`.T]

}
