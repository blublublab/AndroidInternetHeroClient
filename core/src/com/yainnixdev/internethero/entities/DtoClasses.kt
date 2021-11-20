package com.yainnixdev.internethero.entities


import com.google.gson.annotations.SerializedName
import com.yainnixdev.internethero.creatures.Creature
import com.yainnixdev.internethero.creatures.appearence.HeroLook
import com.yainnixdev.internethero.utils.Point

class DtoClasses {
    data class HeroDto(val heroName: String = "",
                       var point: Point, var moveIntention: Point?,
                       val direction: Creature.CreatureDirection,
                       var level : Int = 0, var money: Int = 0,
                       val heroLook: HeroLook)

    data class User(val email : String,
                    @SerializedName("user_id") val userId : String,
                    val username : String,
                    @SerializedName("picture_url")
                    val pictureURL: String, val locale : String)

}