package Game.Main
import scala.collection.mutable.Buffer
import scala.util.Random

class Labyrinth {
  def createLabyrinth(grid: Grid) = {
    val labyrinthSize = grid.gridSize
    val visitedCells = Buffer[(Int, Int)]()
    val floorCells = Buffer[(Int, Int)]()
    val firstCell = grid.locations(Random.nextInt(labyrinthSize))
    floorCells += firstCell
    visitedCells += firstCell

    while (floorCells.nonEmpty) {
      val next = floorCells.reverse.head
      val neighbours = grid.neighbours(next._1, next._2).filter(n => !visitedCells.contains(n))
        if (neighbours.nonEmpty) {
          val neighbour = neighbours(Random.nextInt(neighbours.size))
          floorCells += neighbour
          visitedCells += neighbour
        }else{
          floorCells -= next
        }
      }
      println(visitedCells)
      visitedCells
  }

}
