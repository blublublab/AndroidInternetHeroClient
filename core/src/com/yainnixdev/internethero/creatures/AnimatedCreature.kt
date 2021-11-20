package com.yainnixdev.internethero.creatures

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion

interface AnimatedCreature {
    fun  getCurrentAnimations() :  List<Animation<TextureRegion>>

}