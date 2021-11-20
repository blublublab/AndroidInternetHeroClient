package com.yainnixdev.internethero.creatures

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Array
import com.yainnixdev.internethero.creatures.appearence.HeroLook


data class Hero(var level : Int,
                var money: Int,
                var heroName: String,
                var heroLook : HeroLook
) : Creature() {


    override fun getCurrentAnimations(): List<Animation<TextureRegion>> = heroLook.getAnimations()[direction.ordinal]
}
