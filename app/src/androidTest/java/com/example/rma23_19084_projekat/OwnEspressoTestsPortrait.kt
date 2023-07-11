package com.example.rma23_19084_projekat

import android.content.pm.ActivityInfo
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.PositionAssertions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etf.rma23.projekat.GameData
import ba.etf.rma23.projekat.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/*Ova anotacija definise nacin na koji ce testovi biti pokrenuti*/
@RunWith(AndroidJUnit4::class)

/*Klasa koja sadrzi moje testove u portrait orijentaciji*/

class OwnEspressoTestsPortrait {

    /*Svi scenariji su odabrani tako da vjerodostojno prikazuju funkcionalnosti aplikacije te da ostvare sto vecu pokrivenost
   (testiraju se razlicite funkcionalnosti u oba fragmenta, njihova komunikacija, kao i nacin rada u portrait i landscape orijentaciji).
   Neki detalji glede rasporeda koje su bile testirane na prosloj spirali ovaj put su preskocene (jer bi se fakticki radilo o istom testu),
    tim vise sto u vecini xml fileova nije doslo do znacajne promjene u rasporedu*/

    /*Prvi scenarij: provjerava raspored kao i funkcionalnosti u portrait mode (ove dvije stavke cemo provjeriti zajedno, u jednom testu):
  * na home ekranu treba da je vidljiva lista igrica, kao i search polje i search button iznad nje
  * na dnu treba da se nalazi bottom navigation
  * home i details dugme treba da su disabled
  * nakon klika na igricu iz liste otvara se details ekran
  * detalji odabrane igrice su prikazani
  * home dugme je enabled, details disabled
  * nakon klika na home dugme home ekran je ponovo prikazan
  * home dugme je disabled, details enabled
  * nakon klika na details dugme vracamo se na zadnje odabranu igricu
  * koristene metode su isCompletelyAbove/Rightof (provjerava da li se neki element nalazi iznad/desno od drugog elementa)
  * matches, click() u kombinaciji s metodama poput withId za pronalazak elementa po id-u, withText za poredjenje po tekstualnom sadrzaju...
 */

    @get:Rule
    var detailsFragmentRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)
    @Before
    fun toPortrait(){
        detailsFragmentRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT }
    }

    @Test
    fun portraitTest(){
        //mada vecina uredjaja zapocinje u portrait orijentaciji, za svaki slucaj cemo postaviti odgovarajucu orijentaciju:
        /* detailsFragmentRule.scenario.onActivity { activity ->
             if (!activity.isDestroyed) {
                 activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
             }
         }*/
        //Provjera rasporeda elemenata na home ekranu koristeci razliciti check i matches metode
        Espresso.onView(ViewMatchers.withId(R.id.logo_image))
            .check(PositionAssertions.isCompletelyAbove(ViewMatchers.withId(com.google.android.material.R.id.search_button)))
        Espresso.onView(ViewMatchers.withId(R.id.search_query_edittext))
            .check(PositionAssertions.isCompletelyAbove(ViewMatchers.withId(com.google.android.material.R.id.barrier)))
        Espresso.onView(ViewMatchers.withId(R.id.search_button))
            .check(PositionAssertions.isCompletelyAbove(ViewMatchers.withId(com.google.android.material.R.id.barrier)))
        Espresso.onView(ViewMatchers.withId(R.id.search_button))
            .check(PositionAssertions.isCompletelyRightOf(ViewMatchers.withId(R.id.search_query_edittext)))
        Espresso.onView(ViewMatchers.withId(R.id.game_list))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))

        //da li su home i details navigacija vidljivi i onemoguceni?

        Espresso.onView(ViewMatchers.withId(R.id.homeItem))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.gameDetailsItem)).check(
            ViewAssertions.matches(
                ViewMatchers.isCompletelyDisplayed()
            )
        )

        Espresso.onView(ViewMatchers.withId(R.id.homeItem)).check(
            ViewAssertions.matches(
                ViewMatchers.isNotEnabled()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.gameDetailsItem))
            .check(ViewAssertions.matches(ViewMatchers.isNotEnabled()))

        //scrollamo do proizvoljno i nasumicno odabrane igrice (u ovom slucaju AmongUs na poziciji 3) pomocu scrollToPosition i pozivamo funkciju
        //click() kako bi se simulirao odabir igrice
        Espresso.onView(ViewMatchers.withId(R.id.game_list)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                3
            ), ViewActions.click()
        )

        //da li su detalji ispravne igre prikazani?
        Espresso.onView(ViewMatchers.withId(R.id.item_title_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[3].title)))
        Espresso.onView(ViewMatchers.withId(R.id.release_date_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[3].releaseDate)))
        Espresso.onView(ViewMatchers.withId(R.id.genre_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[3].genre)))
        Espresso.onView(ViewMatchers.withId(R.id.esrb_rating_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[3].esrbRating)))
        Espresso.onView(ViewMatchers.withId(R.id.platform_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[3].platform)))
        Espresso.onView(ViewMatchers.withId(R.id.developer_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[3].developer)))
        Espresso.onView(ViewMatchers.withId(R.id.publisher_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[3].publisher)))
        Espresso.onView(ViewMatchers.withId(R.id.description_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[3].description)))

        // da li je home sada omogucen, a details ne (provjeravamo pomocu isEnabled i isNotEnabled funkcije)?
        Espresso.onView(ViewMatchers.withId(R.id.homeItem)).check(
            ViewAssertions.matches(
                ViewMatchers.isEnabled()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.gameDetailsItem))
            .check(ViewAssertions.matches(ViewMatchers.isNotEnabled()))

        //klikom na home u bottom navigation se vracamo na home fragment
        Espresso.onView(ViewMatchers.withId(R.id.homeItem)).perform(ViewActions.click())
        //sada je details dugme omoguceno, a home nije
        Espresso.onView(ViewMatchers.withId(R.id.homeItem)).check(
            ViewAssertions.matches(
                ViewMatchers.isNotEnabled()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.gameDetailsItem)).check(
            ViewAssertions.matches(
                ViewMatchers.isEnabled()
            )
        )

        //provjeravamo da li je game list prikazan (jer se sada nalazimo u home fragment-u)
        Espresso.onView(ViewMatchers.withId(R.id.game_list))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))

        //klikom na details odlazimo u details fragment, gdje bi ponovo detalji AmongUs trebali biti prikazani
        Espresso.onView(ViewMatchers.withId(R.id.gameDetailsItem)).perform(ViewActions.click())
        //provjeravamo da li su detalji odgovarajuce igrice prikazani
        Espresso.onView(ViewMatchers.withId(R.id.item_title_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[3].title)))
        Espresso.onView(ViewMatchers.withId(R.id.release_date_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[3].releaseDate)))
        Espresso.onView(ViewMatchers.withId(R.id.genre_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[3].genre)))
        Espresso.onView(ViewMatchers.withId(R.id.esrb_rating_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[3].esrbRating)))
        Espresso.onView(ViewMatchers.withId(R.id.platform_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[3].platform)))
        Espresso.onView(ViewMatchers.withId(R.id.developer_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[3].developer)))
        Espresso.onView(ViewMatchers.withId(R.id.publisher_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[3].publisher)))
        Espresso.onView(ViewMatchers.withId(R.id.description_textview))
            .check(ViewAssertions.matches(ViewMatchers.withText(GameData.getAll()[3].description)))
    }
    //kraj prvog scenarija
}