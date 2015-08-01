package checkers

import java.awt.Robot
import java.awt.event.InputEvent

import models.Move

import scala.collection.mutable.ArrayBuffer

//remove if not needed

class Hands {

  private val robot: Robot = new Robot()

  def move(availableMove: ArrayBuffer[Move], board: Array[Array[String]]) {

    System.out.print("Manager: ")
    for (m <- availableMove) {
      System.out.print(m + ", ")
      val start = m.startPoint
      val finish = m.finishPoint
      val startPosX = Eyes.startX + Eyes.plateSize * start.x + Eyes.plateSize / 2
      val startPosY = Eyes.startY + Eyes.plateSize * start.y + Eyes.plateSize / 2
      val finishPosX = Eyes.startX + Eyes.plateSize * finish.x + Eyes.plateSize / 2
      val finishPosY = Eyes.startY + Eyes.plateSize * finish.y + Eyes.plateSize / 2
      robot.mouseMove(startPosX, startPosY)
      robot.mousePress(InputEvent.BUTTON1_MASK)
      robot.mouseRelease(InputEvent.BUTTON1_MASK)
      Thread.sleep(1000)
      robot.mouseMove(finishPosX, finishPosY)
      robot.mousePress(InputEvent.BUTTON1_MASK)
      robot.mouseRelease(InputEvent.BUTTON1_MASK)
      board(finish.x)(finish.y) = board(start.x)(start.y)
      board(start.x)(start.y) = Eyes.getNameByNumber(0)
      Thread.sleep(1000)
    }
    println()
    Thread.sleep(1000)
  }

  def click4NewGame() {
    println("Waiting before new game 3")
    Thread.sleep(1000)
    println("Waiting before new game 2")
    Thread.sleep(1000)
    println("Waiting before new game 1")
    Thread.sleep(1000)
    println("*** New Game ! ***")
    robot.mouseMove(500, 200)
    robot.mousePress(InputEvent.BUTTON1_MASK)
    robot.mouseRelease(InputEvent.BUTTON1_MASK)
  }
}
