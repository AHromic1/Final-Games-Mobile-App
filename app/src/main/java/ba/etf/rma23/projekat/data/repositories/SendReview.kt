package ba.etf.rma23.projekat.data.repositories

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class SendReview  (
    @SerializedName("review") val review: String?,
    @SerializedName("rating") val rating: Int?
)