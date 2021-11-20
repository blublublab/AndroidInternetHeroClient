package com.yainnixdev.internethero.creatures.appearence.parts
enum class HairType {
    BRAIDS,
    BUZZCUT,
    CURLY,
    EMO,
    EXTRALONG,
    FRENCHCURL,
    GENTLEMAN,
    MIDIWAVE,
    SPACEBUNS,
    WAVY
}
enum class HairColor {
    BLACK,
    BLONDE,
    BROWN,
    BROWN_LIGHT,
    COPPER,
    EMERALD,
    GREEN,
    GREY,
    LILAC,
    NAVY,
    PINK,
    PURPLE,
    RED,
    TURQUOISE
}
class Hair(val type: HairType, val color: HairColor)