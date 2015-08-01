package checkers

import checkers.Player._
//remove if not needed

object Player {

  private var brain: Logic = _

  def main(args: Array[String]) {
    Thread.sleep(5000)
    val player = new Player()
    while (brain.notEnd()) {
      player.play()
    }
  }
}

class Player private () {

  private val eyes: Eyes = new Eyes()

  private val hands: Hands = new Hands()

  brain = new Logic()

  private def play() {
    brain.availableMoves.clear()
    brain.availableMoves = brain.getAvailableMoves(eyes)
    if (brain.notEnd() && brain.availableMoves.nonEmpty) {
      hands.move(brain.getBestMove(brain.availableMoves), brain.realBoard)
      println("")
    } else {
      println("******************" + "\n*** Game Over! ***" + "\n******************")
      hands.click4NewGame()
    }
  }
}
