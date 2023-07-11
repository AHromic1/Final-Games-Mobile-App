package ba.etf.rma23.projekat.data.repositories

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AccountApiConfig {

    val baseUrl:String = "https://rma23ws.onrender.com/"

    val retrofit = Retrofit.Builder()
        .baseUrl("https://rma23ws.onrender.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(Api::class.java)

    //ove atribute sam mogla bolje iskoristiti, tako što bih pozivala njihovu instancu umjesto
    //da ponovo dekarisem retrofit u metodama, sto bi dalo kraci i cisci kod
    //medjutim, pri izradi, na pocetku mi je bilo lakse vidjeti citavu metodu odjednom, kako bih otkrila potencijalne greske
    //te sam tako i nastavila raditi kasnije. U ove klase sam dodala atribute, kako bih ispostovala postavku spirale (nije
  // receno da ih moram iskoristiti pozivajuci se na ovu klasu)
}