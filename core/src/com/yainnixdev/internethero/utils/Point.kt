package com.yainnixdev.internethero.utils
/**
 * Utility class was made to replace Point java.awt (not compatible with  Android)
 * and GridPoint2 libgdx (all values whose are in int)
 */
data class Point(
    var x : Float = 0F,
    var y : Float = 0F
) {
    operator fun plusAssign(move: Point) {
        this.x += move.x
        this.y += move.y
    }
}