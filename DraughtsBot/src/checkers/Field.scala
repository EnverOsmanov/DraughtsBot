package checkers

import java.awt._
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import checkers.Field._
//remove if not needed

object Field {

  private val startX = 30

  private val startY = 55

  private val screenWidth = 1920

  private val screenHeight = 1080

  private val plateSize = 48

  private val desk = Array.ofDim[String](8, 8)

  private val getNameByNumber = Array("dark", "white", "pink", "pink_q", "yellow", "yellow_q")

  def main(args: Array[String]) {
    Thread.sleep(5000)
    val mc = new Field()
    mc.getCurrentField
    for (i <- 0 until 8) {
      for (j <- 0 until 8) {
        System.out.print(desk(j)(i) + "\t")
      }
      println()
    }
  }
}

class Field private () {

  val database: Map[String, BufferedImage] = Map(
    "dark" -> ImageIO.read(new File("base//dark.png")),
    "white" -> ImageIO.read(new File("base//white.png")),
    "pink" -> ImageIO.read(new File("base//pink.png")),
    "pink_q" -> ImageIO.read(new File("base//pink_q.png")),
    "yellow" -> ImageIO.read(new File("base//yellow.png")),
    "yellow_q" -> ImageIO.read(new File("base//yellow_q.png"))
  )
  private def captureScreen(): BufferedImage = {
    val screenSize = Toolkit.getDefaultToolkit.getScreenSize
    val screenRectangle = new Rectangle(screenSize)
    val robot = new Robot()
    val image = robot.createScreenCapture(screenRectangle)
    ImageIO.write(image, "png", new File("1.png"))
    image
  }

  private def cutPlate(image: BufferedImage, x: Int, y: Int): BufferedImage = {
    val dst = new BufferedImage(plateSize, plateSize, BufferedImage.TYPE_INT_RGB)
    val g = dst.createGraphics()
    g.drawImage(image, x, y, screenWidth, screenHeight, null)
    g.dispose()
    dst
  }

  private def getPlateState(digit: BufferedImage): String = {
    val percent = Array.ofDim[Long](6)
    for {
      k <- percent.indices
      i <- 0 until 48
      j <- 0 until 48
    } {
      percent(k) += Math.abs(digit.getRGB(j, i) - database(getNameByNumber(k)).getRGB(j, i))
    }
    getNameByNumber(min(percent))
  }

  private def min(array: Array[Long]): Int = {
    var minValue = array(0)
    var minIndex = 0
    for (i <- array.indices if array(i) < minValue) {
      minValue = array(i)
      minIndex = i
    }
    minIndex
  }

  private def cutPlateByIndex(screen: BufferedImage, i: Int, j: Int): BufferedImage = {
    cutPlate(screen, -startX - 1 - plateSize * i, -startY - 1 - plateSize * j)
  }

  def getCurrentField: Array[Array[String]] = {
    Thread.sleep(100)
    val screen = captureScreen()
    for {
      i <- desk.indices
      j <- desk(0).indices
    } {
      desk(i)(j) = getPlateState(cutPlateByIndex(screen, i, j))
    }
    desk
  }
}
