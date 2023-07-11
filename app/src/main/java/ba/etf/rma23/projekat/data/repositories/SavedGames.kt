package ba.etf.rma23.projekat.data.repositories

data class SavedGames (
   // var id: Int,
    var igdb_id: Int,  //provjeriti moze li Long!!! - ne
    var name: String
   //var createdAt: String,
   // var updatedAt: String
    )

data class GameWrapper(
    val game: SavedGames
)