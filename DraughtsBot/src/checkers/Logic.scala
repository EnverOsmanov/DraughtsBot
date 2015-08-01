package checkers

import java.util.ConcurrentModificationException

import checkers.Logic._
import models.{Move, Pointy}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

//remove if not needed
import scala.collection.JavaConversions._

object Logic {

  private val rand = new Random()

  private val playerSide = "yellow"
}

class Logic() {

  var availableMoves = ArrayBuffer[Move]()

  private val priorityMoves: ArrayBuffer[Move] = ArrayBuffer[Move]()

  private val bestMoveOrder: ArrayBuffer[Move] = ArrayBuffer[Move]()

  var realBoard: Array[Array[String]] = Array.ofDim[String](8, 8)

  private val enemySide: String = if (playerSide == "yellow") "pink" else "yellow"

  private def addMovesByCoords(v: Int,
                               h: Int,
                               isQueen: Boolean,
                               priority: Int) {
    if (isQueen) {
      var tmpX: Int = 0
      var tmpY: Int = 0
      var tmpBeatX: Int = 0
      var tmpBeatY: Int = 0
      tmpX = v
      tmpY = h
      while (true) {
        tmpBeatX = 1
        tmpBeatY = 1
        tmpX += 1
        tmpY += 1
        if ((tmpX >= 0) && (tmpX < 8) && (tmpY >= 0) && (tmpY < 8)) {
          if (realBoard(tmpX)(tmpY) == "dark") {
            availableMoves.add(new Move(new Pointy(v, h), new Pointy(tmpX, tmpY)))
          } else if (realBoard(tmpX)(tmpY).startsWith(enemySide)) {
            while (true) {
              if ((tmpX + tmpBeatX >= 0) && (tmpX + tmpBeatX < 8) && (tmpY + tmpBeatY >= 0) &&
                (tmpY + tmpBeatY < 8)) {
                if (realBoard(tmpX + tmpBeatX)(tmpY + tmpBeatY) == "dark") {
                  availableMoves.add(new Move(new Pointy(v, h), new Pointy(tmpX + tmpBeatX, tmpY + tmpBeatY),
                    priority + 1))
                }
                tmpBeatX += 1
                tmpBeatY += 1
              } else {
                //break
              }
            }
          }
        } else {
          //break
        }
      }
      tmpX = v
      tmpY = h

      var variable = true
      while (variable) {
        tmpBeatX = 1
        tmpBeatY = 1
        tmpX += 1
        tmpY -= 1
        if ((tmpX >= 0) && (tmpX < 8) && (tmpY >= 0) && (tmpY < 8)) {
          if (realBoard(tmpX)(tmpY) == "dark") {
            availableMoves.add(new Move(new Pointy(v, h), new Pointy(tmpX, tmpY)))
          } else if (realBoard(tmpX)(tmpY).startsWith(enemySide)) {
            if ((tmpX + tmpBeatX >= 0) && (tmpX + tmpBeatX < 8) && (tmpY + tmpBeatY >= 0) &&
              (tmpY + tmpBeatY < 8)) {
              while (true) {
                if (realBoard(tmpX + tmpBeatX)(tmpY + tmpBeatY) == "dark") {
                  availableMoves.add(new Move(new Pointy(v, h), new Pointy(tmpX + tmpBeatX, tmpY - tmpBeatY),
                    priority + 1))
                } else {
                  //break
                }
                tmpBeatX += 1
                tmpBeatY -= 1
              }
            }
          }
        }
        else variable = false
      }

      tmpX = v
      tmpY = h
      while (true) {
        tmpBeatX = 1
        tmpBeatY = 1
        tmpX -= 1
        tmpY += 1
        if ((tmpX >= 0) && (tmpX < 8) && (tmpY >= 0) && (tmpY < 8)) {
          if (realBoard(tmpX)(tmpY) == "dark") {
            availableMoves.add(new Move(new Pointy(v, h), new Pointy(tmpX, tmpY)))
          } else if (realBoard(tmpX)(tmpY).startsWith(enemySide)) {
            if ((tmpX + tmpBeatX >= 0) && (tmpX + tmpBeatX < 8) && (tmpY + tmpBeatY >= 0) &&
              (tmpY + tmpBeatY < 8)) {
              while (true) {
                if (realBoard(tmpX + tmpBeatX)(tmpY + tmpBeatY) == "dark") {
                  availableMoves.add(new Move(new Pointy(v, h), new Pointy(tmpX - tmpBeatX, tmpY + tmpBeatY),
                    priority + 1))
                } else {
                  //break
                }
                tmpBeatX -= 1
                tmpBeatY += 1
              }
            }
          }
        } else {
          //break
        }
      }
      tmpX = v
      tmpY = h
      while (true) {
        tmpBeatX = 1
        tmpBeatY = 1
        tmpX -= 1
        tmpY -= 1
        if ((tmpX >= 0) && (tmpX < 8) && (tmpY >= 0) && (tmpY < 8)) {
          if (realBoard(tmpX)(tmpY) == "dark") {
            availableMoves.add(new Move(new Pointy(v, h), new Pointy(tmpX, tmpY)))
          } else if (realBoard(tmpX)(tmpY).startsWith(enemySide)) {
            if ((tmpX + tmpBeatX >= 0) && (tmpX + tmpBeatX < 8) && (tmpY + tmpBeatY >= 0) &&
              (tmpY + tmpBeatY < 8)) {
              while (true) {
                if (realBoard(tmpX + tmpBeatX)(tmpY + tmpBeatY) == "dark") {
                  availableMoves.add(new Move(new Pointy(v, h), new Pointy(tmpX - tmpBeatX, tmpY - tmpBeatY),
                    priority + 1))
                } else {
                  //break
                }
                tmpBeatX -= 1
                tmpBeatY -= 1
              }
            }
          }
        } else {
          //break
        }
      }
    } else {
      if (couldCapture(v, h, 1, 1))
        availableMoves.add(
          new Move(new Pointy(v, h), new Pointy(v + 2, h + 2), priority + 1))
      if (couldCapture(v, h, 1, -1))
        availableMoves.add(
          new Move(new Pointy(v, h), new Pointy(v + 2, h - 2), priority + 1))
      if (Pointy.isOnBoard(v + 1, h - 1)) {
        if (realBoard(v + 1)(h - 1).startsWith("dark")) {
          availableMoves.add(
            new Move(new Pointy(v, h), new Pointy(v + 1, h - 1)))
        }
        else if (realBoard(v + 1)(h - 1).startsWith(enemySide)) {
          if (Pointy.isOnBoard(v + 2, h - 2)) {
            if (realBoard(v + 2)(h - 2) == "dark") {
              availableMoves.add(
                new Move(new Pointy(v, h), new Pointy(v + 2, h - 2), priority + 1))
            }
          }
        }
      }
      if (Pointy.isOnBoard(v - 1, h + 1)) {
        if (realBoard(v - 1)(h + 1) == "dark") {
        } else if (realBoard(v - 1)(h + 1).startsWith(enemySide)) {
          if (Pointy.isOnBoard(v - 2, h + 2)) {
            if (realBoard(v - 2)(h + 2) == "dark") {
              availableMoves.add(new Move(new Pointy(v, h), new Pointy(v - 2, h + 2), priority + 1))
            }
          }
        }
      }
      if (Pointy.isOnBoard(v - 1, h - 1)) {
        if (realBoard(v - 1)(h - 1) == "dark") {
          availableMoves.add(new Move(new Pointy(v, h), new Pointy(v - 1, h - 1)))
        } else if (realBoard(v - 1)(h - 1).startsWith(enemySide)) {
          if (Pointy.isOnBoard(v - 2, h - 2)) {
            if (realBoard(v - 2)(h - 2) == "dark") {
              availableMoves.add(new Move(new Pointy(v, h), new Pointy(v - 2, h - 2), priority + 1))
            }
          }
        }
      }
    }
  }

