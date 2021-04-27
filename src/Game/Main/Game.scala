package Game.Main



class Game {
    var grid = new Grid(10, 10)
    var gridLocations = grid.locations
    var labyrinth = new Labyrinth
    var floors = labyrinth.createLabyrinth(grid)
    var bridges = this.labyrinth.bridgeLocations
    var labyrinthLocations = new Grid((grid.width * 3 - 1), (grid.height * 3 - 1))
    var walls = new Grid((grid.width * 3 - 1), (grid.height * 3 - 1)).locations.filter(n => !floors.map(_.location).contains(n)).map(n => new Wall(n._1, n._2, false))
    var player = new Character(15, 15)
    def newGrid(width: Int, height: Int) {
        grid = new Grid(width, height)
        gridLocations = grid.locations
    }
    def newLabyrinth() {
        labyrinth = new Labyrinth
        floors = labyrinth.createLabyrinth(grid)
        bridges = labyrinth.bridgeLocations
        labyrinthLocations = new Grid((grid.width * 3 - 1), (grid.height * 3 - 1))
        walls = new Grid((grid.width * 3 - 1), (grid.height * 3 - 1)).locations.filter(n => !floors.map(_.location).contains(n)).map(n => new Wall(n._1, n._2, false))
        player = new Character(grid.width + (grid.width / 2), grid.height + (grid.height / 2))
    }
}
