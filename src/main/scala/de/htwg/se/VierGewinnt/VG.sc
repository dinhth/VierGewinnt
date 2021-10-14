case class Player(name: String)
val player = Player("Thu Ha")
player.name
val playerList = List(player)
val player2 = Player("Orkan")
playerList :+ player2
playerList

val matrix = Vector(
  Vector("a1", "a2", "a3", "a4", "a5", "a6" ),
  Vector("b1", "b2", "b3", "b4", "b5", "b6"),
  Vector("c1", "c2", "c3", "c4", "c5", "c6"),
  Vector("d1", "d2", "d3", "d4", "d5", "d6"),
  Vector("e1", "e2", "e3", "e4", "e5", "e6"),
  Vector("f1", "f2", "f3", "f4", "f5", "f6"),
  Vector("g1", "g2", "g3", "g4", "g5", "g6"))

//(x)(y)  x=Buchstabe y=Zahl a1=unten links
matrix(1)(5)
matrix(3)(4)

enum Chip:
  case Yellow, Red, Empty

Chip.Yellow
Chip.Red
Chip.Empty


val matrix2 = Vector(
  Vector(Chip, "a2", "a3", "a4", "a5", "a6" ),
  Vector("b1", "b2", "b3", "b4", "b5", "b6"),
  Vector("c1", "c2", "c3", "c4", "c5", "c6"),
  Vector("d1", "d2", "d3", "d4", "d5", "d6"),
  Vector("e1", "e2", "e3", "e4", "e5", "e6"),
  Vector("f1", "f2", "f3", "f4", "f5", "f6"),
  Vector("g1", "g2", "g3", "g4", "g5", "g6"))

case class Spielbrett(rows: Vector[Vector[Chip]]):
  def cell(row: Int, col: Int) = rows(row)(col)
  def fill(filling: Chip): Spielbrett = copy(Vector.tabulate(3, 3) { (row, col) => filling })
  def replaceCell(row: Int, col: Int, cell: Chip) = copy(rows.updated(row, rows(row).updated(col, cell)))

val m = Spielbrett(Vector(Vector(Chip.Yellow, Chip.Yellow, Chip.Yellow, Chip.Red, Chip.Red, Chip.Red), Vector(), Vector(), Vector(), Vector(), Vector(), Vector()))
m.cell(0, 1)
val m2 = m.fill(Chip.Empty)
m2.replaceCell(1, 1, Chip.Red)