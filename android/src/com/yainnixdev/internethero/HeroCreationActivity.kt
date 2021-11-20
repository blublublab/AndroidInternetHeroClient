package com.yainnixdev.internethero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.yainnixdev.internethero.api.API
import com.yainnixdev.internethero.api.APIHandler
import com.yainnixdev.internethero.creatures.Hero

import com.yainnixdev.internethero.creatures.appearence.HeroLook
import com.yainnixdev.internethero.creatures.appearence.parts.HeroAction
import com.yainnixdev.internethero.databinding.ActivityHeroCreationBinding
import com.yainnixdev.internethero.utils.Point
import com.yainnixdev.internethero.utils.getString
import com.yainnixdev.internethero.utils.sharedPrefs
import com.yainnixdev.internethero.utils.toast
import kotlinx.coroutines.*


@ExperimentalCoroutinesApi
class HeroCreationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHeroCreationBinding
    private lateinit var gameIntent: Intent
    private lateinit var api: API

    private val heroCreationScope = CoroutineScope(Job() + Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        api = APIHandler.getApi()
        gameIntent = Intent(this, GameActivity::class.java)

        binding.buttonCreateHero.setOnClickListener {
            val heroName = binding.editTextHeroName.text.toString()
            val newHero = Hero(0, 0, heroName, heroLook = HeroLook(0, HeroAction.WALK))
            val heroJsonJob = heroCreationScope.async {
                   return@async try{
                        api.createNewHero(newHero, sharedPrefs.getString("user_id"))
                    } catch (e: Exception){
                           null
                    }
                }
           heroCreationScope.launch {
               awaitAll(heroJsonJob)
               val hero = heroJsonJob.getCompleted()
               if(hero != null) {
                   gameIntent.putExtra("heroJson", Gson().toJson(hero))
                   finish()
                   startActivity(gameIntent)
               } else retryOnError()
           }
           }

    }
    private fun retryOnError() {
        var timeRetry = 3
        heroCreationScope.launch {
            for(i in timeRetry downTo 1) {
                runOnUiThread { toast("Server trouble on creating new hero, retry in $timeRetry") }
                delay(1000)
                timeRetry--
            }
            binding.buttonCreateHero.performClick()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        heroCreationScope.cancel()
    }
}
