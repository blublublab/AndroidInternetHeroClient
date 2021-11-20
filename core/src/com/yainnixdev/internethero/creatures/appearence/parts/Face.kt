package com.yainnixdev.internethero.creatures.appearence.parts
// Lipstick and Blush are sorted from light to dark.

enum class EyeColor(){
    BLACK, BLUE, BLUE_LIGHT, BROWN, BROWN_DARK, BROWN_LIGHT,
    GREEN, GREEN_DARK, GREEN_LIGHT, GREY, GREY_LIGHT, PINK,
    PINK_LIGHT, RED
}

class Face(val color: EyeColor, val lipstick: Int? = null, val blush: Int? = null)
