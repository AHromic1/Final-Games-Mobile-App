package ba.etf.rma23.projekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.rma23_19084_projekat.R


class ArrayAdapterHome ( //konstruktor za ArrayAdapterHome
    private var games: List<Game>, //lista igara
    private val onItemClicked: (game: Game) -> Unit //bit ce definisano sta ce se desiti kada se klikne na igru iz liste
) : RecyclerView.Adapter<ArrayAdapterHome.GameViewHolder>() { //nasljedjuje recylerview koji ce raditi s games

    //override onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game, parent, false)
        //kreiramo const varijablu view / pozivamo inflator/inflate iz parenta/inflate xml file game
        return GameViewHolder(view)
        //vracamo holder ovog inflaetanog view-a
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val gameValue = games[position]
        //holder je vec primljen kao parametar funkcije
        holder.gameTitle.text = gameValue.title //mora .text
        holder.gameReleaseDate.text = gameValue.releaseDate
        holder.gamePlatform.text = gameValue.platform
        holder.gameRating.text = gameValue.rating.toString()
        holder.itemView.setOnClickListener{ onItemClicked(gameValue)}

    }

    override fun getItemCount(): Int {
        return games.size
        //moglo je i = games.size, to bi bio kraci nacin pisanja
    }

    //klasa za GameViewHolder
    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { //nasljedjuje recyclerview viewholder
        val gameTitle: TextView = itemView.findViewById(R.id.item_title_textview) //itemBiew kao parametar
        val gameReleaseDate: TextView = itemView.findViewById(R.id.game_release_date_textview)
        val gamePlatform: TextView = itemView.findViewById(R.id.game_platform_textview)
        val gameRating: TextView = itemView.findViewById(R.id.game_rating_textview)
    }

}