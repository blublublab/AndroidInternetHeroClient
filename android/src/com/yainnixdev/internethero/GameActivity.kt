package com.yainnixdev.internethero

import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.yainnixdev.internethero.databinding.ActivityGameBinding
import com.yainnixdev.internethero.utils.getString
import com.yainnixdev.internethero.utils.sharedPrefs

class GameActivity : AndroidApplication(){
    private lateinit var binding : ActivityGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val config = AndroidApplicationConfiguration()
        config.useAccelerometer = false
        config.useCompass = false
        config.useGyroscope  = false

        val additionalData = mutableMapOf<String, Any>()
       /* additionalData["screenSizeX"] = screenSize.x
        additionalData["screenSizeY"] = screenSize.y*/
        additionalData["stomp_url"] = getString(R.string.server_url_game)
        additionalData["token"] = sharedPrefs.getString("token")
        additionalData["user_hero"] = sharedPrefs.getString("userHero")
        initialize(MainGdxClass(additionalData), config)

    }
}