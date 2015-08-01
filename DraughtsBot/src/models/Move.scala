package models

//remove if not needed
import scala.collection.JavaConversions._

case class Move(startPoint: Pointy, finishPoint: Pointy, priority: Int = 0) {

  override def toString: String = {
    "" + (startPoint.x + 65).toChar + (8 - startPoint.y) +
      "-" +
      (finishPoint.x + 65).toChar +
      (8 - finishPoint.y) +
      (if (priority > 0) " p" + priority else "")
  }
}
