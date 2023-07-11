package ba.etf.rma23.projekat

import ba.etf.rma23.projekat.UserImpression

data class UserReview(
    override val username: String, //ne da override
    override val timestamp: Long,
    val review: String
): UserImpression()  //trazi konstruktor da se popuni!
//klasu sam popravila u skladu s odgovorima na Piazzi, isto vrijedi i za UserRating te UserImpression