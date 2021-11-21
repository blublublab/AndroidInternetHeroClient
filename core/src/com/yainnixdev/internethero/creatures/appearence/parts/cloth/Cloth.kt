package com.yainnixdev.internethero.creatures.appearence.parts.cloth

enum class ClothColor(){
    BLACK,
    BLUE,
    BLUE_LIGHT,
    BROWN,
    GREEN,
    GREEN_LIGHT,
    PINK,
    PURPLE,
    RED,
    WHITE_GREY
}


enum class ClothSuit(){
    SPOOKY,
    PUMPKIN,
    PUMPKIN_PURPLE,
    DRESS_WITCH,
    CLOWN,
    CLOWN_BLUE
}


class Cloth(
    val shirt: Shirt? = null,
    val pants: Pants? = null,
    val shoes : ClothColor? = null,
){

    var suit: ClothSuit? = null

    constructor(suit: ClothSuit?, shoes: ClothColor?) : this(null, null, shoes){
        this.suit = suit
    }
    constructor(suit: ClothSuit?) : this(suit, null)

}