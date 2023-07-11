package ba.etf.rma23.projekat

import com.google.gson.annotations.SerializedName

data class IdRequest (
    @SerializedName("fields") val fields: String,
    @SerializedName("where") val where: String
        )