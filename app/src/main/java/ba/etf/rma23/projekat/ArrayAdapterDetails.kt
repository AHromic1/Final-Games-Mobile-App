package ba.etf.rma23.projekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rma23_19084_projekat.R

/*Najprije sam pokusala implementirati reviews i ratings koristeci dva adaptera i funkcije za spajanje adaptera (Concat i sl.),
* no kako ih android studio nije prepoznao, odlucila sam se na drugi pristup (AdaptersGameDetails). Tj. ovaj adapter nigdje ne koristim, no ostavila sam
* ga cisto kao  neku vrstu evidencije svega sto sam radila u okviru spirale 1.*/

class ArrayAdapterDetails (
    private var impressions: List<UserRating>, //lista igara
    //private val onItemClicked: (rating: UserRating) -> Unit
) : RecyclerView.Adapter<ArrayAdapterDetails.ImpressionsViewHolder>() { //nasljedjuje recylerview koji ce raditi s games

    //override onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImpressionsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_details_item_rating, parent, false)
        /*mozda da napravim generic layout bez ratinga i onda u onbind da pozivam odgovarajuci? da li bi to funkcionisalo? */
        //kreiramo const varijablu view / pozivamo inflator/inflate iz parenta/inflate xml file game
        return ImpressionsViewHolder(view)
        //vracamo holder ovog inflaetanog view-a
    }

    override fun onBindViewHolder(holder: ImpressionsViewHolder, position: Int) {
        //holder je vec primljen kao parametar funkcije
        holder.username.text = impressions[position].username //mora .text
        holder.rating = impressions[position].rating
        //holder.itemView.setOnClickListener { impressions[position] }
    }

    override fun getItemCount(): Int {
        return impressions.size

    }

    //klasa za ImpressionsViewHolder
    inner class ImpressionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { //nasljedjuje recyclerview viewholder
        var username: TextView = itemView.findViewById(R.id.username_textview)
        //ako ima rating bar
        var rate: TextView = itemView.findViewById(R.id.rating_bar)
        var rateS: String = rate.text.toString()  //text umjesto getText
        var rating: Double = rateS.toDouble()  //convert jer je nekompatabilno view i float
        //ako ima review
        /*
        var reviewV: TextView = itemView.findViewById(Build.VERSION_CODES.R.id.review_textview)
        var review: String = reviewV.text.toString()  //text umjesto getText
        */
    }

}