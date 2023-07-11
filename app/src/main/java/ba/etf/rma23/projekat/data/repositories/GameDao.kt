package ba.etf.rma23.projekat.data.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ba.etf.rma23.projekat.Game


@Dao
interface GameDao {
    @Query("SELECT * FROM gamereview")
    suspend fun getAll(): List<GameReview>

    @Query("SELECT * FROM gamereview WHERE online = 0")  //0 i 1 koristiti!
    suspend fun getOffline(): List<GameReview>

    @Insert
    suspend fun insertAll(vararg games: GameReview)  //dozvoljava vise game objekata da se posalje

    @Insert
    suspend fun insertOne(game: GameReview)

    @Update
    suspend fun updateOnline(game: GameReview)
}