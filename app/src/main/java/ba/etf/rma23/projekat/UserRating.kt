package ba.etf.rma23.projekat

import ba.etf.rma23.projekat.UserImpression


/*data automatski ima settere gettere i sl, ne moraju se definisati*/
data class UserRating(
    override val username: String,
    override val timestamp: Long, //cannot be overwritten jer je timestamp final - dodala da je open
    val rating: Double
): UserImpression()