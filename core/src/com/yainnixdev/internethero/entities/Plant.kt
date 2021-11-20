package com.yainnixdev.internethero.entities

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.Array
import com.yainnixdev.internethero.SpriteHandler
import com.yainnixdev.internethero.utils.GameRectangle
import com.yainnixdev.internethero.utils.Point



class Plant(
    val valueOfSell: Int = 0,
    val valueOfBuy: Int = 0,
    private val plantType: PlantType,
    texture: TextureAtlas.AtlasRegion = plantType.texture[2],
    rectangle: GameRectangle,
): GameObject(
    clickable = false,
    colliding = false,
    texture, rectangle
){
    enum class PlantType(
        val texture: Array<TextureAtlas.AtlasRegion>,
        val maxGrowStage : Int) {

       // CARROT(SpriteHandler.carrot, SpriteHandler.carrot.size-2)

    }
    var growStage = 0
        set(value) {
            if(value > plantType.maxGrowStage){
                clickable = true
                colliding = true
                field = plantType.maxGrowStage
            } else field = value
        }

    override var texture = plantType.texture[0]!!


    var growTexture = plantType.texture[growStage+2]!!
    val seedTexture = plantType.texture[1]!!



}
