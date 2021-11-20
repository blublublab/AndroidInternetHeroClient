package com.yainnixdev.internethero.utils

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.GridPoint2
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.Array
import com.google.gson.Gson
import com.yainnixdev.internethero.chat.Chat
import com.yainnixdev.internethero.chat.ChatMessage
import com.yainnixdev.internethero.creatures.Creature
import com.yainnixdev.internethero.creatures.Creature.CreatureDirection

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import okhttp3.OkHttpClient
import org.hildan.krossbow.stomp.StompSession
import org.hildan.krossbow.stomp.subscribeText
import java.lang.Exception
import java.util.concurrent.TimeUnit
import kotlin.math.abs


const val HERO_ANIMATION_DURATION = 0.25F
const val HERO_SIZE = 32F
const val CELL_SIZE = 32F
const val ANIMATION_SPEED = 0.125F


//Screen Direction Utils
fun getDirectionVector(clickPoint: Point, heroPoint: Point): Point {
    val vectorPoint = Point(1F, 1F)
    if (clickPoint.x < heroPoint.x) vectorPoint.x *= -1
    if (clickPoint.y < heroPoint.y) vectorPoint.y *= -1
    when (getDirection(clickPoint, heroPoint)) {
        CreatureDirection.LEFT, CreatureDirection.RIGHT -> vectorPoint.y = 0F
        CreatureDirection.UP, CreatureDirection.DOWN -> vectorPoint.x = 0F
    }
    return vectorPoint
}
fun getDirection(clickPoint:  Point, heroPoint: Point): CreatureDirection {
    val xDistance: Int = abs(clickPoint.x.toInt() - heroPoint.x.toInt())
    val yDistance: Int = abs(clickPoint.y.toInt() - heroPoint.y.toInt())
    return if (xDistance > yDistance) {
        if (clickPoint.x < heroPoint.x) CreatureDirection.LEFT else CreatureDirection.RIGHT
    } else {
        if (clickPoint.y > heroPoint.y) CreatureDirection.UP else CreatureDirection.DOWN
    }
}


fun Game.getGdxFont(filePath : String,
                    additional : FreeTypeFontParameter = FreeTypeFontParameter().apply {
                        size = 16
                        borderStraight = true
                    }): BitmapFont? {

    val file = Gdx.files.internal(filePath)
    val fontGenerator = FreeTypeFontGenerator(file)
    val bitmapFont = fontGenerator.generateFont(additional)
    fontGenerator.dispose()
    return bitmapFont
}

fun <T> Collection<T>.toGdxArray() : Array<T> {
    val gdxArray = Array<T>(this.size)
    val listOfT =  this.toList()
    for(i in this.indices) {
        gdxArray.add(listOfT[i])
    }
    return gdxArray
}


suspend fun <T> StompSession.subscribeJson(destination: String, anyClass: Class<T>): Flow<T> =
    subscribeText(destination).transform { (Gson().fromJson<T>(it, anyClass::class.java)) }


//Coroutine Utils
val gameScope = CoroutineScope(Job()  + Dispatchers.IO)

fun CoroutineScope.updateAtFixedRateAsync(funcToRepeat: () -> Unit,
                                          updateRate: Long,
                                          doOnFinish: () -> Unit = {})= async {
    while(isActive) {
        funcToRepeat()
        delay(updateRate)
    }
    doOnFinish()
    return@async true

}

fun BitmapFont?.drawMessage(batch: Batch?, message: ChatMessage,  x: Float, y: Float) {

}

fun Batch.draw(texture : TextureRegion, gameRectangle: GameRectangle){
    draw(texture, gameRectangle.x, gameRectangle.y, gameRectangle.width, gameRectangle.height)
}
//Rectangle extensions
operator fun Rectangle?.plus(point: Point): Rectangle? {
    this?.x = this?.x?.plus(point.x)
    this?.y = this?.y?.plus(point.y)
    return this
}

fun Rectangle.getCenter() : Point {
    val centerX = this.x+ width/2
    val centerY = this.y+ height/2

    return Point(centerX, centerY)
}

fun ShapeRenderer.rect(gameRectangle: GameRectangle) =
    rect(gameRectangle.x, gameRectangle.y, gameRectangle.width, gameRectangle.height)
//Rectangle extensions


private var okHttpClient : OkHttpClient? = null
fun gameOkHttpClient(token : String) : OkHttpClient {
    if (okHttpClient != null) return okHttpClient as OkHttpClient
    okHttpClient = OkHttpClient.Builder().addInterceptor {
        val request = it.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        it.proceed(request)
    }
        .retryOnConnectionFailure(true)
       // .pingInterval(15, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    return okHttpClient as OkHttpClient
}