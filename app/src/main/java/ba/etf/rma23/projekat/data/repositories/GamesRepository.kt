package ba.etf.rma23.projekat.data.repositories


import android.annotation.SuppressLint
import ba.etf.rma23.projekat.Game
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GamesRepository {
    companion object {

        @SuppressLint("SuspiciousIndentation")
        fun Game2toGame(games: List<Game2>): List<Game>{
            var result: MutableList<Game> = mutableListOf();
            for (game in games){
                var found = false;
                if(game.age_ratings!=null) {

                    var ratings = game.age_ratings

                    for (rating in ratings) { //za svaki rating iz liste
                       // println("catgory" + rating.category)
                        if (rating.category == 1) {//pronaci kategoriju
                            game.esrbRating = rating.rating
                            //println("ima esrb i rating je" + rating.rating)
                            found = true
                        }
                    }
                    if (!found)
                        for (rating in ratings) { //za svaki rating iz liste
                             if (rating.category == 2)//pronaci kategoriju
                               // println("ima pegi i rating je" + rating.rating)
                                game.esrbRating = rating.rating
                        }
                }
                //tek sada provjeravam da li ima PEGI rating, ukoliko esrb ne postoji

                val resultGame = Game(
                    id = game.id,
                    title = game.name,
                    platform = game.platforms?.get(0)?.name.orEmpty(),
                    releaseDate = game.release_dates?.get(0)?.human.orEmpty(),
                    rating = game.rating?.toDouble() ?: 0.0,
                    coverImage = game.cover?.url.orEmpty(),
                    esrbRating = game.esrbRating.toString(),
                    developer = game.involved_companies?.get(0)?.company?.name.orEmpty(),
                    publisher = game.involved_companies?.get(0)?.company?.name.orEmpty(),
                    genre = game.genres?.get(0)?.name.orEmpty(),
                    description = game.summary.orEmpty(),
                    userImpressions = listOf()
                )
                result.add(resultGame)
            }

            // Return the list of games
            return result ?: emptyList()

        }
        suspend fun getGameData(): List<Game> {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.igdb.com/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(Api::class.java)

            var gamesResponse = apiService.getGames()
            gamesResponse = gamesResponse.take(10); //prvih 10 igrica

           // var result: MutableList<Game> = mutableListOf();
            //var resultGame: Game = Game(0,"","","",0.0,"","","","","","", listOf());
            // Return the list of games
            return Game2toGame(gamesResponse)
        }

        //radi! samo clean up onaj uraditi
        suspend fun getGamesByName(name: String): List<Game> {  //vraća listu igara koje sadrže string u nazivu sa IGDB api-a
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.igdb.com/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(Api::class.java)
           // val gamesResponse = apiService.getGames()  //ovako prosljedjivati parametar
            val gamesResponse = apiService.searchGames(name=name)
            var result: MutableList<Game2> = mutableListOf()
           /* for (game in gamesResponse) {
                if (game.name.contains(name)) result.add(game)
            }*/
            // Return the list of games
            return Game2toGame(gamesResponse)
        }

        suspend fun getGamesSafe(name: String): List<Game> {
            if(AccountGamesRepository.age == null) return listOf();  //ako age nie postavljen vratite praznu listu
           // println("TESTING")
           // println("Age is" + AccountGamesRepository.age)

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.igdb.com/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(Api::class.java)
            val gamesResponse = apiService.searchGames(name = name)  //ovako prosljedjivati parametar
            val temp = Game2toGame(gamesResponse)
            var result: MutableList<Game> = mutableListOf<Game>()

            for (game in temp) {
                if(game.esrbRating == null) result.add(game)
                else if (game.esrbRating?.toIntOrNull() != null && checkSafe(game.esrbRating.toInt()!!, AccountGamesRepository.age)){
                    println("games:  " + game)
                    result.add(game)
                }

            }
            return result
        }

        fun checkSafe(rating:Int, age:Int?): Boolean{
           // println("I AM CALLED")
            var appropriateAge: Int = 0 //minimum appropriate age - ukljucujuci donju granicu
            //PEGI
           // println("u ckeck safe age je" + age)
            if(rating == 5) appropriateAge = 18;
            if(rating == 4) appropriateAge = 16;
            if(rating == 3) appropriateAge = 12;
            if(rating == 2) appropriateAge = 7;
            if(rating == 1) appropriateAge = 3;
            //ESRB
            if(rating == 6) appropriateAge = 0;  //RP rating pending, valjda svi mogu?
            if(rating == 7) appropriateAge = 3;   //EC - early childhood 3+
            if(rating == 8) appropriateAge = 6;  //E - everyone 6+
            if(rating == 9) appropriateAge = 10;  //E10 - everyone 10+
            if(rating == 10) appropriateAge = 13; //T - teen 13+
            if(rating == 11) appropriateAge = 17;  //M - mature 17+
            if(rating == 12) appropriateAge = 18;  //AO - adults only 18+
            //println("Apprpriate age je " + appropriateAge)
            if (age != null) {
                if(age < appropriateAge) {
                    //println("Age nije null, ali je rating neodgovarajuci vracam false")
                    return false
                }
            } //nije dozvoljeno korisniku da pristupi igri!
           // println("mozete igrati igricu, vracam true!")
            return true   //moze pristupiti igri
        }

        //kako je receno da mozemo implementirati vlatiti interfejs, ja sam sort games koristila samo pri prvom ucitavanju home fragmenta, tj da prikaze izbor nekih pocetnih igrica
        //nisam isti primijenjivala na search metode i sl.
        public suspend fun sortGames(): List<Game> {

            //val account: AccountGamesRepository = AccountGamesRepository();
            val savedGames = AccountGamesRepository.getSavedGames()  //getting the saved games
           val list1 =  savedGames.sortedBy { it.title.lowercase()}  //sortirane spasene
          //  println("sorted saved " + list1)
            val unsavedGames = getGameData() //dobija se 10 nespasenih igrica
            val list2 = unsavedGames.sortedBy {it.title.lowercase()}  //jer dobijam Game, a ne Game2
           // println("sorted rest " + list2)
            val result = list1 + list2  //eventualno 10 prvih uzeti
            return result //vracam spojene dvije liste, obje sortirane, sa spasenim igricama prvim
        }
    }
}