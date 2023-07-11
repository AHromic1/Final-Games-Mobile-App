package ba.etf.rma23.projekat.data.repositories

import retrofit2.http.*

interface Api {
/*
   // @FormUrlEncoded
    @POST("games")
    suspend fun getGames(
        @Header("Client-id") clientId: String = c,  //ZAREZ!!!
        @Header("Authorization") token: String = "Bearer 498sykfvn96xgpnu805juluya9rsbe"
    ): List<Game>  //govorim STA vraca

*/
@POST("games")
suspend fun getGames(
    @Header("Client-ID") clientId: String = "uiattswnsfxde39hjz7lupokhv9n54",
    @Header("Authorization") authorization: String = "Bearer 498sykfvn96xgpnu805juluya9rsbe",
    @Query("fields") fields: String = "id, name, platforms.name,release_dates.human,rating, genres.name, cover.url, age_ratings.category, age_ratings.rating, involved_companies.company.name, summary;"//"name,rating, platforms.name, genres.name, summary, first_release_date,cover.url", // Include age_ratings and platforms with all attributes
    //@Query("limit") limit: Int = 10
): List<Game2>

@GET("games")
suspend fun searchGames(
    @Header("Client-ID") clientId: String  = "uiattswnsfxde39hjz7lupokhv9n54",
    @Header("Authorization") authorization: String = "Bearer 498sykfvn96xgpnu805juluya9rsbe",
    @Query("search") name: String,
    @Query("fields") fields: String = "id, name, platforms.name,release_dates.human,rating, genres.name, cover.url, age_ratings.category, age_ratings.rating, involved_companies.company.name, summary;",
    @Query("limit") limit: Int = 10
): List<Game2>

@GET("account/{aid}/games")
suspend fun savedGames(
    @Path("aid") aid: String= AccountGamesRepository.getHash(),//"0d539c39-3068-42ae-aa94-48b3e10e36bc",
    @Query("fields") fields: String = "*"
): List<SavedGames>

@POST("games")
suspend fun getGame(
    @Header("Client-ID") clientId: String  = "uiattswnsfxde39hjz7lupokhv9n54",
    @Header("Authorization") authorization: String = "Bearer 498sykfvn96xgpnu805juluya9rsbe",
    @Query("search") name: String,    //ime je unique, moze ime!
    @Query("fields") fields: String = "id, name, platforms.name,release_dates.human,rating, genres.name, cover.url, age_ratings.category, age_ratings.rating, involved_companies.company.name, summary;"
): List<Game2>



@GET("games/{id}")
suspend fun getById(
    @Header("Client-ID") clientId: String = "uiattswnsfxde39hjz7lupokhv9n54",
    @Header("Authorization") authorization: String = "Bearer 498sykfvn96xgpnu805juluya9rsbe",
    @Path("id") id: Int?,
    @Query("fields") fields: String = "id, name, platforms.name,release_dates.human,rating, genres.name, cover.url, age_ratings.category, age_ratings.rating, involved_companies.company.name, summary"
): List<Game2>

@POST("account/{aid}/game")
suspend fun saveGame(
    @Path("aid") aid: String= AccountGamesRepository.getHash(),//"0d539c39-3068-42ae-aa94-48b3e10e36bc",//
    @Body gameWrapper: GameWrapper
)


@GET("login")
suspend fun login(
)

@DELETE("account/{aid}/game/{gid}/")
suspend fun deleteGame(
    @Path("aid") aid:String =AccountGamesRepository.getHash(), //"0d539c39-3068-42ae-aa94-48b3e10e36bc",
    @Path("gid") gid:Int //broj igrice koja se brise0d
)

@GET("game/{gid}/gamereviews")
suspend fun getGameReviews(
        @Path("gid") gid:Int //broj igrice za koju dohvacam reviews
):List<GameReview>

@POST("account/{aid}/game/{gid}/gamereview")
    suspend fun sendReview(
        @Path("aid") aid:String = AccountGamesRepository.getHash(), //"0d539c39-3068-42ae-aa94-48b3e10e36bc",//AccountGamesRepository.getHash(),
        @Path("gid") gid:Int, //broj igrice koja se brise0d
        @Body gameReview: SendReview
)

}