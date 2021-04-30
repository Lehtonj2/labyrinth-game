package Game.Main
import scala.collection.mutable.Buffer

class Grid(val width: Int, val height: Int) {
  val gridSize = width * height
  private val y = (0 until height).toArray

  private val x = (0 until width).toArray

  val locations = x.flatMap(n => y.map(m => (n, m))).toBuffer //Grid locations.

  def contains(x:Int, y: Int) = x >= 0 && x < this.width && y >= 0 && y < this.height //Checks if grid contains a location.

  def neighbours(x: Int, y: Int) = { //Returns the locations from 4 main compass-directions.
    val neighbourLocations = Buffer[((Int, Int),(String))]()
    if (contains(x, y - 1)) neighbourLocations.+=:((x, y - 1), "N")
    if (contains(x, y + 1)) neighbourLocations.+=:((x, y + 1), "S")
    if (contains(x - 1, y)) neighbourLocations.+=:((x - 1, y), "W")
    if (contains(x + 1, y)) neighbourLocations.+=:((x + 1, y), "E")
    neighbourLocations
  }
  def weavableNeighbours(x: Int, y: Int) = { //Returns the locations from 4 main compass-directions one location away.
    val neighbourLocations = Buffer[((Int, Int),(String))]()
    if (contains(x, y - 2)) neighbourLocations.+=:((x, y - 2), "N")
    if (contains(x, y + 2)) neighbourLocations.+=:((x, y + 2), "S")
    if (contains(x - 2, y)) neighbourLocations.+=:((x - 2, y), "W")
    if (contains(x + 2, y)) neighbourLocations.+=:((x + 2, y), "E")
    neighbourLocations
  }
  def beforeWeave(x: Int, y: Int, dir: String) = { //Checks the location in stack from before the start of a weave.
    dir match {
      case "N" => {
        (x, y + 1)
      }
      case "S" => {
        (x, y - 1)
      }
      case "W" => {
        (x + 1, y)
      }
      case "E" => {
        (x - 1, y)
      }
    }
    }



}


