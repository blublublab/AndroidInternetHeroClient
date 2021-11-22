package com.yainnixdev.internethero.creatures.appearence

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.yainnixdev.internethero.SpriteHandler.getAnimation
import com.yainnixdev.internethero.SpriteHandler.getHeroAnimation
import com.yainnixdev.internethero.creatures.appearence.parts.*
import com.yainnixdev.internethero.creatures.appearence.parts.cloth.Cloth
import com.yainnixdev.internethero.utils.ANIMATION_SPEED
import com.yainnixdev.internethero.utils.HERO_SIZE
import com.yainnixdev.internethero.utils.toGdxArray

/**
 *
 *   Model of work consisting on creating array of animation objects , that later drawn in
 *   for each cycle in right order for each hero(Currently only in TownScreen.class).
 *
 *  Standard build for hero appearance is:
 *
 *    (What's later in list come on front view, what's first come on back)
 *
 *      Action -> (Necessarily part of animation!)
 *      Shirt -> (Can't be drawn with Suit)
 *      Pants -> (Can't be drawn with Suit)
 *      Suit -> (Can't be drawn with Shirt and/or Pants)
 *      Hair ->
 *      Headdress ->
 *      Eyes ->
 *      Blush ->
 *      Lips ->
 *      Glasses->
 *      Beard ->
 *      Shoes ->
 *
*/

class HeroLook(
    private val heroModel: Int = 0,
    private var heroAction: HeroAction = HeroAction.WALK,
    private val hair: Hair = Hair(HairType.BRAIDS, HairColor.BLACK),
    private val face: Face = Face(EyeColor.BLUE, null, null),
    private val accessory : Accessory = Accessory(null, null, null),
    private val cloth: Cloth = Cloth(null, null, null)
) {


    private val splitSize = HERO_SIZE.toInt()
    fun getAnimations(): List<List<Animation<TextureRegion>>> {
        val textures  = mutableListOf<TextureAtlas.AtlasRegion>()
        //All animations drawn in right order written in docs

        textures.add(getHeroAnimation(heroModel, heroAction))

        getAnimation(Pair(cloth.shirt?.shirtType?.name ?: "",
            cloth.shirt?.color?.ordinal ), heroAction)?.let { textures.add(it) }
        getAnimation(Pair(cloth.pants?.pantsType?.name ?: "",
            cloth.pants?.color?.ordinal ), heroAction)?.let { textures.add(it) }
        //getAnimation(Pair(cloth.suit!!.name, 0), heroAction)?.let { textures.add(it) }


        getAnimation(Pair(hair.type.name ,hair.color.ordinal), heroAction)?.let { textures.add(it) }

        //getAnimation(Pair(accessory.headdress?.name ?: "", -1), heroAction)?.let { textures.add(it) }

        getAnimation(Pair("eyes" ,face.color.ordinal), heroAction)?.let { textures.add(it) }
        getAnimation(Pair("blush", face.blush) , heroAction)?.let { textures.add(it) }
        getAnimation(Pair("lipstick", face.lipstick), heroAction)?.let { textures.add(it) }



        getAnimation(Pair("glasses", accessory.glasses?.ordinal), heroAction)?.let {textures.add(it)}
        getAnimation(Pair("beard", accessory.beard?.ordinal), heroAction)?.let { textures.add(it) }

        // no shoes in folder wtf?
       //  getAnimation(Pair("shoes", cloth.shoes?.ordinal), heroAction)?.let { textures.add(it) }


        val textureGdxArray = textures.map { texture ->  texture
            .split(splitSize, splitSize)
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



