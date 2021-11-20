package com.yainnixdev.internethero.api

import android.app.Application
import com.google.gson.GsonBuilder
import com.yainnixdev.internethero.creatures.Creature
import com.yainnixdev.internethero.creatures.Enemy
import com.yainnixdev.internethero.creatures.Hero
import com.yainnixdev.internethero.utils.RuntimeTypeAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit


var token: String? = null
var okHttpClient: OkHttpClient? =null

object APIHandler : Application() {
    private lateinit var api: API


    private val runtimeTypeAdapterFactory =
        RuntimeTypeAdapterFactory
            .of(Creature::class.java, "type")
            .registerSubtype(Hero::class.java, "hero")
            .registerSubtype(Enemy::class.java, "enemy")

    fun getApi(SERVER_URL : String, mToken : String): API {
        token = mToken
        if(!this::api.isInitialized) {
            val gsonSettings = GsonBuilder()
                .registerTypeAdapterFactory(runtimeTypeAdapterFactory)
                .setLenient()
                .create()
            val retrofit = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gsonSettings))
                .baseUrl(SERVER_URL)
                .client(customOkhttpClient())
                .build()
            api = retrofit.create(API::class.java)
        }

        return api
    }

    fun getApi() :API{ if(this::api.isInitialized) return api else throw Exception("Api was not initialized") }


}


fun customOkhttpClient() : OkHttpClient {
    if (okHttpClient != null) return okHttpClient as OkHttpClient
    if (token != null) {
        okHttpClient = OkHttpClient.Builder().addInterceptor {
            val request = it.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            it.proceed(request)
        }
            .retryOnConnectionFailure(true)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    } else throw Exception("Can't instantiate HTTP Client")

    return okHttpClient as OkHttpClient
}