package ba.etf.rma23.projekat

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rma23_19084_projekat.R

class GameDetailActivity : AppCompatActivity() {
    private lateinit var commentsView: RecyclerView
    private lateinit var customAdapter: AdaptersGameDetails
    private var gamesList = GameData.getAll()
    private lateinit var game: Game
    private lateinit var sortedComments: List<UserImpression>
    private lateinit var gameTitle : TextView
    private lateinit var coverImage : ImageView
    private lateinit var platform : TextView
    private lateinit var esbrRating : TextView
    private lateinit var developer : TextView
    private lateinit var publisher : TextView
    private lateinit var genre : TextView
    private lateinit var description : TextView
    private lateinit var releaseDate : TextView

    //xml stvari su lateinit

    //ova klasa se ne koristi vise u ovoj spirali, tj. po uputstvima, funkcionalnost je prebacena u GameDetailsFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)
        //finding buttons

        //return to home button
       /* homeButton.setOnClickListener {
            //goToHome()
            val intent = Intent(this, MainActivity::class.java)
            //ovdje moze put extra za title da pokusam, tkd mogu ostaviti details dugme na home da vodi na zadnje otvorenu app
            intent.putExtra("game_title", game.title)
            startActivity(intent)
        }*/


//trazim views za sve elemente
        gameTitle = findViewById(R.id.item_title_textview)
            coverImage = findViewById(R.id.cover_imageview)
        platform = findViewById(R.id.platform_textview)
        esbrRating = findViewById(R.id.esrb_rating_textview)
        developer = findViewById(R.id.developer_textview)
        publisher = findViewById(R.id.publisher_textview)
        genre = findViewById(R.id.genre_textview)
        description = findViewById(R.id.description_textview)
        releaseDate = findViewById(R.id.release_date_textview)

        commentsView = findViewById(R.id.comments_list)
        commentsView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        //primam intent
        val extras = intent.extras
        if (extras != null) {
            game = getGameByTitle(extras.getString("game_title","")) //prima title iz intenta i nalazi odgovarajucu game
            populateDetails()  //punimo sve detalje i views s onima iz pronadjene igre
        } else {
            finish()
        }
        //return to home button
       /* homeButton.setOnClickListener {
            //goToHome()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("game_title", game.title)
            startActivity(intent)
        }*/
        sortedComments = game.userImpressions.sortedByDescending { it.timestamp }
        customAdapter= AdaptersGameDetails(sortedComments)
        commentsView.adapter = customAdapter
    }


    private fun goToHome() {
        val intent = Intent(this, MainActivity::class.java)
        //intent.putExtra("game_title", extras)
        startActivity(intent)
        }

    private fun getGameByTitle(title: String): Game {
      game = gamesList.find { game -> title==game.title }!!
      return game//?:Game("","","",0.0,"","", "", "", "", "", listOf())
      //elvis operator - default vrijednost ako je null je u zagradi
    }

    private fun populateDetails(){
        gameTitle.text = game.title
        coverImage.setImageURI(Uri.parse(game.coverImage))
        platform.text = game.platform
        releaseDate.text = game.releaseDate
        esbrRating.text = game.esrbRating
        developer.text = game.developer
        publisher.text = game.publisher
        genre.text = game.genre
        description.text = game.description
    }

}
