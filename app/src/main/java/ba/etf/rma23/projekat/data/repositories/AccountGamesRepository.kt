package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AccountGamesRepository //: Application() //provjeriti smijem li ovo
{
    companion object {
        private var hash: String = "";
        var saved: MutableList<Game>? = null
         var age: Int? = null


        suspend fun getGameTest(): List<Game2> {
            var idIgdb: Int
            var completeGame: Game2
            val retrofit2 = Retrofit.Builder()
                .baseUrl("https://api.igdb.com/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService2 = retrofit2.create(Api::class.java)
            return apiService2.getGame(name = "Dotra")
        }

        fun setHash(acHash: String): Boolean {//- postavlja (lokalno, ne putem web servisa) hash korisnika koji Ä‡e biti koriÅ¡ten
// za identifikovanje accounta, vraÄ‡a true ukoliko je hash postavljen, false inaÄe.
            hash = acHash;
            if (hash != null) return true

            return false //ukoliko hash nije postavljen, i dalje je null, dobit cemo false
        }

        fun getHash(): String   //PROVJERITI smije li se definisati sa String? !!!
        {//- vraÄ‡a hash korisnika koji je postavljen
            return hash;
        }

        suspend fun getSavedGames(): List<Game> {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://rma23ws.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(Api::class.java)
            val gamesResponse =
                apiService.savedGames() //ovako prosljedjivati parametar
            var games: MutableList<Game2> = mutableListOf();
            // null //null lista zasad, jer mora biti initialized

            var idIgdb: Int
            var completeGame: Game2
            val retrofit2 = Retrofit.Builder()
                .baseUrl("https://api.igdb.com/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService2 = retrofit2.create(Api::class.java)
            for (game in gamesResponse) {

                completeGame = apiService2.getGame(name = game.name)[0]  //prva vracena igrica
               // println("complete_game name" + completeGame.name)
                games.add(completeGame)


            }

            // Return the list of games
            return GamesRepository.Game2toGame(games)
        }

        suspend fun saveGame(game: Game): Game {
            if(savedContains(game)) return game;  //vec je spasena
            //dodati da vraca null ako je neuspjesno spasavanje
            val retrofit = Retrofit.Builder()
                .baseUrl("https://rma23ws.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val gameForSaving: SavedGames = SavedGames(
                game.id,
                game.title
            )   //pravim objekat saved games preuzimajuci odatke iz ulazne game
            println("this is the game i wish to save: "  + gameForSaving);
            val gameWrapper = GameWrapper(gameForSaving)
            println("OVO JE GAME KOJA CE BITI SPASENA" + gameWrapper);
            val apiService = retrofit.create(Api::class.java)
            apiService.saveGame(gameWrapper = gameWrapper);  //spasavam igricu
            // val savedGamesList = apiService.savedGames()  //trazim listu spasenih igrica
            saved?.add(game)  //dodajem u listu spasenih igrica
            return game;  //valjda vracam igricu koju sam dodala?
        }

        suspend fun savedContains(game: Game): Boolean{
            var savedGames = getSavedGames()
            for(element in savedGames){
                if(game == element) return true //sadrzi igricu
            }
            return false
        }


        suspend fun logIn() {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://rma23ws.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(Api::class.java)
            val gamesResponse = apiService.login()
        }

        suspend fun removeGame(id: Int): Boolean {  //kada treba vratiti true, kada false?
            if (saved?.size == 0) {
                return false;
            } //lista spasenih igrica je prazna, nema se sta izbrisati!

            val retrofit = Retrofit.Builder()
                .baseUrl("https://rma23ws.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(Api::class.java)

            val delete = apiService.deleteGame(gid = id)
            //deletion is successful
            return true;
        }

        suspend fun removeNonSafe(): Boolean {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://rma23ws.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(Api::class.java)
            var deleted: Boolean = false
            val savedGames = getSavedGames()
            //prolazim kroz listu i za svaku igricu trazim njen age rating, ako je ispod, onda mogu onu delete funkciju iskoristiti
            if (savedGames != null)
                for (game in savedGames) {  //!! je not null assertion
                if (game.esrbRating == null) continue  //igrica je safe, prema upustvima s Piazze
                else if (game.esrbRating?.toIntOrNull() != null && GamesRepository.checkSafe(game.esrbRating.toInt(), age)==false) {
                    apiService.deleteGame(gid = game.id)
                    //println("i have deleted the following game: " + game.title)
                    deleted = true
                }
            }
            if (deleted) return true
            return false  //there was nothing to delete - moze?
        }

        //u postavci spirale pise da su godine u rasponu od 3 do 100, te sam pretpostavila da se 3 i 100 ukljucuju
        fun setAge(age: Int): Boolean {
            if (age >= 3 && age <= 100) {
                this.age = age
                return true
            }
            return false
        }

        //po upustvu s piazze, ova metoda je case insensitive
        suspend fun getGamesContainingString(query: String): List<Game> {
            val result: MutableList<Game> = mutableListOf()
            val saved: List<Game> = getSavedGames()
            val lowercaseQuery = query.lowercase()
            for (game in saved) {
                if (game.title.lowercase().contains(lowercaseQuery)) {
                    result.add(game)
                }
            }
            return result;
        }

        suspend fun getOneGame(title: String): Game {

            var completeGame: List<Game2>
            val retrofit2 = Retrofit.Builder()
                .baseUrl("https://api.igdb.com/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService2 = retrofit2.create(Api::class.java)

            completeGame = apiService2.getGame(name = title)  //prva vracena igrica
            return GamesRepository.Game2toGame(completeGame)[0]
            //println("complete_game name" + completeGame.name)

        }
    }
}