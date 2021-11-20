package com.yainnixdev.internethero

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.gson.Gson
import com.yainnixdev.internethero.api.API
import com.yainnixdev.internethero.api.APIHandler.getApi
import com.yainnixdev.internethero.creatures.Creature
import com.yainnixdev.internethero.creatures.Hero
import com.yainnixdev.internethero.utils.putAny
import com.yainnixdev.internethero.utils.sharedPrefs
import kotlinx.coroutines.*

@ExperimentalCoroutinesApi
class LoginActivity : AppCompatActivity() {
    private lateinit var api : API
    private val loginScope = CoroutineScope(Job() + Dispatchers.IO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPrefs = getSharedPreferences("save", 0)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_id))
            .requestId()
            .requestProfile()
            .requestEmail()
            .build()
        val  googleSignInClient = GoogleSignIn.getClient(this, gso)
        val intentLaunch = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                serverInit(task.result)
            }
        }
        googleSignInClient.silentSignIn().addOnCompleteListener(this) {
            try {
                serverInit(it.result)
            } catch (e: Exception) {
                intentLaunch.launch(Intent(googleSignInClient.signInIntent))
            }
        }
    }

    private fun serverInit(account: GoogleSignInAccount) {
        api = getApi(resources.getString(R.string.server_url), account.idToken ?: "")
        val userHeroJob = loginScope.async(Dispatchers.IO) { return@async try{
                    getUserHero(account)
                    }catch (e: Exception){ null }
        }

        sharedPrefs
            .putAny("user_id", account.id)
            .putAny("token", account.idToken)
        loginScope.launch {
            userHeroJob.await()
            val hero = userHeroJob.getCompleted()
            if(hero != null) {
                val heroJson = Gson().toJson(hero, Hero::class.java)
                println("hero: $heroJson")
                sharedPrefs.putAny("userHero", heroJson)
                startActivity(Intent(applicationContext, GameActivity::class.java))
            } else startActivity(Intent(applicationContext, HeroCreationActivity::class.java))

            finish()
            }

        }

    private suspend fun getUserHero(account : GoogleSignInAccount): Hero? =
        api.getUserHero(account.id?.toString())


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        loginScope.cancel()
    }
}