package ba.etf.rma23.projekat

class GameData {
    /*ne moram kreirati instancu klase zahvajujuci companion object, djeluje kao static klasa*/
    companion object{
        fun getAll(): List<Game>{
            return listOf(
                Game(0,"Grand Theft Auto V", "PS3 Xbox","1.1.2013.",3.5,"android.resource://com.example.rma23_19084_projekat/drawable/gta", "M", "Rockstar Games",
                    "Rockstar Games", "Action",
                    "When a young street hustler, a retired bank robber, and a terrifying psychopath find themselves entangled with some " +
                            "of the most frightening and deranged elements of the criminal underworld, the U.S. Government, and the entertainment industry, " +
                            "they must pull off a series of dangerous heists to survive in a ruthless city ...",
                    listOf(
                        UserRating("John", 23,2.4), UserRating("Gamer_123", 50,3.5),
                        UserReview("Jaslyn Jones",34, "Not a bad game."),  UserReview("Adam Smith",32, "Got bored after an hour. But the graphics is seriously good"),
                        UserRating("GTAWizard", 23,4.9)
                    )
                ),

                Game(2,"Fortnite", "Play Station 4","21.7.2017.",4.0,"android.resource://com.example.rma23_19084_projekat/drawable/fortnite", "M", "Epic Games",
                    "Warner Bros. Entertainment", "survival",
                    "Fortnite is a survival game where 100 players fight against each other in player versus player combat to be the last one standing. " +
                            "It is a fast-paced, action-packed game, not unlike The Hunger Games, where strategic thinking is a must in order to survive..",
                    listOf(
                        UserReview("Alicia Holmes", 4,"I loved it! From the costumes to the plot, everything was amazing."), UserRating("Disappointed", 30,1.0),
                        UserReview("H_Gamer",34, "It couldn't load..."),  UserReview("Adam Smith",32, "It's a good game, but a bit overrated I would say."),
                        UserRating("A.H.", 23,4.5)
                    )),
                Game(3,"Minecraft", "Windows","18.10.2011.",4.9,"android.resource://com.example.rma23_19084_projekat/drawable/minecraft", "T", "Mojang",
                    "Telltale Games", "Sandbox survival",
                    "In Minecraft, players explore a blocky, procedurally generated, three-dimensional world with virtually infinite terrain and may discover and extract raw materials, " +
                            "craft tools and items, and build structures, earthworks, and machines.",
                    listOf()),
                Game(4,"AmongUs", "Android","18.7.2018.",5.0,"android.resource://com.example.rma23_19084_projekat/drawable/amongus", "T", "Innersloth",
                    "Innersloth", "Social deduction",
                    "The game splits players into two camps: crewmates and impostors. For crewmates, the objective of the game is to complete a series of tasks and survive. Impostors, on the other hand, " +
                            "want to kill most of the crewmates on board; once there's an equal number of impostors and crewmates left alive, the impostors win.",
                    listOf()),
                Game(5,"Mario Kart 8", "Wii U","4.5.2022.",2.1,"android.resource://com.example.rma23_19084_projekat/drawable/mario_kart", "F", "Nintendo",
                    "Nintendo", "Racing",
                    "Mario Kart Wii is a kart racing game featuring single-player and multiplayer modes. The players control one of many selectable Mario franchise characters and participate in races or" +
                            " battles using go-karts or bikes on courses thematically based on locations from the Mario franchise, this time with new characters!",
                    listOf()),
                Game(6,"Mario Kart 7", "Wii U","2.11.2020.",1.9,"android.resource://com.example.rma23_19084_projekat/drawable/mario_kart", "F", "Nintendo",
                    "Nintendo", "Racing",
                    "Mario Kart Wii is a kart racing game featuring single-player and multiplayer modes. The players control one of many selectable Mario franchise characters and participate in races or" +
                            " battles using go-karts or bikes on courses thematically based on locations from the Mario franchise.",
                    listOf()),
                Game(7,"FIFA 22", "Nintendo switch","27.9.2021.",3.7,"android.resource://com.example.rma23_19084_projekat/drawable/fifa", "F", "EA Romania",
                    "Electronic Arts", "Sport",
                    "FUT 22 redesigns Division Rivals and FUT Champions to create a more accessible way to test your skills and progress against other players, gives you even more ways to make your club" +
                            " your own with new depths of customisation both on and off the pitch, and introduces FUT Heroes.",
                    listOf()),
                Game(8,"Tetris", "iOS","6.3.1984.",3.9,"android.resource://com.example.rma23_19084_projekat/drawable/tetris", "R", "SoftAcademy",
                    "PlayStudios", "Puzzle",
                    "Inspired by his favorite puzzle board game, Pentominos, Pajitnov created an electronic game that let players arrange puzzle pieces in real time as they fell " +
                            "from the top of the playing field. ",
                    listOf()),
                Game(9,"Far Cry 6", "Microsoft","23.3.2004.",2.8,"android.resource://com.example.rma23_19084_projekat/drawable/far_cry", "M", "Ubisoft",
                    "Ubisoft", "First-person shooter",
                    "The game is set on a mysterious archipelago in Micronesia, where ex-special forces operative Jack Carver must use various weapons, tools, and his surroundings " +
                            "to survive against hostile mercenaries that control the islands while searching for the journalist who was accompanying him and who went missing. ",
                    listOf()),
                Game(10,"Call of Duty", "XONE","29.9.2003.",4.2,"android.resource://com.example.rma23_19084_projekat/drawable/call_of_duty", "M", "InfinityWard",
                    "Activision", "First-person shooter",
                    "Call of Duty follows the exploits of three soldiers fighting for three different nations. The American campaign features Private Martin," +
                            " Baker Company's sniper, demolitions expert, intel collector, and pathfinder.",
                    listOf())
            )
        }

        fun getDetails(title:String): Game {
                val games: List<Game> = getAll()

                games.forEach{
                    if(it.title==title) return it
                }
            return Game(1,"","","",0.0,"","","","","","", listOf())
            //ne da return null !!!


        }
    }
}