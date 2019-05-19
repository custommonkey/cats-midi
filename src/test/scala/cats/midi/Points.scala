/**
  * Copyright Homeaway, Inc 2016-Present. All Rights Reserved.
  * No unauthorized use of this software.
  */
package cats.midi

import com.cibo.evilplot.numeric.Point
import javax.sound.midi.ShortMessage
import simulacrum.{op, typeclass}

@typeclass trait Points[A] {
  @op("points") def points(m: A): Seq[Point]
}

object Points {

  implicit def macroPoints[A](implicit macrow: Macro[A]): Points[A] =
    a =>
      macrow.messages(a).toSeq.flatMap { e =>
        e.msg match {
          case m: ShortMessage =>
            Some(
              Point(
                e.timestamp.toDouble,
                m.getData1.toDouble
              ))
        }
    }
}
