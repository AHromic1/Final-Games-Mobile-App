package ba.etf.rma23.projekat.data.repositories

import retrofit2.Call
import retrofit2.http.*

interface ApiToken {

    @FormUrlEncoded
    @POST("token")  //endpoint
    fun getToken(
        @Field("grant_type") grantType: String = "client_credentials",
        @Field("client_id") clientId: String = "uiattswnsfxde39hjz7lupokhv9n54",
        @Field("client_secret") clientSecret: String = "cimlgxx2hq2jv9zzjjknmohofopm2s"
    ): Call<AccessTokenResponse>  //response are returned values

}