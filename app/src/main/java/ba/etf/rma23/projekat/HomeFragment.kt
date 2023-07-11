package ba.etf.rma23.projekat

import android.os.Bundle
import android.view.LayoutInflater


import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

import android.view.View
import android.view.ViewGroup

import android.widget.EditText
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import ba.etf.rma23.projekat.data.repositories.IGDBApiConfig
import com.example.rma23_19084_projekat.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment: Fragment() {
    //kako je receno da sami odlucujemo kako cemo implementirati interfejs, rasporedila sam metode onako kako sam procijenila da je najintuitivnije
    //safe mode dugme primjenjuje removeNonSafe metodu na originalnu listu
    //nakon ulogovanja, sortGames ce prikazati sortirane spasene igre i pvih 10 igara dobijenh s twitch-a
    //search new poziva ili getGamesByName ili getGamesSafe, zavisno od toga da li je korisnik mladji od 18 godina ili ne (receno na piazzi)
    //search saved poziva getGamesContainingString, tj. pretrazuje spasene igrice
    //saved games daje getSavedGames, tj. prikazuje spasene igrice
    //nakon klika na igricu, korisnik moze istu da spasi ili obrise iz liste spasenih igrica - saveGame i removeGame metode
    //setAge i setHash se pozivaju pri log in-u

    private lateinit var gamesView: RecyclerView
    private lateinit var gamesAdapter: ArrayAdapterHome
    private lateinit var gamesList: List<Game>


    private lateinit var searchButton: Button
    private lateinit var searchButtonSaved: Button
    private lateinit var safeButton: Button
    private lateinit var savedButton: Button
    private lateinit var searchText: EditText
    private var  clickedSearch: Boolean = false;
    private var  clickedSearchSaved: Boolean = false;
    private var  clickedSafeMode: Boolean = false;
    private var  clickedSearchSafe: Boolean = false;
    private var  clickedSavedGames: Boolean = false;

    private var clicked: Boolean = false;


    override fun onCreateView(  //kreiramo pogled za fragment
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(
            R.layout.fragment_home, container,  //inflateamo odgovarajuci xm file
            false  //ovo ce android da odradi za nas
        )
        var receivedTitle:String = requireArguments().getString("game_title").toString()


        gamesView = view.findViewById(R.id.game_list) //moram mu pristupiti preko view koji sam kreirala u onCreateView
        gamesView.layoutManager = GridLayoutManager(activity,1)


        searchButton = view.findViewById(R.id.search_button)
        searchButtonSaved = view.findViewById(R.id.search_button_saved)
        searchText = view.findViewById(R.id.search_query_edittext)
        safeButton = view.findViewById(R.id.safe_mode)
        savedButton = view.findViewById(R.id.saved_games)

        //search new button listener
        searchButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                try {
                   // println("AGE USER:" + AccountGamesRepository.age)
                    if (AccountGamesRepository.age!! >= 18) {
                        gamesList = GamesRepository.getGamesByName(searchText.text.toString())
                        clickedSearch = true;
                       // println("KLIKNUTO")
                        gamesAdapter =
                            ArrayAdapterHome(gamesList) { game -> //findNavController().currentDestination?.id = R.id.homeFragment
                                findNavController().navigate(
                                    R.id.gameDetailsFragment,
                                    Bundle().apply { putString("game_title", game.title) })
                            }
                        gamesView.adapter = gamesAdapter
                        gamesAdapter.notifyDataSetChanged()
                    } else {
                        gamesList = GamesRepository.getGamesSafe(searchText.toString())
                        clickedSearch = true;
                        gamesAdapter =
                            ArrayAdapterHome(gamesList) { game -> //findNavController().currentDestination?.id = R.id.homeFragment
                                findNavController().navigate(
                                    R.id.gameDetailsFragment,
                                    Bundle().apply { putString("game_title", game.title) })
                            }
                        gamesView.adapter = gamesAdapter
                        gamesAdapter.notifyDataSetChanged()
                    }
                }  //POSLATI ADAPTERU LISTU!!!
                catch (e: Exception) {

                            e.printStackTrace()
                }
            }
        }

        //search saved button
        searchButtonSaved.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                try {

                        gamesList = AccountGamesRepository.getGamesContainingString(searchText.text.toString())
                        clickedSearchSaved = true;
                        //println("KLIKNUTO")
                        gamesAdapter =
                            ArrayAdapterHome(gamesList) { game -> //findNavController().currentDestination?.id = R.id.homeFragment
                                findNavController().navigate(
                                    R.id.gameDetailsFragment,
                                    Bundle().apply { putString("game_title", game.title) })
                            }
                    gamesView.adapter = gamesAdapter
                    gamesAdapter.notifyDataSetChanged()

                }  //POSLATI ADAPTERU LISTU!!!
                catch (e: Exception) {

                    e.printStackTrace()
                }
            }
        }

        //safe mode
      safeButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    var gamesTemp: List<Game> = GamesRepository.sortGames()
                    AccountGamesRepository.removeNonSafe()  //iz saved brise non-safe
                    gamesList = GamesRepository.sortGames()  //nova lista sortiranih igrica
                    clickedSafeMode = true;
                    println("KLIKNUTO")
                    gamesAdapter =
                        ArrayAdapterHome(gamesList) { game -> //findNavController().currentDestination?.id = R.id.homeFragment
                            findNavController().navigate(
                                R.id.gameDetailsFragment,
                                Bundle().apply { putString("game_title", game.title) })
                        }


                    gamesView.adapter = gamesAdapter
                    gamesAdapter.notifyDataSetChanged()

                }  //POSLATI ADAPTERU LISTU!!!
                catch (e: Exception) {

                    e.printStackTrace()
                }
            }
        }

        //savedGames
        //search new button listener
        savedButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                try {

                        gamesList = AccountGamesRepository.getSavedGames()
                        clickedSavedGames = true;
                        gamesAdapter =
                            ArrayAdapterHome(gamesList) { game -> //findNavController().currentDestination?.id = R.id.homeFragment
                                findNavController().navigate(
                                    R.id.gameDetailsFragment,
                                    Bundle().apply { putString("game_title", game.title) })
                            }
                        gamesView.adapter = gamesAdapter
                        gamesAdapter.notifyDataSetChanged()

                }  //POSLATI ADAPTERU LISTU!!!
                catch (e: Exception) {

                    e.printStackTrace()
                }
            }
        }




        GlobalScope.launch(Dispatchers.Main) {
            try {

                 if (!clickedSearch && !clickedSearchSaved && !clickedSafeMode && !clickedSearchSafe && !clickedSavedGames){

                     gamesList = GamesRepository.sortGames()
                     println("IME PRVE" + gamesList[0].title)
                     gamesAdapter =
                         ArrayAdapterHome(gamesList) { game -> //findNavController().currentDestination?.id = R.id.homeFragment
                             findNavController().navigate(
                                 R.id.gameDetailsFragment,
                                 Bundle().apply { putString("game_title", game.title) })
                         }
                     //  }

                     // findNavController().currentDestination?.id = R.id.homeFragment
                     gamesView.adapter = gamesAdapter  //adapter umjesto setAdapter

                 }


            } catch (e: Exception) {
                // Handle any exceptions
                e.printStackTrace()
            }


            /*   if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_details) as NavHostFragment
            val navController = navHostFragment.navController
            gamesAdapter =
                ArrayAdapterHome(gamesList) { game -> //findNavController().currentDestination?.id = R.id.homeFragment
                    navController.navigate(R.id.gameDetailsFragment, Bundle().apply { putString("game_title", game.title) })
                }
           //potrebno je odrediti navcontroller u ovom slucaju
        }
        else {


            gamesAdapter =
                ArrayAdapterHome(gamesList) { game -> //findNavController().currentDestination?.id = R.id.homeFragment
                    findNavController().navigate(
                        R.id.gameDetailsFragment,
                        Bundle().apply { putString("game_title", game.title) })
                }
            //  }

            // findNavController().currentDestination?.id = R.id.homeFragment
            gamesView.adapter = gamesAdapter  //adapter umjesto setAdapter
            val message = "Hello, world!"
            //val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(message)
            //button.setOnClickListener(Navigation.createNavigateOnClickListener(action))
            */

        }
        return view
    }




    private fun getGameByTitle(title: String): Game {
        var game: Game
        game = gamesList.find { game -> title==game.title }!!
        return game
        //elvis operator - default rijednost ako je null je u zagradi
    }

    private fun fetchGameData() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                var gameDataProvider: IGDBApiConfig = IGDBApiConfig();

                val games = gameDataProvider.getGameData()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}