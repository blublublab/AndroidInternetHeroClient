package com.yainnixdev.internethero.creatures

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.GridPoint2
import com.badlogic.gdx.utils.Array
import com.yainnixdev.internethero.utils.*
import kotlinx.coroutines.Job


abstract class Creature(
    var health: Int = 0,
    var damage: Int = 0,
    var direction: CreatureDirection = CreatureDirection.DOWN,
    var gameRectangle: GameRectangle? = null,
    var point: Point = Point(),
    var collidingListener: CreatureCollidingListener? = null
    )  : CreatureCollidingListener , CreatureStateChangedListener{
    companion object {
       const val  CREATURE_SPEED = 2F
    }
    private var moveVector = Point(0F,  0F)
    var moveJob: Job? = null


    enum class CreatureDirection { DOWN,  UP, RIGHT, LEFT }

    private fun move(move: Point) {
        point += move
        gameRectangle?.x = gameRectangle?.x?.plus(move.x)
        gameRectangle?.y = gameRectangle?.y?.plus(move.y)
    }

    fun startMoving(moveIntention: Point) {

        val centralizedPoint = gameRectangle?.getCenter() ?: Point(0F, 0F)
        val pathDirection = getDirectionVector(moveIntention, centralizedPoint)

        direction = getDirection(moveIntention, centralizedPoint)
        moveVector = Point(pathDirection.x * CREATURE_SPEED,  pathDirection.y * CREATURE_SPEED)
        moveJob = gameScope.updateAtFixedRateAsync({ move(moveVector) }, 40)
        appearanceChanged()
    }
    fun stopMoving() {
        moveJob?.cancel()
        moveJob = null
    }
/*
    fun intersectPoint(clickPoint2: GridPoint2): Boolean =
        gameRectangle?.contains(clickPoint2.x.toFloat(), clickPoint2.y.toFloat()) ?: false*/

    override fun onCreatureCollided() = stopMoving()

}