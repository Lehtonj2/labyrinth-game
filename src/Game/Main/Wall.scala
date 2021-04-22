package Game.Main

class Wall(x: Int, y: Int, underlapping: Boolean) extends Object {
  def location = (x, y)
  def underLap = underlapping
}
