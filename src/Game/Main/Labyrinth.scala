package Game.Main
import scala.collection.mutable.Buffer
import scala.util.Random

class Labyrinth {
  val bridgeLocations = Buffer[(Int, Int, String)]()
  def closeWalls(x: Int, y: Int, dir: String) = {
    val walls = Buffer[(Int, Int)]()
    dir match {
      case "N" => {
        walls += ((x * 3, y * 3 + 2))
        walls += ((x * 3 + 1, y * 3 + 2))
      }
      case "S" => {
        walls += ((x * 3, y * 3 - 2))
        walls += ((x * 3 + 1, y * 3 - 2))
      }
      case "W" => {
        walls += ((x * 3 + 2, y * 3))
        walls += ((x * 3 + 2, y * 3 + 1))
      }
      case "E" => {
        walls += ((x * 3 - 2, y * 3))
        walls += ((x * 3 - 2, y * 3 + 1))
      }
    }
    walls
  }
  def createLabyrinth(grid: Grid) = {
    val labyrinthSize = grid.gridSize
    val drawFloors = Buffer[Floor]()
    val visitedCells = Buffer[(Int, Int)]()
    val floorCells = Buffer[(Int, Int)]()
    val firstCell = grid.locations(Random.nextInt(labyrinthSize))
    floorCells += firstCell
    visitedCells += firstCell
    drawFloors += new Floor(firstCell._1 * 3, firstCell._2 * 3, false)
    drawFloors += new Floor(firstCell._1 * 3 + 1, firstCell._2 * 3, false)
    drawFloors += new Floor(firstCell._1 * 3, firstCell._2 * 3 + 1, false)
    drawFloors += new Floor(firstCell._1 * 3 + 1, firstCell._2 * 3 + 1, false)
    while (floorCells.nonEmpty) {
      val next = floorCells.reverse.head
      val neighbours = grid.neighbours(next._1, next._2).filter(n => !visitedCells.contains(n._1)).filter(n => closeWalls(n._1._1, n._1._2, n._2).forall(n => !drawFloors.contains(new Floor(n._1, n._2, true))))
      var weavableNeighbours = grid.weavableNeighbours(next._1, next._2).filter(n => !visitedCells.contains(n._1))
      if (floorCells.size >= 2) weavableNeighbours = weavableNeighbours.filter(n => !(floorCells.reverse(1) == grid.beforeWeave(n._1._1, n._1._2, n._2)))
        if (neighbours.nonEmpty) { //normal
          val neighbour = neighbours(Random.nextInt(neighbours.size))
          floorCells += neighbour._1
          visitedCells += neighbour._1
          drawFloors += new Floor(neighbour._1._1 * 3, neighbour._1._2 * 3, false)
          drawFloors += new Floor(neighbour._1._1 * 3 + 1, neighbour._1._2 * 3, false)
          drawFloors += new Floor(neighbour._1._1 * 3, neighbour._1._2 * 3 + 1, false)
          drawFloors += new Floor(neighbour._1._1 * 3 + 1, neighbour._1._2 * 3 + 1, false)
          neighbour._2 match {
            case "N" => {
              drawFloors += new Floor(neighbour._1._1 * 3, neighbour._1._2 * 3 + 1 + 1, false)
              drawFloors += new Floor(neighbour._1._1 * 3 + 1, neighbour._1._2 * 3 + 1 + 1, false)
            }
            case "S" => {
              drawFloors += new Floor(neighbour._1._1 * 3, neighbour._1._2 * 3 - 1, false)
              drawFloors += new Floor(neighbour._1._1 * 3 + 1, neighbour._1._2 * 3 - 1, false)
            }
            case "W" => {
              drawFloors += new Floor(neighbour._1._1 * 3 + 1 + 1, neighbour._1._2 * 3, false)
              drawFloors += new Floor(neighbour._1._1 * 3 + 1 + 1, neighbour._1._2 * 3 + 1, false)
            }
            case "E" => {
              drawFloors += new Floor(neighbour._1._1 * 3 - 1, neighbour._1._2 * 3, false)
              drawFloors += new Floor(neighbour._1._1 * 3 - 1, neighbour._1._2 * 3 + 1, false)
            }
          }
        }else if (weavableNeighbours.nonEmpty) { //weave
          val neighbour = weavableNeighbours(Random.nextInt(weavableNeighbours.size))
          val neighbours = grid.neighbours(neighbour._1._1, neighbour._1._2)
          floorCells += neighbour._1
          visitedCells += neighbour._1
          drawFloors += new Floor(neighbour._1._1 * 3, neighbour._1._2 * 3, false)
          drawFloors += new Floor(neighbour._1._1 * 3 + 1, neighbour._1._2 * 3, false)
          drawFloors += new Floor(neighbour._1._1 * 3, neighbour._1._2 * 3 + 1, false)
          drawFloors += new Floor(neighbour._1._1 * 3 + 1, neighbour._1._2 * 3 + 1, false)

          neighbour._2 match {
            case "N" => {
              drawFloors += new Floor(neighbour._1._1 * 3, neighbour._1._2 * 3 + 2, true)
              drawFloors += new Floor(neighbour._1._1 * 3 + 1, neighbour._1._2 * 3 + 2, true)
              drawFloors += new Floor(neighbour._1._1 * 3, neighbour._1._2 * 3 + 3, true)
              drawFloors += new Floor(neighbour._1._1 * 3 + 1, neighbour._1._2 * 3 + 3, true)
              drawFloors += new Floor(neighbour._1._1 * 3, neighbour._1._2 * 3 + 4, true)
              drawFloors += new Floor(neighbour._1._1 * 3 + 1, neighbour._1._2 * 3 + 4, true)
              drawFloors += new Floor(neighbour._1._1 * 3, neighbour._1._2 * 3 + 5, true)
              drawFloors += new Floor(neighbour._1._1 * 3 + 1, neighbour._1._2 * 3 + 5, true)

              this.bridgeLocations += ((neighbour._1._1 * 3, neighbour._1._2 * 3 + 2, "N"))
              this.bridgeLocations += ((neighbour._1._1 * 3 + 1, neighbour._1._2 * 3 + 2, "N"))
              this.bridgeLocations += ((neighbour._1._1 * 3, neighbour._1._2 * 3 + 3, "N"))
              this.bridgeLocations += ((neighbour._1._1 * 3 + 1, neighbour._1._2 * 3 + 3, "N"))
              this.bridgeLocations += ((neighbour._1._1 * 3, neighbour._1._2 * 3 + 4, "N"))
              this.bridgeLocations += ((neighbour._1._1 * 3 + 1, neighbour._1._2 * 3 + 4, "N"))
              this.bridgeLocations += ((neighbour._1._1 * 3, neighbour._1._2 * 3 + 5, "N"))
              this.bridgeLocations += ((neighbour._1._1 * 3 + 1, neighbour._1._2 * 3 + 5, "N"))

            }
            case "S" => {
              drawFloors += new Floor(neighbour._1._1 * 3, neighbour._1._2 * 3 - 1, true)
              drawFloors += new Floor(neighbour._1._1 * 3 + 1, neighbour._1._2 * 3 - 1, true)
              drawFloors += new Floor(neighbour._1._1 * 3, neighbour._1._2 * 3 - 2, true)
              drawFloors += new Floor(neighbour._1._1 * 3 + 1, neighbour._1._2 * 3 - 2, true)
              drawFloors += new Floor(neighbour._1._1 * 3, neighbour._1._2 * 3 - 3, true)
              drawFloors += new Floor(neighbour._1._1 * 3 + 1, neighbour._1._2 * 3 - 3, true)
              drawFloors += new Floor(neighbour._1._1 * 3, neighbour._1._2 * 3 - 4, true)
              drawFloors += new Floor(neighbour._1._1 * 3 + 1, neighbour._1._2 * 3 - 4, true)

              this.bridgeLocations += ((neighbour._1._1 * 3, neighbour._1._2 * 3 - 1, "S"))
              this.bridgeLocations += ((neighbour._1._1 * 3 + 1, neighbour._1._2 * 3 - 1, "S"))
              this.bridgeLocations += ((neighbour._1._1 * 3, neighbour._1._2 * 3 - 2, "S"))
              this.bridgeLocations += ((neighbour._1._1 * 3 + 1, neighbour._1._2 * 3 - 2, "S"))
              this.bridgeLocations += ((neighbour._1._1 * 3, neighbour._1._2 * 3 - 3, "S"))
              this.bridgeLocations += ((neighbour._1._1 * 3 + 1, neighbour._1._2 * 3 - 3, "S"))
              this.bridgeLocations += ((neighbour._1._1 * 3, neighbour._1._2 * 3 - 4, "S"))
              this.bridgeLocations += ((neighbour._1._1 * 3 + 1, neighbour._1._2 * 3 - 4, "S"))

            }
            case "W" => {
              drawFloors += new Floor(neighbour._1._1 * 3 + 2, neighbour._1._2 * 3, true)
              drawFloors += new Floor(neighbour._1._1 * 3 + 2, neighbour._1._2 * 3 + 1, true)
              drawFloors += new Floor(neighbour._1._1 * 3 + 3, neighbour._1._2 * 3, true)
              drawFloors += new Floor(neighbour._1._1 * 3 + 3, neighbour._1._2 * 3 + 1, true)
              drawFloors += new Floor(neighbour._1._1 * 3 + 4, neighbour._1._2 * 3, true)
              drawFloors += new Floor(neighbour._1._1 * 3 + 4, neighbour._1._2 * 3 + 1, true)
              drawFloors += new Floor(neighbour._1._1 * 3 + 5, neighbour._1._2 * 3, true)
              drawFloors += new Floor(neighbour._1._1 * 3 + 5, neighbour._1._2 * 3 + 1, true)

              this.bridgeLocations += ((neighbour._1._1 * 3 + 2, neighbour._1._2 * 3, "W"))
              this.bridgeLocations += ((neighbour._1._1 * 3 + 2, neighbour._1._2 * 3 + 1, "W"))
              this.bridgeLocations += ((neighbour._1._1 * 3 + 3, neighbour._1._2 * 3, "W"))
              this.bridgeLocations += ((neighbour._1._1 * 3 + 3, neighbour._1._2 * 3 + 1, "W"))
              this.bridgeLocations += ((neighbour._1._1 * 3 + 4, neighbour._1._2 * 3, "W"))
              this.bridgeLocations += ((neighbour._1._1 * 3 + 4, neighbour._1._2 * 3 + 1, "W"))
              this.bridgeLocations += ((neighbour._1._1 * 3 + 5, neighbour._1._2 * 3, "W"))
              this.bridgeLocations += ((neighbour._1._1 * 3 + 5, neighbour._1._2 * 3 + 1, "W"))
            }
            case "E" => {
              drawFloors += new Floor(neighbour._1._1 * 3 - 1, neighbour._1._2 * 3, true)
              drawFloors += new Floor(neighbour._1._1 * 3 - 1, neighbour._1._2 * 3 + 1, true)
              drawFloors += new Floor(neighbour._1._1 * 3 - 2, neighbour._1._2 * 3, true)
              drawFloors += new Floor(neighbour._1._1 * 3 - 2, neighbour._1._2 * 3 + 1, true)
              drawFloors += new Floor(neighbour._1._1 * 3 - 3, neighbour._1._2 * 3, true)
              drawFloors += new Floor(neighbour._1._1 * 3 - 3, neighbour._1._2 * 3 + 1, true)
              drawFloors += new Floor(neighbour._1._1 * 3 - 4, neighbour._1._2 * 3, true)
              drawFloors += new Floor(neighbour._1._1 * 3 - 4, neighbour._1._2 * 3 + 1, true)
              this.bridgeLocations += ((neighbour._1._1 * 3 - 1, neighbour._1._2 * 3, "E"))
              this.bridgeLocations += ((neighbour._1._1 * 3 - 1, neighbour._1._2 * 3 + 1, "E"))
              this.bridgeLocations += ((neighbour._1._1 * 3 - 2, neighbour._1._2 * 3, "E"))
              this.bridgeLocations += ((neighbour._1._1 * 3 - 2, neighbour._1._2 * 3 + 1, "E"))
              this.bridgeLocations += ((neighbour._1._1 * 3 - 3, neighbour._1._2 * 3, "E"))
              this.bridgeLocations += ((neighbour._1._1 * 3 - 3, neighbour._1._2 * 3 + 1, "E"))
              this.bridgeLocations += ((neighbour._1._1 * 3 - 4, neighbour._1._2 * 3, "E"))
              this.bridgeLocations += ((neighbour._1._1 * 3 - 4, neighbour._1._2 * 3 + 1, "E"))
            }
          }
        }else{
          floorCells -= next
        }
    }
      drawFloors
  }

}
