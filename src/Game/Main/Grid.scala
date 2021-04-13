package Game.Main

class Grid(height: Int, width: Int) {
  val y = (0 to height).toArray
  val x = (0 to width).toArray
  val yx = y.map(n => x.map(m => (n, m)))
}
