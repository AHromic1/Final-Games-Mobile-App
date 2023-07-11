package ba.etf.rma23.projekat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GameReview
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository
import com.bumptech.glide.Glide
import com.example.rma23_19084_projekat.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameDetailsFragment : Fragment() {
    private lateinit var commentsView: RecyclerView
    private lateinit var customAdapter: AdaptersGameDetails
    private var gamesList = GameData.getAll()
    private lateinit var game: Game
    private lateinit var sortedComments: List<UserImpression>
    private lateinit var gameTitle: TextView
    private lateinit var coverImage: ImageView
    private lateinit var platform: TextView
    private lateinit var esbrRating: TextView
    private lateinit var developer: TextView
    private lateinit var publisher: TextView
    private lateinit var genre: TextView
    private lateinit var description: TextView
    private lateinit var releaseDate: TextView

    private lateinit var saveButton: Button
    private lateinit var deleteButton: Button

    private fun getReviewsForAdapter(id:Int) {
        lifecycleScope.launch {
            GameReviewsRepository.getReviewsForGame(id)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view =
            inflater.inflate(R.layout.fragment_game_details, container, false)  //inflaetam fragment

        //pronalazimo sve elemente
        saveButton = view.findViewById(R.id.save)
        deleteButton = view.findViewById(R.id.delete)
        gameTitle = view.findViewById(R.id.item_title_textview)
        coverImage = view.findViewById(R.id.cover_imageview)
        platform = view.findViewById(R.id.platform_textview)
        esbrRating = view.findViewById(R.id.esrb_rating_textview)
        developer = view.findViewById(R.id.developer_textview)
        publisher = view.findViewById(R.id.publisher_textview)
        genre = view.findViewById(R.id.genre_textview)
        description = view.findViewById(R.id.description_textview)
        releaseDate = view.findViewById(R.id.release_date_textview)

        commentsView = view.findViewById(R.id.comments_list)
        commentsView.layoutManager = GridLayoutManager(activity,1)





        //gamesView.adapter = gamesAdapter
       // gamesAdapter.notifyDataSetChanged()


        var receivedTitle:String = requireArguments().getString("game_title").toString()?: ""


        GlobalScope.launch(Dispatchers.Main) {
            try {
                game = AccountGamesRepository.getOneGame(receivedTitle)
                populateDetails()

                //reviews mapiranje
                var userReview:UserReview? = null;
                var userRating:UserRating? = null;
                var commentsList: MutableList<UserImpression> = mutableListOf();
                var reviewsList: List<GameReview> = GameReviewsRepository.getReviewsForGame(game.id)
                for(review in reviewsList){
                    if(review.review!=null && review.rating == null){  //mapirati u review
                        if(review.student!=null) {
                            userReview =
                                UserReview(review.student!!, 0, review.review)
                            commentsList.add(userReview)
                        }
                    }
                    else if(review.rating!=null && review.review == null){ //mapirati u rating
                        if(review.student!=null) {
                            userRating = UserRating(review.student!!, 0, review.rating.toDouble());
                            commentsList.add(userRating);
                        }
                    }
                }

                //adapter
                customAdapter =
                    AdaptersGameDetails(commentsList as List<UserImpression>);
                commentsView.adapter = customAdapter;
                customAdapter.notifyDataSetChanged();

                //elvis operator - default vrijednost ako je null je u zagradi
            } catch (e: Exception) {
                // Handle any exceptions
                e.printStackTrace()
            }
        }

        saveButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                try {


                    game = AccountGamesRepository.getOneGame(receivedTitle)
                    println("KLIKNUTO")
                    AccountGamesRepository.saveGame(game)

                }
                catch (e: Exception) {

                    e.printStackTrace()
                }
            }
        }

        deleteButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                try {


                    game = AccountGamesRepository.getOneGame(receivedTitle)  //igrica koja je prikazana na ekranu
                    println("KLIKNUTO")
                    AccountGamesRepository.removeGame(game.id)

                }
                catch (e: Exception) {

                    e.printStackTrace()
                }
            }
        }
       // game = getGameByTitle(receivedTitle) //prima title iz  i nalazi odgovarajucu game



        // populateDetails()  //punimo sve detalje i views s onima iz pronadjene igre
        //game je ona koju sam dobile preko game title - moram ga sada preuzeti

      /*  sortedComments = game.userImpressions.sortedByDescending { it.timestamp }
        customAdapter=AdaptersGameDetails(sortedComments)
        commentsView.adapter = customAdapter
*/

        //receivedTitle saljem nazad u home
        // findNavController().navigate(R.id.action_gameDetailsFragment_to_homeFragment, Bundle().apply { putString("game_title",receivedTitle) })

        /*commentsView = findViewById(R.id.comments_list)
    commentsView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)*/
        return view
    }



    private fun populateDetails(){
        println("VOER IMAGE JE OVO JE COVER URL" + game.coverImage);
        gameTitle.text = game.title
       // coverImage.setImageURI(Uri.parse(game.coverImage))
        platform.text = game.platform
        releaseDate.text = game.releaseDate
        esbrRating.text = game.esrbRating
        developer.text = game.developer
        publisher.text = game.publisher
        genre.text = game.genre
        description.text = game.description
        //dodavanje slike - Glide
         val coverPath = "https:"
        Glide.with(this)
            .load(coverPath + game.coverImage)
            .centerCrop()
            .placeholder(R.drawable.logo_game)
            .error(R.drawable.logo_game)
            .into(coverImage)
    }
}