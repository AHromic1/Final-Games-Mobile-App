package ba.etf.rma23.projekat.data.repositories

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory




class GameReviewsRepository {
    companion object {

        suspend fun sendReview(context: Context, gameReview: GameReview):Boolean{  //DA LI TREBA review: GameReview???
            return withContext(Dispatchers.IO) {
                try {
                    val retrofit = Retrofit.Builder()
                    .baseUrl("https://rma23ws.onrender.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val apiService = retrofit.create(Api::class.java)

                val savedGames: List<Game> =
                    AccountGamesRepository.getSavedGames()  //lista omiljenih igrica
                var saved: Boolean = false;
                for (game in savedGames) {
                    if (game.id == gameReview.igdb_id) {
                        saved = true;
                    }
                }
                if (!saved) {
                    println("nije spaseno")
                    val retrofit2 = Retrofit.Builder()
                        .baseUrl("https://api.igdb.com/v4/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    val apiService = retrofit2.create(Api::class.java)
                    println("this is id im using to search: " + gameReview.igdb_id)
                    val game2List: List<Game2> =
                        apiService.getById(id = gameReview.igdb_id)    //problem!
                    println("I retrieved the game using id:" + game2List[0].name);
                    val game = GamesRepository.Game2toGame(game2List)[0]
                    AccountGamesRepository.saveGame(game)
                }


                    val sendReview = SendReview(gameReview.review, gameReview.rating)
                    val response =
                        apiService.sendReview(gid = gameReview.igdb_id, gameReview = sendReview)
                    println("spaseno!")
                    return@withContext true; //dodati success i fail
                } catch (e: Exception) {
                    println("Error! Added to the database instead.")
                    gameReview.online = false;
                    println("promijenjeno na false $gameReview");
                    val result = writeToDB(context, gameReview)
                    if (result == "success") {
                        println("Review added successfully")
                    } else {
                        println("Error")
                    }
                    return@withContext false;
                }
                //dodati u bazu
            }
        }


        //sendOfflineReviews():Int //- vraća broj review-ova koji su uspješno poslani na web servis, a koji ranije nisu bili spašeni (bili su samo lokalno u sqlite bazi). Svim review-ovima koje uspješno pošaljete na web servis ažurirajte kolonu online na true.
       suspend fun sendOfflineReviews(context: Context): Int{
            return withContext(Dispatchers.IO) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://rma23ws.onrender.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val apiService = retrofit.create(Api::class.java)

                var counter: Int = 0;
                val offlineList: List<GameReview> = getOfflineReviews(context)
                println("These are offline reviews BEFORE: $offlineList")
                for (review in offlineList) {
                    try {
                        val sendReview = SendReview(review.review, review.rating)
                        val response =
                            apiService.sendReview(gid = review.igdb_id, gameReview = sendReview)
                        counter += 1;
                        //update za online kolonu
                        review.online = true;
                        updateOnlineColumn(context, review);
                    } catch (e: Exception) {
                        println("Unsucessful operation");
                        e.printStackTrace()
                    }
                }
                return@withContext counter;
            }
       }


        suspend fun getReviewsForGame(igdb_id: Int): List<GameReview>//- vraća listu review-ova sa web servisa za igru sa zadanim igdb id-em.
        {
            return withContext(Dispatchers.IO) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://rma23ws.onrender.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val apiService = retrofit.create(Api::class.java)
                val gamesResponse =
                    apiService.getGameReviews(gid = igdb_id);
                return@withContext gamesResponse;
            }
        }

        //add to database
        suspend fun writeToDB(context: Context, review:GameReview) : String?{
            return withContext(Dispatchers.IO) {
                try{
                    var db = AppDatabase.getInstance(context)
                    db!!.gameDao().insertOne(review)
                    return@withContext "success"
                }
                catch(error:Exception){
                    error.printStackTrace()
                    return@withContext null
                }
            }
        }
        //update online column
        suspend fun updateOnlineColumn(context: Context, review:GameReview) : String?{
            return withContext(Dispatchers.IO) {
                try{
                    var db = AppDatabase.getInstance(context)
                    db!!.gameDao().updateOnline(review)
                    return@withContext "success"
                }
                catch(error:Exception){
                    error.printStackTrace()
                    return@withContext null
                }
            }
        }

        //get from database
        suspend fun getFromDB(context: Context) : List<GameReview> {
            return withContext(Dispatchers.IO) {
                var db = AppDatabase.getInstance(context)
                var games = db!!.gameDao().getAll()
                return@withContext games
            }
        }

        //get offline from database
        suspend fun getOfflineReviews(context: Context) : List<GameReview> {
            return withContext(Dispatchers.IO) {
                var db = AppDatabase.getInstance(context)
                var games = db!!.gameDao().getOffline()
                return@withContext games
            }
        }


    }
}