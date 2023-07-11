package ba.etf.rma23.projekat.data.repositories

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class IGDBApiConfig {

    var baseURL: String =  "https://api.igdb.com/v4/";


        val retrofit: Retrofit = Retrofit.Builder()  //:Api!!!
            .baseUrl("https://api.igdb.com/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    val api = retrofit.create(Api::class.java)

    //retrofit.create se moglo i odmah nakon .build staviti, slicno kao i na vjezbama



suspend fun getGameData(): List<Game2> {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.igdb.com/v4/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(Api::class.java)

    val gamesResponse = apiService.getGames()

    return gamesResponse?: emptyList()
}




    val clientId = "uiattswnsfxde39hjz7lupokhv9n54"




}