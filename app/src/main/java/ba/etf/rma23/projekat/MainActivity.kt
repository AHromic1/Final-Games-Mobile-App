package ba.etf.rma23.projekat


import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ba.etf.rma23.projekat.data.repositories.*
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository.Companion.writeToDB
import com.example.rma23_19084_projekat.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class MainActivity : AppCompatActivity() {
    //kreiram instance fragmenata
    lateinit var homeFragment: HomeFragment
    lateinit var gameDetailsFragment: GameDetailsFragment
     var clicked = false

//ne moram postavljati koji je home fragment jer je to definisano kada se u navigaciji dodaju
private fun addGameReviewToDatabase(review: GameReview) {
    println("trying to add reviews")
    lifecycleScope.launch {
        val result = writeToDB(applicationContext, review)
        if (result == "success") {
            println("Review added successfully")
        } else {
            println("Error")
        }
    }
}

    private fun getGameReviewFromDatabase() {
        lifecycleScope.launch {
           /* val result =GameReviewsRepository.getFromDB(applicationContext)
            println("result is: $result");*/
           /* val review: GameReview = GameReview(7,5,"bad!!!", 1, true);
            GameReviewsRepository.sendReview(review, applicationContext)*/
            GameReviewsRepository.sendOfflineReviews(applicationContext)

        }
    }
    private fun addReview(review: GameReview) {
        lifecycleScope.launch {
            val result = GameReviewsRepository.sendReview(applicationContext,review)
        }
    }
    private fun getOfflineFromDatabase() {
        lifecycleScope.launch {
            val result =GameReviewsRepository.getOfflineReviews(applicationContext)
            println("result is: $result");
        }
    }
    private fun addToDB(review: GameReview){
        lifecycleScope.launch {
           review.online = false;
            //println("promijenjeno na false $gameReview");
            val result = writeToDB(applicationContext, review)
            if (result == "success") {
                println("Review added successfully")
            } else {
                println("Error")
            }

        }
    }
    private fun getOfflineFromDB() {
        lifecycleScope.launch {
            /* val result =GameReviewsRepository.getFromDB(applicationContext)
             println("result is: $result");*/
            /* val review: GameReview = GameReview(7,5,"bad!!!", 1, true);
             GameReviewsRepository.sendReview(review, applicationContext)*/
            val games = GameReviewsRepository.getFromDB(applicationContext)
            println("REVIEWS: $games")

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_home)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment  //nasli smo fragment u xml i castali ga u NavHost
            val navController = navHostFragment.navController


//inicijalizacija mora biti drukcija jer vise nisu navHost, jer ne zelim da mii se sve prikazuje u jednom containeru, zbog ladnscape-a
            val navHostFragmentDetails =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment_details) as NavHostFragment  //nasli smo fragment u xml i castali ga u NavHost

            val navControllerDetails =navHostFragmentDetails.navController //uzimamo njegov kontroler

            navControllerDetails.navigate(R.id.gameDetailsFragment, Bundle().apply { putString("game_title", GameData.getAll()[0].title) })

        }
        else {
            setContentView(R.layout.activity_home)


            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment //nasli smo fragment u xml i castali ga u NavHost

            val navController = navHostFragment.navController //uzimamo njegov kontroler
            val navigationView: BottomNavigationView =
                findViewById(R.id.bottom_nav)  //casta se u bottmnacigationview

            //nasli smo bottomnavigation
            navigationView.setupWithNavController(navController)  //povezujemo ga sa kontrolerom
            navController.navigate(R.id.logInFragment)  //PROVJERITI - radi!
            //inicijaliziram fragmente:
            homeFragment = HomeFragment()
            gameDetailsFragment = GameDetailsFragment()

            //  val currentFragment = navHostFragment.childFragmentManager.fragments.get(0)
            val openReviews = navigationView.menu.findItem(R.id.reviewItem)
            openReviews.setOnMenuItemClickListener {
                navController.navigate(R.id.reviewFragment)
                true
            }

            navController.addOnDestinationChangedListener(object :
                NavController.OnDestinationChangedListener {
                override fun onDestinationChanged(
                    controller: NavController,
                    destination: NavDestination,
                    @Nullable arguments: Bundle?
                ) {
                    if (destination.id == R.id.logInFragment) {
                        navigationView.menu.findItem(R.id.homeItem).isEnabled = false
                        navigationView.menu.findItem(R.id.gameDetailsItem).isEnabled = false
                        navigationView.menu.findItem(R.id.reviewItem).isEnabled = false
                    }
                    else if (destination.id == R.id.reviewFragment) {
                        navigationView.menu.findItem(R.id.homeItem).isEnabled = true
                        navigationView.menu.findItem(R.id.gameDetailsItem).isEnabled = false
                        navigationView.menu.findItem(R.id.reviewItem).isEnabled = false
                        val homeMenu = navigationView.menu.findItem(R.id.homeItem)
                        homeMenu.setOnMenuItemClickListener {
                            navController.navigate(R.id.homeFragment)
                        true
                        }
                    }
                    else if (destination.id == R.id.homeFragment) {
                        navigationView.menu.findItem(R.id.reviewItem).isEnabled = true
                        navigationView.menu.findItem(R.id.gameDetailsItem).isEnabled = false
                        navigationView.menu.findItem(R.id.homeItem).isEnabled = false
                        if (clicked) {
                            navigationView.menu.findItem(R.id.gameDetailsItem).isEnabled = true
                            val details = navigationView.menu.findItem(R.id.gameDetailsItem)
                            details.setOnMenuItemClickListener {
                                navController.navigate(R.id.gameDetailsFragment, Bundle().apply {
                                    putString(
                                        "game_title",
                                       arguments?.getString("game_title")
                                    )
                                })
                                true
                            }
                        }

                    } else {
                        navigationView.menu.findItem(R.id.reviewItem).isEnabled = true
                        navigationView.menu.findItem(R.id.gameDetailsItem).isEnabled = false
                        navigationView.menu.findItem(R.id.homeItem).isEnabled = true
                        val home = navigationView.menu.findItem(R.id.homeItem)
                        home.setOnMenuItemClickListener {
                            clicked = true
                            navController.navigate(
                                R.id.homeFragment,
                                Bundle().apply {
                                    putString(
                                        "game_title",
                                        arguments?.getString("game_title")
                                    )
                                })

                            true
                        }
                    }
                }
            })
        }
    }

   suspend fun writeDB(context: Context, review:GameReview){
        GlobalScope.launch(Dispatchers.Main) {
            try {

                val result = GameReviewsRepository.writeToDB(context, review);
            }
            catch (e:Exception){
            }
        }

    }

    private fun processGameData(games: List<Game>) {

        for (game in games) {
            println(" ID: ${game.id}")
            println(" NAME: ${game.title}")
            println(" PLATFORM: ${game.platform}")
            println(" RELEASEDATE: ${game.releaseDate}")
            println(" RATING: ${game.rating}")
            println(" COVERIMAGE: ${game.coverImage}")
            println(" ESRBRATING: ${game.esrbRating}")
            println(" DEVELOPER: ${game.developer}")
            println(" PUBLISHER: ${game.publisher}")
            println(" GENRE: ${game.genre}")
            println(" DESCRIPTION: ${game.description}")
            println(" USERIMPRESSIONS: ${game.userImpressions}")

        }
    }


    private fun processGameDataold(games: List<Game2>) {

        for (game in games) {
            println(" Name: ${game.name}")

            game.genres?.let{genres->
                println("Genres:")
                for (genre in genres) {
                    println("Platform name: ${genre.name}")
                }

            }

            game.cover?.let { cover ->
                println("Cover url: ${cover.url}")

            }

            game.platforms?.let { platforms ->
                println("Platforms:")
                for (platform in platforms) {
                    println("Platform name: ${platform.name}")
                }
            }

            games.forEach { game ->
                println("Game: ${game.name}")
                game.involved_companies?.let { involvedCompanies ->
                    println("Involved Companies:")
                    val first: Company = involvedCompanies[0].company
                    println("Company name: ${first.name}")
                 /*   involvedCompanies.forEach { involvedCompany ->
                        val company = involvedCompany.company
                        println("Company name: ${company.name}")
                    }*/
                }

            }

            println("--------------")
        }
    }


}





