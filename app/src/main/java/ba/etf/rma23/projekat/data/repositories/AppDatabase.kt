package ba.etf.rma23.projekat.data.repositories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(GameReview::class), version = 8)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
         // context.deleteDatabase("gamereview")
                if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }
        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "gamereview"
            ).fallbackToDestructiveMigration().build()    //fallbackToDestructiveMigration().
    }
}