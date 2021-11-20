package com.yainnixdev.internethero.entities

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.Array
import com.yainnixdev.internethero.utils.GameRectangle
import com.yainnixdev.internethero.utils.Point
import com.yainnixdev.internethero.utils.getCenter


open class GameObject(
    var clickable: Boolean,
    var colliding: Boolean,
    open var texture: TextureAtlas.AtlasRegion,
    val rectangle: GameRectangle,
    val point: Point = rectangle.getCenter()
)