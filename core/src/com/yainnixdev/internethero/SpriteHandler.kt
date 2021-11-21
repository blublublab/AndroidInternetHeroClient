package com.yainnixdev.internethero

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.yainnixdev.internethero.creatures.appearence.parts.Face
import com.yainnixdev.internethero.creatures.appearence.parts.Hair
import com.yainnixdev.internethero.creatures.appearence.parts.HeroAction

/**
 * All textures are not null safe (!!)
 * Any lack of sprite should not load game
 */

object SpriteHandler {

    private  val heroAtlas : TextureAtlas = TextureAtlas(Gdx.files.internal("tile/hero_animation/game.pack"))


    /** For all Plants :
     [0] - Plant
     [1] - Plant package
     [2-x] - Plant grow stages
     */
 //   val carrot = heroAtlas.findRegions("carrot") !!


    fun getHeroAnimation(id : Int, operation: HeroAction) =
        heroAtlas.findRegions(operation.name.lowercase())[id]



    fun getAnimation(pair: Pair<String, Int?>, heroAction: HeroAction) =
        pair.second?.let {
            val path = "${heroAction.name.lowercase()} ${pair.first.replace("_", " ").lowercase()}"
            println(path)
            heroAtlas.findRegions(path)?.get(it)
        }



}