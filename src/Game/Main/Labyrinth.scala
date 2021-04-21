package Game.Main
import scala.collection.mutable.Buffer
import scala.util.Random

class Labyrinth {
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
      val neighbours = grid.neighbours(next._1, next._2).filter(n => !visitedCells.contains(n._1))
        if (neighbours.nonEmpty) {
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
        }else{
          floorCells -= next
        }
      }
      println(visitedCells)
      println(drawFloors.map(_.location))
      drawFloors
  }

}
