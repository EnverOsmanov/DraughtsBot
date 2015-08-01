package models

/**
 * Created by Enver on 01.08.2015.
 */
case class Pointy(x: Int, y: Int)

object Pointy {

  def isOnBoard(v: Int, h: Int): Boolean =
    (v >= 0) && (v < 8) && (h >= 0) && (h < 8)

}
