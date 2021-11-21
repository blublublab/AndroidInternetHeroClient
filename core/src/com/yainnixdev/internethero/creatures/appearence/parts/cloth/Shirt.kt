package com.yainnixdev.internethero.creatures.appearence.parts.cloth

enum class ClothShirt(){
    BASIC,
    SUIT,
    SPORTY,
    SAILOR,
    SAILOR_BOW,
    OVERALL,
    FLORAL
}

class Shirt(val shirtType : ClothShirt, val color : ClothColor)