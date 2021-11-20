package com.yainnixdev.internethero.creatures

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion

class Enemy : Creature(){
    override fun getCurrentAnimations(): List<Animation<TextureRegion>> {
        return listOf()
    }

}