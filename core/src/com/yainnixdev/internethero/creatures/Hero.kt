package com.yainnixdev.internethero.creatures

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.yainnixdev.internethero.creatures.appearence.HeroLook


data class Hero(var level : Int,
                var money: Int,
                var heroName: String,
                var heroLook : HeroLook,
) : Creature(){

    lateinit var animation : List<Animation<TextureRegion>>
    override fun appearanceChanged(tempLook: HeroLook?) {
        if (tempLook != null) heroLook = tempLook
        animation = heroLook.getAnimations()[direction.ordinal]
    }
}
