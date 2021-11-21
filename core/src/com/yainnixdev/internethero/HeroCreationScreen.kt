package com.yainnixdev.internethero

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL30

class HeroCreationScreen(
    private val mainScreen: MainGdxClass,
    private val additionalData: Map<String, Any>? = null
): Screen {
    override fun show() {
        TODO("Not yet implemented")
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0F, 0F, 0F, 1F);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT)

    }

    override fun resize(width: Int, height: Int) {
        TODO("Not yet implemented")
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun resume() {
        TODO("Not yet implemented")
    }

    override fun hide() {
        TODO("Not yet implemented")
    }

    override fun dispose() {
        TODO("Not yet implemented")
    }
}