package ba.etf.rma23.projekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.GameReview
import com.example.rma23_19084_projekat.R

class AdaptersGameDetails(
    private var impressions: List<UserImpression> //any je generic kao object
    //private val onItemClicked: (rating: UserRating) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() { //staviti RecyclerView.ViewHolder
    companion object {
        private const val ITEM_TYPE_REVIEW: Int = 0
        private const val ITEM_TYPE_RATING: Int = 1
    }


    inner class RatingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { //nasljedjuje recyclerview viewholder
        var username: TextView = itemView.findViewById(R.id.username_textview)
        //ako ima rating bar
        var rating: RatingBar = itemView.findViewById(R.id.rating_bar)
        //var rateS: String = rating.text.toString()  //text umjesto getText
       // var ratingD: Double = rateS.toDouble()  //convert jer je nekompatabilno view i float
        //ako ima review
        /*
        var reviewV: TextView = itemView.findViewById(Build.VERSION_CODES.R.id.review_textview)
        var review: String = reviewV.text.toString()  //text umjesto getText
        */
    }

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { //nasljedjuje recyclerview viewholder
        var username: TextView = itemView.findViewById(R.id.username_textview)
        //ako ima rating bar
        /*var rate: TextView = itemView.findViewById(Build.VERSION_CODES.R.id.rating_bar)
        var rateS: String = rate.text.toString()  //text umjesto getText
        var rating: Double = rateS.toDouble()  //convert jer je nekompatabilno view i float
        */

        //ako ima review

        var review: TextView = itemView.findViewById(R.id.review_textview)
        var reviewS: String = review.text.toString()  //text umjesto getText

    }

    override fun getItemViewType(position: Int): Int {
        return if (impressions[position] is UserReview) {  //is je instanceof
            ITEM_TYPE_REVIEW
        } else {
            ITEM_TYPE_RATING
        }
    }

    override fun getItemCount(): Int {
        return impressions.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == ITEM_TYPE_REVIEW) {
            view = layoutInflater.inflate(R.layout.game_details_review, parent, false)
            return ReviewViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.game_details_item_rating, parent, false)
            return RatingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


       if (holder is ReviewViewHolder) {
        //if(impression is UserReview)
           //impressions
            holder.username.text = impressions[position].username
            holder.review.text = (impressions[position] as UserReview).review

            //holder.bind(impression as UserReview)
        } else {
            holder as RatingViewHolder
            holder.username.text = impressions[position].username
            holder.rating.rating = (impressions[position] as UserRating).rating.toFloat()

        }
    }

}