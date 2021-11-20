package com.yainnixdev.internethero

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.freetype.FreeType

import com.badlogic.gdx.math.GridPoint2
import com.yainnixdev.internethero.utils.getGdxFont
import org.hildan.krossbow.stomp.StompSession
import java.awt.Font

data class MainGdxClass(private var additionalData: Map<String, Any>,
                        var batch: SpriteBatch? = null,
                        var pixelFont : BitmapFont? = null,
                        var nickNameFont : BitmapFont? = null,
                        var chatFont : BitmapFont? = null): Game() {

    override fun create() {
        batch = SpriteBatch()

        pixelFont = this.getGdxFont("font/pixel_font.ttf")
        pixelFont?.data?.setScale(5F, 5F)
        pixelFont?.region?.texture?.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)




        nickNameFont = this.getGdxFont("font/arial_black.ttf")
        nickNameFont?.region?.texture?.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        nickNameFont?.data?.setScale(0.5F, 0.5F)


        chatFont = this.getGdxFont("font/arial_black.ttf")
        chatFont?.color = Color.BLACK
        chatFont?.data?.setScale(3F, 3F)

        setScreen(TownScreen(this, additionalData))
    }
    override fun dispose() {
        super.dispose()
        pixelFont?.dispose()
        batch?.dispose()
    }

}