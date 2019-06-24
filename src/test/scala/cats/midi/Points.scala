package cats.midi

import cats.midi.Messages.Messages
import com.cibo.evilplot.numeric.Point
import javax.sound.midi.ShortMessage
import simulacrum.typeclass

@typeclass trait Points[A] {
  def points(m: A): Seq[Point]
}

object Points {

  implicit val msgPoints: Points[Messages] = _.toSeq.flatMap { e =>
    e.msg match {
      case m: ShortMessage => Some(Point(e.timestamp.toDouble, m.getData1.toDouble))
    }
  }

  implicit def macroPoints[A](implicit macrow: ToMessages[A]): Points[A] =
    a => msgPoints.points(macrow.messages(a))
}
