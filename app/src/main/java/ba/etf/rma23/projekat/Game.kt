package ba.etf.rma23.projekat

import ba.etf.rma23.projekat.UserImpression

data class Game(
    var id: Int,
 var title: String,  //ima name
 var platform: String,  //ima platform
 var releaseDate: String,  //ima first_release date
 var rating: Double,  //ima rating
 var coverImage: String,  //ima, dobije se url
 var esrbRating: String,
 var developer: String,
 var publisher: String,
 var genre: String, //ima
 var description: String,  //ima
 var userImpressions: List<UserImpression> = listOf() //zasad prazno, do iduceg puta (:

)

data class AgeRating(
   //val id: Long,
   val rating: Int,
   //val category: Int
)

data class Platform(
   // val id: Long,
   val name: String
)
data class Genre(
   val name: String
)

data class Cover(
   val url:String
)
