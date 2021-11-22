package com.yainnixdev.internethero

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.files.FileHandle
import com.yainnixdev.internethero.utils.gameScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object MusicHandler{
    private lateinit var currentTrack : Music
    private var currentTrackPath : String? = null
    private val musicList = listOf(
        "music/town_0.ogg",
        "music/town_1.ogg",
        "music/town_2.ogg",
        "music/town_3.ogg"
    )
        //Bad logic. REDO

    fun playMusicList(){
        if(currentTrackPath == null) currentTrackPath = musicList.random()
        currentTrack = Gdx.audio.newMusic(Gdx.files.internal(currentTrackPath))
        var playTimes = 3
        currentTrack.play()
        currentTrack.setOnCompletionListener {
            playTimes--
            if (playTimes != 0) currentTrack.play()
            else {
                currentTrack.pause()
                playTimes = 3
                currentTrackPath = musicList.filterNot { it == currentTrackPath }.random()
                gameScope.launch {
                    delay(5000)
                    playMusicList()
                }
            }
        }
    }

    fun dispose(){
        currentTrack.dispose()
    }
}