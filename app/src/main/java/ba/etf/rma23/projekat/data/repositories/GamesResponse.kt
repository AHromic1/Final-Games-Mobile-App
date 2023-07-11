package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import com.google.gson.annotations.SerializedName

data class GamesResponse (
    @SerializedName("page") val page: Int,
    @SerializedName("results") val games: List<Game>,
    @SerializedName("total_pages") val pages: Int

)