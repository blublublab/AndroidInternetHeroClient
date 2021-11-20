package com.yainnixdev.internethero.api


import com.yainnixdev.internethero.creatures.Hero
import com.yainnixdev.internethero.entities.DtoClasses
import com.yainnixdev.internethero.utils.Point
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface API {
    @POST("/hero/get_by/user_id")
    suspend fun getUserHero(@Body userId: String?):  Hero?

    @POST("/hero/get_by/hero_name")
    suspend fun getHeroByHeroName(@Body heroName: String?): Hero?


    @POST("/hero/create")
    suspend fun createNewHero(
        @Body hero: Hero?,
        @Header("user_id") userId: String?
    ): Hero?

    @POST("/hero/get_point")
    suspend fun getPointByHeroName(@Body heroName: String?) : Point?

    @GET("/server/get_time")
    suspend fun getServerTime() : Int?
}