package ba.etf.rma23.projekat.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AccessToken {


    var token: String? = null;

    //trebalo bi da dobijem token
    val retrofit : ApiToken = Retrofit.Builder()
        .baseUrl("https://id.twitch.tv/oauth2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiToken::class.java)

    val clientId = "uiattswnsfxde39hjz7lupokhv9n54"
    val clientSecret = "cimlgxx2hq2jv9zzjjknmohofopm2s"

    fun getAccessToken(callback: (String?) -> Unit) {
        val call = retrofit.getToken()
        call.enqueue(object : Callback<AccessTokenResponse> {
            override fun onResponse(
                call: Call<AccessTokenResponse>,
                response: Response<AccessTokenResponse>
            ) {
                if (response.isSuccessful) {
                    val accessTokenResponse = response.body()
                    val accessToken = accessTokenResponse?.accessToken
                    // Use the accessToken here
                    println("Access Token: $accessToken")
                    callback(accessToken) // Pass the accessToken to the callback
                } else {
                    val errorBody = response.errorBody()?.string()
                    println("Error: $errorBody")
                    callback(null) // Pass null to the callback in case of an error
                }
            }

            override fun onFailure(call: Call<AccessTokenResponse>, t: Throwable) {
                println("Failure: ${t.message}")
                callback(null) // Pass null to the callback in case of failure
            }
        })
    }


    fun getAccessToken() {
        val call = retrofit.getToken()
        call.enqueue(object : Callback<AccessTokenResponse> {
            override fun onResponse(
                call: Call<AccessTokenResponse>,
                response: Response<AccessTokenResponse>
            ) {
                if (response.isSuccessful) {
                    val accessTokenResponse = response.body()
                    val accessToken = accessTokenResponse?.accessToken
                    // Use the accessToken here

                    println("Access Token: $accessToken")

                } else {

                    val errorBody = response.errorBody()?.string()
                    println("Error: $errorBody")
                }
            }

            override fun onFailure(call: Call<AccessTokenResponse>, t: Throwable) { //ovu metodu sam morala staviti
                //zbog ovoga object

                println("Failure: ${t.message}")
            }
        })

    }

    fun requestAccessToken(callback: (String?) -> Unit) {
        val call = retrofit.getToken()
        call.enqueue(object : Callback<AccessTokenResponse> {
            override fun onResponse(
                call: Call<AccessTokenResponse>,
                response: Response<AccessTokenResponse>
            ) {
                if (response.isSuccessful) {
                    val accessTokenResponse = response.body()
                    val accessToken = accessTokenResponse?.accessToken
                    callback(accessToken) // Pass the access token as a string to the callback
                } else {
                    val errorBody = response.errorBody()?.string()
                    println("Error: $errorBody")
                    callback(null) // Pass null to the callback in case of an error
                }
            }

            override fun onFailure(call: Call<AccessTokenResponse>, t: Throwable) {
                println("Failure: ${t.message}")
                callback(null) // Pass null to the callback in case of failure
            }
        })
    }


    suspend fun getAccessToken3(): String? {
        return withContext(Dispatchers.IO) {
            try {
                val call = retrofit.getToken()
                val response = call.execute()

                if (response.isSuccessful) {
                    val accessTokenResponse = response.body()
                    val accessToken = accessTokenResponse?.accessToken
                    // Use the accessToken here
                    println("Access Token: $accessToken")
                    accessToken
                } else {
                    val errorBody = response.errorBody()?.string()
                    println("Error: $errorBody")
                    null
                }
            } catch (e: Exception) {
                println("Failure: ${e.message}")
                null
            }
        }
    }




}