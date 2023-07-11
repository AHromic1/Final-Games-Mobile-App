package ba.etf.rma23.projekat.data.repositories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity (tableName = "gamereview")
data class GameReview (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id") var id: Long = 0,
    @ColumnInfo(name = "rating") @SerializedName("rating") val rating: Int?,
    @ColumnInfo(name = "review") @SerializedName("review") val review: String?,
    @ColumnInfo(name = "igdb_id") @SerializedName("igdb_id") val igdb_id: Int,
    @ColumnInfo(name = "online") @SerializedName("online") var online: Boolean?, //? jer se ne vraca s web servisa
    @ColumnInfo(name = "student") @SerializedName("student") var student: String?,
    @ColumnInfo(name = "timestamp") @SerializedName("timestamp") var timestamp: String?
){
    constructor(rating: Int?, review: String?, igdb_id: Int, online: Boolean?, student: String?, timestamp: String?) :
            this(0, rating, review, igdb_id, online, student, timestamp);
}