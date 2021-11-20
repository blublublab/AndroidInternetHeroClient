package com.yainnixdev.internethero.creatures.appearence.parts

enum class HeroAction(val tilesWidth: Int, val tilesHeight: Int) {
    WALK(8, 4),
    CARRY(8, 4),

    AXE(5, 4),
    JUMP(5, 4),
    PICKAXE(5, 4),
    FISH(5, 4),
    HOE(5, 4),

    SWORD(4, 4),
    WATER(2, 4),

    HURT(1, 4),

    // Do it later
    DIE(2, 1),
    BLOCK(1, 1)
}