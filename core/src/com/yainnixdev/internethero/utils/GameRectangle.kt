package com.yainnixdev.internethero.utils

import com.badlogic.gdx.math.Rectangle

/**
 * Utility class that replacing standard Rectangle class, because in a game with a pixel fixed size
 * all widths and heights of rectangles would be CELL_SIZExCELL_SIZE
 */
class GameRectangle(x: Float, y: Float, width: Float = CELL_SIZE, height: Float = CELL_SIZE) :
    Rectangle(x, y, CELL_SIZE, CELL_SIZE) {

}