package com.example.rma23_19084_projekat


import android.content.pm.ActivityInfo
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.PositionAssertions
import androidx.test.espresso.assertion.PositionAssertions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etf.rma23.projekat.GameData
import ba.etf.rma23.projekat.MainActivity
import org.hamcrest.Matchers
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


import org.hamcrest.CoreMatchers.*


/*Ova anotacija definise nacin na koji ce testovi biti pokrenuti*/
@RunWith(AndroidJUnit4::class)

/*Klasa koja sadrzi moje testove u landscape orijentaciji*/

class OwnEspressoTestsLandscape {
    /*Svi scenariji su odabrani tako da vjerodostojno prikazuju funkcionalnosti aplikacije te da ostvare sto vecu pokrivenost
    (testiraju se razlicite funkcionalnosti u oba fragmenta, njihova komunikacija, kao i nacin rada u portrait i landscape orijentaciji).
    Neki detalji glede rasporeda koje su bile testirane na prosloj spirali ovaj put su preskocene (jer bi se fakticki radilo o istom testu),
    tim vise sto u vecini xml fileova nije doslo do znacajne promjene u rasporedu*/

    /*Drugi scenarij
   * Testovi za testiranje raporeda
   * testirat ćemo novi raspored u fragment_game_details, te raspored activity_home u landscape orijentaciji pri prvom otvaranju*/

    /*Prvi test provjerava novi raspored fragment_game_details. Nove promjene u odnosu na proslu spiralu su:
    * release date i genre se nalaze jedno pored drugog, a ispod cover image
    * esrb rating i platform se nalaze jedno pored drugog, a ispod prethodna 2 elementa
    * ispod svih ovih elemenata se nalaze developeri publisher, poravnati jedno uz drugo.
    * takodjer, home i details dugme, kao i logo (po uputstvima s Piazze), su izbrisani*/

    /*Prvo treba da dobijemo xml layout koji zelimo testirati. U ovom slučaju
    to je fragment_game_details, koji je sadrzan u aktivnosti MainActivity
     */

