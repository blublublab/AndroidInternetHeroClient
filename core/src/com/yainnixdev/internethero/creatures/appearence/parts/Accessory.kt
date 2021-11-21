package com.yainnixdev.internethero.creatures.appearence.parts

import com.yainnixdev.internethero.creatures.appearence.parts.cloth.ClothColor

enum class HeaddressType(){
    HAT_COWBOY,
    HAT_LUCKY,
    HAT_PUMPKIN,
    HAT_PUMPKIN_PURPLE,
    HAT_WITCH,
    MASK_CLOWN,
    MASK_CLOWN_RED,
    MASK_SPOOKY
}

class Accessory(val glasses: ClothColor? = null, val beard: HairColor? = null, val headdress: HeaddressType? = null)