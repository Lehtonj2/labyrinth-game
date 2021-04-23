package Game.Main



class Game {
    val grid = new Grid(10, 10)
    val gridLocations = grid.locations
    val labyrinth = new Labyrinth
    val floors = labyrinth.createLabyrinth(grid)
    val walls = new Grid((grid.width * 3 - 1), (grid.height * 3 - 1)).locations.filter(n => !floors.map(_.location).contains(n)).map(n => new Wall(n._1, n._2, false))
    var player = new Character(0, 0)


}
