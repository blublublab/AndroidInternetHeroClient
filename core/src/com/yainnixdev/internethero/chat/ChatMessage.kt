package com.yainnixdev.internethero.chat

import com.badlogic.gdx.graphics.Color


enum class MessageChannel {REGION, GLOBAL, PRIVATE}

data class ChatMessage(
    val author : String,
    val channel : MessageChannel = MessageChannel.REGION,
    val sendTime : String,
    val text : String,

) {
  var messageColor : Int = Color.BLACK.toIntBits()
  private set
}



