package ba.etf.rma23.projekat

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GameReview
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository
import com.example.rma23_19084_projekat.R
import kotlinx.coroutines.launch

class ReviewFragment: Fragment() {
    private lateinit var submitButton: Button
    private lateinit var igdbText: EditText

    private lateinit var reviewText: EditText
    private lateinit var ratingText: EditText

    override fun onCreateView(  //kreiramo pogled za fragment
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(
            R.layout.fragment_review, container,
            false
        )
        submitButton = view.findViewById(R.id.submit)
        igdbText = view.findViewById(R.id.igdb_id_edittext)
        reviewText = view.findViewById(R.id.review_edittext)
        ratingText = view.findViewById(R.id.rating_edittext)


        submitButton.setOnClickListener {
            var gameReview = GameReview(ratingText.text.toString().toInt(),
            reviewText.text.toString(), igdbText.text.toString().toInt(),true, "", ""); //sve su online dok se ne kaze drukcije
           addReview(gameReview, requireContext())
        }

        return view
    }

    private fun addReview(review: GameReview, context:Context) {
        lifecycleScope.launch {
            val result = GameReviewsRepository.sendReview(context, review)
            GameReviewsRepository.sendOfflineReviews(context)
        }
    }

}