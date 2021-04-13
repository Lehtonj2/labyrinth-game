package Game.Main

class Grid(width: Int, height: Int) {
  private val y = (0 to height).toArray
  private val x = (0 to width).toArray
  val locations = y.map(n => x.map(m => (m, n)))

}
