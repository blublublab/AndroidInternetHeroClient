package com.yainnixdev.internethero.utils

data class Point(
    var x : Float = 0F,
    var y : Float = 0F
) {
    operator fun plusAssign(move: Point) {
        this.x += move.x
        this.y += move.y
    }
}