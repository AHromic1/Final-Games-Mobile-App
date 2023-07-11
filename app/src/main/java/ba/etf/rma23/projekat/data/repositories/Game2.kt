package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class Game2(

    @SerializedName("id") val id: Int,  //smije li se id dodati?
    @SerializedName("name") val name: String,
    @SerializedName("age_ratings") val age_ratings: List<AgeRating>,
    @SerializedName("platforms") val platforms: List<Platform>?,   //uzeti jednu platformu pri prikazu!
    @SerializedName("release_dates") val release_dates: List<ReleaseDate>?,   //MORAT cu formatirati kada budem stavljala u layout!!!
    @SerializedName("rating") val rating: Double?,
    @SerializedName("genres") val genres: List<Genre>?,    //uzeti prvi genre pri prikazu!
    // val firstGenre: Int?,
    @SerializedName("summary") val summary: String?,
    @SerializedName("cover") val cover: Cover?,
    @SerializedName("involved_companies") val involved_companies: List<InvolvedCompany>,  //ovo je i za publisher i developer
    var esrbRating: Int?
    //zanr, oublisher i developer su problematicni, i cover cu morati u adapteru dodati preko glide-a
    //takodjer morat cu malo clean up koda uraditi, dodati sve u odgovarajuce klase, i sl.
)
//MORAM IH DODATI kao nove data classes, ne moze kao u+dodatni atribut u Game, dobijam null vrijednosti
data class AgeRating(
    @SerializedName("rating") val rating: Int,
    @SerializedName("category") val category: Int
)

data class Platform(
   // val id: Long,
    @SerializedName("name") val name: String
)
data class Genre(
    @SerializedName("name") val name: String
)

data class Cover(
    @SerializedName("url") val url:String
)
data class ReleaseDate(
    @SerializedName("id") val id: Int,
    @SerializedName("human") val human:String
)

data class InvolvedCompany(
    @SerializedName("company") val company: Company
)
data class Company(
    @SerializedName("name") val name: String
)