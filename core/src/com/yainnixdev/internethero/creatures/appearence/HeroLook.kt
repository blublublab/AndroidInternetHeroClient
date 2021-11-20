package com.yainnixdev.internethero.creatures.appearence

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.yainnixdev.internethero.SpriteHandler
import com.yainnixdev.internethero.creatures.appearence.parts.*
import com.yainnixdev.internethero.utils.ANIMATION_SPEED
import com.yainnixdev.internethero.utils.HERO_SIZE
import com.yainnixdev.internethero.utils.toGdxArray



class HeroLook(
    private val heroModel: Int = 0,
    private val heroAction: HeroAction = HeroAction.WALK,
    private val hair: Hair = Hair(HairType.BRAIDS, HairColor.BLACK),
    private val face: Face = Face(EyeColor.BLUE, null, null),
    private val accessory : Accessory = Accessory(null, null, null)
) {
    private val splitSize = HERO_SIZE.toInt()

    fun getAnimations(): List<List<Animation<TextureRegion>>> {

        val actionTexture = SpriteHandler.getHeroAnimation(heroModel, heroAction)
        val hairTexture = SpriteHandler.getAnimation(Pair(hair.type.name ,hair.color.ordinal), heroAction)

        val eyesTexture = SpriteHandler.getAnimation(Pair("eyes" ,face.color.ordinal), heroAction)
        val blushTexture = SpriteHandler.getAnimation(Pair("blush", face.blush) , heroAction)
        val lipsTexture = SpriteHandler.getAnimation(Pair("lipstick", face.lipstick), heroAction)

        val glassesTexture = SpriteHandler.getAnimation(Pair("glasses", accessory.glasses?.ordinal), heroAction)
        val beardTexture = SpriteHandler.getAnimation(Pair("beard", accessory.beard?.ordinal), heroAction)
        //val headdressTexture = SpriteHandler.getAnimation(Pair(accessory.headdress?.name ?: "", 0 ), heroAction)

        val textures = mutableListOf(actionTexture,
            hairTexture,
            eyesTexture,
            blushTexture,
            lipsTexture, glassesTexture, beardTexture)
        val textureGdxArray = textures.map { texture ->  texture
            ?.split(splitSize, splitSize)
            ?.map { it.toList().toGdxArray() }
            ?.toGdxArray()
        }

        val animations = mutableListOf<List<Animation<TextureRegion>>>()

        val numberOfRows = textureGdxArray[0]?.size ?: 4
        for (i in 0 until numberOfRows){
           val list = mutableListOf<Animation<TextureRegion>>()
            for(element in textureGdxArray){
                if(element != null) {
                    val animation = Animation(ANIMATION_SPEED, element.get(i))
                    list.add(animation)
                }
            }
            animations.add(list)
        }
        return animations
    }
}



