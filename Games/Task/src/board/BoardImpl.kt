package board

import board.Direction.*

fun createSquareBoard(width: Int): SquareBoard = MyBoard(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = MyGameBoard<T>(width)

