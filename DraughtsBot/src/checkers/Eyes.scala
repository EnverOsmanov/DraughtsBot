package checkers


import java.awt.{Toolkit, Rectangle, Robot}
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

object Eyes {
  val startX = 28
  val getNameByNumber: Array[String] = Array("dark", "white", "pink", "pink_q", "yellow", "yellow_q")
  val startY = 53

  val plateSize = 48

  def main(args: Array[String]) {
    Thread.sleep(5000)
    val mc = new Eyes()
    mc.getCurrentField
    for (i <- 0 until 8) {
      for (j <- 0 until 8) {
        print(mc.desk(j)(i) + "\t")
      }
      println()
    }
  }
}

class Eyes() {
  val desk = Array.ofDim[String](8, 8)

  def captureScreen(): BufferedImage = {
    val screenSize = Toolkit.getDefaultToolkit.getScreenSize
    val screenRectangle = new Rectangle(screenSize)
    val robot = new Robot()
    robot.createScreenCapture(screenRectangle)
  }

  def getCurrentField: Array[Array[String]] = {
    Thread.sleep(100)
    val screen = captureScreen()
    for {
      i <- desk.indices
      j <- desk(0).indices
    }
      desk(i)(j) = getPlateState(
        cutPlateByIndex(screen, i, j))

    desk
  }


  def getPlateState(digit: BufferedImage): String = {
    val percent = Array.ofDim[Long](6)

    for {
      k <- percent.indices
      i <- 0 until 48
      j <- 0 until 48
    } {
      percent(k) += Math.abs(digit.getRGB(j, i) - database(Eyes.getNameByNumber(k)).getRGB(j, i))
    }

    Eyes.getNameByNumber(min(percent))
  }

  private def cutPlateByIndex(screen: BufferedImage, i: Int, j: Int): BufferedImage = {
    cutPlate(screen, - Eyes.startX - 1 - Eyes.plateSize * i, - Eyes.startY - 1 - Eyes.plateSize * j)
  }

  private def cutPlate(image: BufferedImage, x: Int, y: Int): BufferedImage = {
    val dst = new BufferedImage(Eyes.plateSize, Eyes.plateSize, BufferedImage.TYPE_INT_RGB)
    val g = dst.createGraphics()
    g.drawImage(image, x, y, screenWeight, screenHeight, null)
    g.dispose()
    dst
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

  val database: Map[String, BufferedImage] = Map(
    "dark" -> ImageIO.read(new File("c://base//dark.png")),
    "white" -> ImageIO.read(new File("c://base//white.png")),
    "pink" -> ImageIO.read(new File("c://base//pink.png")),
    "pink_q" -> ImageIO.read(new File("c://base//pink_q.png")),
    "yellow" -> ImageIO.read(new File("c://base//yellow.png")),
    "yellow_q" -> ImageIO.read(new File("c://base//yellow_q.png"))
  )




  private val screenWeight = 1280

  private val screenHeight = 800


}
