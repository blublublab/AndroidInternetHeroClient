package com.yainnixdev.internethero

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yainnixdev.internethero.creatures.Creature
import com.yainnixdev.internethero.creatures.Hero
import com.yainnixdev.internethero.creatures.appearence.HeroLook
import com.yainnixdev.internethero.creatures.appearence.parts.*
import com.yainnixdev.internethero.creatures.appearence.parts.cloth.*
import com.yainnixdev.internethero.entities.DtoClasses
import com.yainnixdev.internethero.entities.GameObject
import com.yainnixdev.internethero.utils.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.StompSession
import org.hildan.krossbow.stomp.sendText
import org.hildan.krossbow.stomp.subscribeText
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient

class TownScreen(
    private val mainScreen: MainGdxClass,
    private val additionalData: Map<String, Any>? = null,
) : Screen {
    private var stateTime: Float = 0F

    private var userHero : Hero
    private val serverHeroes = mutableListOf<Hero>()
    private val gameObjects = mutableListOf<GameObject>()

    private var gameCamera = OrthographicCamera()
    private var screenSize : Point

    private val uiStage : Stage
    private val uiCamera = OrthographicCamera()

    private val map : TiledMap
    private val mapRenderer : OrthogonalTiledMapRenderer
    private val shapeRenderer : ShapeRenderer = ShapeRenderer()

    private val token = additionalData?.get("token").toString()
    private lateinit var stompSession : StompSession
    private val gson = GsonBuilder().setLenient().create()
    init {
        setStompClient()
        screenSize = Point(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat() )
        gameCamera.setToOrtho(false , screenSize.x, screenSize.y)
        shapeRenderer.color = Color.RED

/*
        val plant = Plant(32, 32, Plant.PlantType.CARROT,rectangle =
        GameRectangle(1600F, 1600F))
        plant.growStage = Plant.PlantType.CARROT.maxGrowStage
        plant.texture = plant.growTexture
        plant.colliding = true

        gameObjects.add(plant)*/

        map = TmxMapLoader().load("map/map_town.tmx")
        mapRenderer = OrthogonalTiledMapRenderer(map , 1F)
        uiStage = Stage(FitViewport(screenSize.x, screenSize.y, uiCamera))
        userHero = Gson().fromJson(additionalData?.get("user_hero").toString(), Hero::class.java)
        userHero.direction = Creature.CreatureDirection.DOWN
        addHero(userHero)

        initInput()
    }

    override fun render(delta: Float) {
        stateTime +=  Gdx.graphics.deltaTime
        Gdx.gl.glClearColor(0F, 0F, 0F, 1F);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT)

        mapRenderer.render()
        gameCamera.position.set(userHero.point.x + HERO_SIZE/2,
            userHero.point.y + HERO_SIZE/2, 0F)
        gameCamera.update()
        mapRenderer.setView(gameCamera)
        mainScreen.batch?.apply {
            projectionMatrix = gameCamera.combined;
            begin()
            serverHeroes.forEach { hero ->
                //Draw animations
                for(animation in hero.animation){
                    draw(animation.getKeyFrame(
                        // If hero is not moving set it texture to 1 frame = Animation_speed/2
                        // Didn't added new variable because of  possible performance issues
                        if(hero.moveJob == null) ANIMATION_SPEED/2 else stateTime,
                        hero.moveJob != null), hero.gameRectangle!!)

                }
                /*    with(hero.point) {
            //Draw nicknames
            mainScreen.nickNameFont?.draw(this@apply, hero.heroName,
                x + HERO_SIZE / 6,
                y + HERO_SIZE + (mainScreen.nickNameFont?.lineHeight?.div(2) ?: 0F))

        }*/
            }
            gameObjects.forEach { draw(it.texture, it.rectangle) }
            end()
        }
        drawHeroRect()
        drawUI()
    }
    private fun addHero(inputHero:  Hero): Hero {
        with(inputHero){
            gameRectangle = GameRectangle(point.x, point.y)
            inputHero.heroLook = HeroLook(
                heroModel = 0,
                HeroAction.WALK,
                Hair(HairType.GENTLEMAN, HairColor.BROWN),
                accessory =  Accessory(
                    glasses = ClothColor.PINK),
                cloth = Cloth(shirt = Shirt(shirtType = ClothShirt.BASIC, ClothColor.PINK), shoes = ClothColor.PINK, pants = Pants(ClothPants.PANTS, ClothColor.BLUE)))
            inputHero.appearanceChanged()
            serverHeroes.add(this)
            return this
        }
    }
    private fun drawHeroRect(){
        shapeRenderer.projectionMatrix = gameCamera.combined
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        gameObjects.forEach {
            shapeRenderer.rect(it.rectangle)
        }
        serverHeroes.forEach {
            it.gameRectangle?.let { it1 -> shapeRenderer.rect(it1) }
        }
        shapeRenderer.end()
    }

    private fun drawUI(){
        val uiBatch = uiStage.batch
        uiBatch.begin()


/*
       uiBatch.draw(chatTexture, BORDER_PIXELS, BORDER_PIXELS, screenSize.x/3, screenSize.y/3)
        chatMessages.takeLast(MESSAGES_TO_DISPLAY).forEachIndexed { index, message ->
            val lineHeight  = mainScreen.chatFont?.lineHeight ?: 16F
            val displayMessage = "${message.sendTime}| ${message.author} : ${message.text}"
            mainScreen.chatFont?.color = Color(message.messageColor)
            mainScreen.chatFont?.draw(uiBatch, displayMessage, 0F, index*lineHeight)
        }
      uiBatch.draw(chatBorderTexture, 0F, 0F, screenSize.x/3, screenSize.y/3)*/
        mainScreen.pixelFont?.draw(uiBatch, "FPS: ${Gdx.graphics.framesPerSecond}", screenSize.x-screenSize.x/7, screenSize.y);
        mainScreen.pixelFont?.draw(uiBatch, "X:${userHero.point.x} Y:${userHero.point.y}", 0F, screenSize.y )

        uiBatch.end()
    }





    private fun heroColliding() = gameObjects
        .filter { it.colliding }
        .any { userHero.gameRectangle?.overlaps(it.rectangle) ?: false }

    private suspend fun sendCoordinates(heroDto: DtoClasses.HeroDto)  =
        stompSession.sendText("/app/send_position", gson.toJson(heroDto , DtoClasses.HeroDto::class.java))


    private fun initInput(){
        var heroDto: DtoClasses.HeroDto
        Gdx.input.inputProcessor = object : InputProcessor {
            override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
                userHero.stopMoving()
                val moveIntention = Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0F)
                gameCamera.unproject(moveIntention)

                val clickedOnHero = (userHero.gameRectangle)?.contains(moveIntention.x, moveIntention.y) ?: false
                if (!clickedOnHero) {
                    var sendIntention: Point?
                    sendIntention = Point(moveIntention.x, moveIntention.y)
                    if (heroColliding()) {
                        userHero.stopMoving()
                        sendIntention = null
                    } else userHero.startMoving(Point(moveIntention.x, moveIntention.y))

                    heroDto = DtoClasses.HeroDto(userHero.heroName,
                        userHero.point, sendIntention, userHero.direction,heroLook = userHero.heroLook)

                    gameScope.launch { sendCoordinates(heroDto) }
                }
                return true
            }
            override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
                userHero.stopMoving()
                heroDto = DtoClasses.HeroDto(userHero.heroName, userHero.point, null, userHero.direction, heroLook = userHero.heroLook)
                gameScope.launch { sendCoordinates(heroDto) }
                return true
            }

            override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
                return true
            }


            override fun keyDown(keycode: Int) = false
            override fun keyUp(keycode: Int) = false
            override fun mouseMoved(screenX: Int, screenY: Int) = false
            override fun keyTyped(character: Char) = false
            override fun scrolled(amountX: Float, amountY: Float) = false
        }
    }


    private fun updateHeroList(heroDto: DtoClasses.HeroDto) {
        if (heroDto.heroName != userHero.heroName) {
            val heroFromArray = serverHeroes.find { heroDto.heroName == it.heroName }
            //Check if Hero on server already exist and update else create it
            val doneHero =  if (heroFromArray != null) {
                heroFromArray.point = heroDto.point
                heroFromArray.money = heroDto.money
                heroFromArray.level = heroDto.level
                heroFromArray.direction = heroDto.direction
                heroFromArray
            } else {
                val freshWebHero = Hero(heroDto.level, heroDto.money, heroDto.heroName, heroDto.heroLook)
                freshWebHero.point = heroDto.point
                freshWebHero.direction = heroDto.direction
                addHero(freshWebHero)
            }

            if(heroDto.moveIntention != null){
                doneHero.startMoving(heroDto.moveIntention ?: Point())
            } else doneHero.stopMoving()
        }
    }

    private fun updateCells(){

    }


    private fun writeCell(point: Point, gameObject: GameObject){
    //TODO :  Send Retrofit call , that returns or empty string , or cause of can't writing cell

    }


    private fun setStompClient() {
            val preparedUrl = additionalData?.get("stomp_url").toString()
            val wsClient = OkHttpWebSocketClient(gameOkHttpClient(token))
             gameScope.launch{
                stompSession = StompClient(wsClient).connect(preparedUrl,
                    customStompConnectHeaders = mapOf(Pair("Authorization", token)))

                stompSession.subscribeText("/topic/get_position")
                    .collect {
                        println("topic : $it")
                        updateHeroList(gson.fromJson(it, DtoClasses.HeroDto::class.java)) }
                stompSession.subscribeText("/topic/get_message")
                    .collect {
                        println("topic: $it")
                        //printMessage(gson.fromJson(it, ChatMessage::class.java))
                    }
                 stompSession.subscribeText("/topic/get_cells")
                     .collect {
                         println()
                     }
             }
    }


    override fun resize(width: Int, height: Int) {
        val camWidth = CELL_SIZE * 10
        val camHeight = camWidth * (height.toFloat() / width.toFloat())

        gameCamera.viewportWidth = camWidth
        gameCamera.viewportHeight = camHeight
        gameCamera.update()


        uiCamera.viewportWidth = camWidth
        uiCamera.viewportHeight = camHeight
        uiCamera.update()
    }

    override fun pause() {
       userHero.stopMoving()
    }


    override fun dispose() {
        map.dispose()
        mapRenderer.dispose()
        uiStage.dispose()
        shapeRenderer.dispose()
      //  mainScreen.dispose()
    }

    override fun resume() = Unit
    override fun hide() = Unit
    override fun show() = Unit

}



