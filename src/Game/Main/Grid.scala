package Game.Main
import scala.collection.mutable.Buffer

class Grid(width: Int, height: Int) {
  val gridSize = width * height
  private val y = (0 until height).toArray

  private val x = (0 until width).toArray

  val locations = x.flatMap(n => y.map(m => (n, m))).toBuffer

  def contains(x:Int, y: Int) = x >= 0 && x < this.width && y >= 0 && y < this.height

  def neighbours(x: Int, y: Int) = {
    val neighbourLocations = Buffer[(Int, Int)]()
    if (contains(x, y - 1)) neighbourLocations.+=:(x, y - 1)
    if (contains(x, y + 1)) neighbourLocations.+=:(x, y + 1)
    if (contains(x - 1, y)) neighbourLocations.+=:(x - 1, y)
    if (contains(x + 1, y)) neighbourLocations.+=:(x + 1, y)
    neighbourLocations
  }

}