    @get:Rule
    var detailsFragmentRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun toLandscape(){
        detailsFragmentRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE }
    }

    /*S obzirom na to da je fragment_game_details vidljiv u landscape, najprije ćem obrnuti uređaj. Alternativno, test se mogao napisati i
    * tako što bi se, u portrait, nakon klika na igricu iz liste otvorio odgovarajuci fragment, no ovdje sam se odlucila za ranije opisanu metodu.*/

    /*U ovom testu provjeravamo ranije opisani raspored elemenata, tj. da li se odovarajuci elementi nalaze na odgovarajucim pozicijama koristeci
    * metode isCompletely...*/
    @Test
    fun detailsLayoutTest(){
        Espresso.onView(ViewMatchers.withId(R.id.release_date_textview))
            .check(PositionAssertions.isCompletelyLeftOf(ViewMatchers.withId(R.id.genre_textview)))
        Espresso.onView(ViewMatchers.withId(R.id.genre_textview))
            .check(PositionAssertions.isCompletelyRightOf(ViewMatchers.withId(R.id.release_date_textview)))
        Espresso.onView(ViewMatchers.withId(R.id.esrb_rating_textview))
            .check(PositionAssertions.isCompletelyLeftOf(ViewMatchers.withId(R.id.platform_textview)))
        Espresso.onView(ViewMatchers.withId(R.id.platform_textview))
            .check(PositionAssertions.isCompletelyRightOf(ViewMatchers.withId(R.id.esrb_rating_textview)))
        Espresso.onView(ViewMatchers.withId(R.id.developer_textview))
            .check(PositionAssertions.isCompletelyLeftOf(ViewMatchers.withId(R.id.publisher_textview)))
        Espresso.onView(ViewMatchers.withId(R.id.publisher_textview))
            .check(PositionAssertions.isCompletelyRightOf(ViewMatchers.withId(R.id.developer_textview)))
        Espresso.onView(ViewMatchers.withId(R.id.description_textview))
            .check(PositionAssertions.isCompletelyBelow(ViewMatchers.withId(R.id.publisher_textview)))
        Espresso.onView(ViewMatchers.withId(R.id.comments_list))
            .check(PositionAssertions.isCompletelyBelow(ViewMatchers.withId(R.id.description_textview)))
        Espresso.onView(ViewMatchers.withId(R.id.cover_imageview))
            .check(PositionAssertions.isCompletelyAbove(ViewMatchers.withId(R.id.release_date_textview)))

        //malo drukcija sintaksa je koristena za testiranje pozicije item_title_textview, jer postoji vise elemenata s istim id-em,
        //u istom prikazu (u listi igara i u detaljima o igri)
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.item_title_textview),
                ViewMatchers.withParent(ViewMatchers.withId(R.id.detailsParent))
            )
        ).check(PositionAssertions.isCompletelyAbove(withId(R.id.cover_imageview)))
    }


    /*Drugi test provjerava da li su potrebni element prikazani u landscape mode, pri prvom otvaranju:
    * treba da bude vidljiva lista igrica
    * treba da budu prikazani detalji prve igrice
   */


    /*U ovom testu provjeravamo ranije opisani raspored elemenata, tj. da li se elementi poklapaju sa ocekivanim vrijednostima te da li su prikazani,
    * pomocu metoda poput matches, with text, isDisplayed, i sl.*/
    @Before
    fun toLandscape2(){
        detailsFragmentRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE }
    }
    @Test
    fun landscapeLayoutTest() {
        //Da li je lista prikazana?
        Espresso.onView(ViewMatchers.withId(R.id.game_list))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        //da li su detalji PRVE igre prikazani (provjeravamo detalje za title "GTA")?
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.item_title_textview),
                ViewMatchers.withParent(ViewMatchers.withId(R.id.detailsParent))
            )
        ).check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[0].title)))

        Espresso.onView(ViewMatchers.withId(R.id.release_date_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[0].releaseDate)))
        Espresso.onView(ViewMatchers.withId(R.id.genre_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[0].genre)))
        Espresso.onView(ViewMatchers.withId(R.id.esrb_rating_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[0].esrbRating)))
        Espresso.onView(ViewMatchers.withId(R.id.platform_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[0].platform)))
        Espresso.onView(ViewMatchers.withId(R.id.developer_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[0].developer)))
        Espresso.onView(ViewMatchers.withId(R.id.publisher_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[0].publisher)))
        Espresso.onView(ViewMatchers.withId(R.id.description_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[0].description)))
        Espresso.onView(ViewMatchers.withId(R.id.comments_list))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    //NAPOMENA: iako su se ova 2 testa mogla sastaviti u jedan, zbog testiranja potpuno razlicitih funkcionalnosti te radi preglednosti i logike razdvojeni su
    //kraj drugog scenarija

    /*Treci scenarij: testiranje odabira razlicitih igrica u landscape mode
    * klikom na igricu iz liste u details fragmentu vidimo detalje te igrice prikazane na istom ekranu
    * testirat cemo 3 razlicite, proizvoljno odabrane igrice (ekvivalentno radi i za svaku drugu)
    * koristit cemo funkcije poput: matches, check, click, withId da bismo simulirali potrebne akcije*/

    //bilo je dovoljno koristiti before anotaciju samo na pocetku klase
    @Before
    fun toLandscape3(){
        detailsFragmentRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE }
    }

    @Test
    fun landscapeFunctionalitiesTest() {
        //krecuci se redom kroz listu, biramo 2 proizvoljne igrice sa sredine liste, kao predstavnike prosjecnog slucaja
        Espresso.onView(ViewMatchers.withId(R.id.game_list)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5),
            ViewActions.click()
        )
        //da li su detalji odgovarajuce igre prikazani?
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.item_title_textview),
                ViewMatchers.withParent(ViewMatchers.withId(R.id.detailsParent))
            )
        ).check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[5].title)))

        Espresso.onView(ViewMatchers.withId(R.id.release_date_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[5].releaseDate)))
        Espresso.onView(ViewMatchers.withId(R.id.genre_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[5].genre)))
        Espresso.onView(ViewMatchers.withId(R.id.esrb_rating_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[5].esrbRating)))
        Espresso.onView(ViewMatchers.withId(R.id.platform_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[5].platform)))
        Espresso.onView(ViewMatchers.withId(R.id.developer_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[5].developer)))
        Espresso.onView(ViewMatchers.withId(R.id.publisher_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[5].publisher)))
        Espresso.onView(ViewMatchers.withId(R.id.description_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[5].description)))

        Espresso.onView(ViewMatchers.withId(R.id.game_list)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(8),
            ViewActions.click()
        )
        //da li su detalji odgovarajuce igre prikazani?
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.item_title_textview),
                ViewMatchers.withParent(ViewMatchers.withId(R.id.detailsParent))
            )
        ).check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[8].title)))

        Espresso.onView(ViewMatchers.withId(R.id.release_date_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[8].releaseDate)))
        Espresso.onView(ViewMatchers.withId(R.id.genre_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[8].genre)))
        Espresso.onView(ViewMatchers.withId(R.id.esrb_rating_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[8].esrbRating)))
        Espresso.onView(ViewMatchers.withId(R.id.platform_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[8].platform)))
        Espresso.onView(ViewMatchers.withId(R.id.developer_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[8].developer)))
        Espresso.onView(ViewMatchers.withId(R.id.publisher_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[8].publisher)))
        Espresso.onView(ViewMatchers.withId(R.id.description_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[8].description)))

        //biramo posljednju igricu na listi, jer predstavlja neku vrstu granicnog, ekstremnog slucaja, koje je uvijek neophodno testirati
        Espresso.onView(ViewMatchers.withId(R.id.game_list)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(GameData.getAll().size-1),
            ViewActions.click()
        )
        //da li su detalji odgovarajuce igre prikazani? (u biti, dovoljno je provjeriti samo title jer je jedinstven
        // za svaku igricu, no ovdje cemo ipak, zbog bolje pokrivenost, provjeravati i druge najrelevantnije elemente)
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.item_title_textview),
                ViewMatchers.withParent(ViewMatchers.withId(R.id.detailsParent))
            )
        ).check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[(GameData.getAll().size-1)].title)))
        Espresso.onView(ViewMatchers.withId(R.id.release_date_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[(GameData.getAll().size - 1)].releaseDate)))
        Espresso.onView(ViewMatchers.withId(R.id.genre_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[(GameData.getAll().size - 1)].genre)))
        Espresso.onView(ViewMatchers.withId(R.id.esrb_rating_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[(GameData.getAll().size - 1)].esrbRating)))
        Espresso.onView(ViewMatchers.withId(R.id.platform_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[(GameData.getAll().size - 1)].platform)))
        Espresso.onView(ViewMatchers.withId(R.id.developer_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[(GameData.getAll().size - 1)].developer)))
        Espresso.onView(ViewMatchers.withId(R.id.publisher_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[(GameData.getAll().size - 1)].publisher)))
        Espresso.onView(ViewMatchers.withId(R.id.description_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[(GameData.getAll().size - 1)].description)))
    }
    //kraj treceg scenarija

    //vracamo orijentaciju na portrait, zbog eventualnih drugih testova, s obzirom na to da je to default
    @After
    fun toPortrait(){
        detailsFragmentRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT }
    }

}