  private def recursiveMovesCheck(priorityIndex: Int) {
    var isDoubles = false
    try {
      for (m <- availableMoves if m.priority > priorityIndex) {
        addMovesByCoords(m.finishPoint.getX, m.finishPoint.getY, isQueen = false, priorityIndex + 1)
        isDoubles = true
      }
      if (isDoubles) {
        recursiveMovesCheck(priorityIndex + 1)
      }
    } catch {
      case e: ConcurrentModificationException =>
    }
  }

  def getAvailableMoves(eyes: Eyes): ArrayBuffer[Move] = {
    realBoard = eyes.getCurrentField
    for {
      i <- realBoard.indices
      j <- realBoard(0).indices if realBoard(i)(j).startsWith(playerSide)
    } {
      addMovesByCoords(i, j, realBoard(i)(j).endsWith("_q"), 0)
    }
    recursiveMovesCheck(0)
    availableMoves
  }

  def notEnd(): Boolean = {
    var player1 = false
    var player2 = false
    for (aBoard <- realBoard; j <- realBoard(0).indices) {
      if (aBoard(j) == null) {
        return true
      }
      if (!player1) {
        if (aBoard(j).startsWith(playerSide)) {
          player1 = true
        }
      }
      if (!player2) {
        if (aBoard(j).startsWith(enemySide)) {
          player2 = true
        }
      }
      if (player1 && player2) {
        return true
      }
    }
    if (player1) {
      println("******************" + "\n*** You Win ! ***")
    } else {
      println("******************" + "\n*** You lose! ***")
    }
    false
  }

  private def getMaxPriority(ListMoves: ArrayBuffer[Move]): Int = {
    var startPriority = 0
    for (m <- ListMoves if m.priority > startPriority) {
      startPriority = m.priority
    }
    startPriority
  }

  def getBestMove(moves: ArrayBuffer[Move]): ArrayBuffer[Move] = {
    val maxPriority: Int = getMaxPriority(moves)
    priorityMoves.clear()
    bestMoveOrder.clear()
    for (m <- moves) {
      System.out.print(m + ", ")
      if (m.priority == maxPriority) {
        priorityMoves.add(m)
      }
    }
    println()
    val r = rand.nextInt(priorityMoves.size)
    var maxPriorityMove = priorityMoves.get(r)
    var minPriority = maxPriority
    bestMoveOrder.prepend(maxPriorityMove)
    while (maxPriorityMove.priority == minPriority && minPriority > 1) {
      for (m <- moves if maxPriorityMove.startPoint == m.finishPoint) {
        bestMoveOrder.add(0, m)
        maxPriorityMove = m
        minPriority -= 1
        //break
      }
      minPriority -= 1
    }
    bestMoveOrder
  }

  private def couldCapture(v: Int,
                           h: Int,
                           directionV: Int,
                           directionH: Int): Boolean = {
    (Pointy.isOnBoard(v + directionV, h + directionH) &&
      realBoard(v + directionV)(h + directionH).startsWith(enemySide) &&
      Pointy.isOnBoard(v + 2 * directionV, h + 2 * directionH)) &&
      realBoard(v + 2 * directionV)(h + 2 * directionH) == "dark"
  }
}
