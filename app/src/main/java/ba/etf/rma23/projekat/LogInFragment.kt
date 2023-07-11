package ba.etf.rma23.projekat

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import com.example.rma23_19084_projekat.R

class LogInFragment: Fragment() {

    private lateinit var logInButton: Button
    private lateinit var ageText: EditText
    private lateinit var hashText: EditText
    var account:AccountGamesRepository = AccountGamesRepository()

    override fun onCreateView(  //kreiramo pogled za fragment
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(
            R.layout.fragment_login, container,  //inflateamo odgovarajuci xm file
            false  //ovo ce android da odradi za nas
        )

       logInButton = view.findViewById(R.id.login) //moram mu pristupiti preko view koji sam kreirala u onCreateView
        ageText = view.findViewById(R.id.age_edittext)
        hashText = view.findViewById(R.id.hash_edittext)



        logInButton.setOnClickListener {


            AccountGamesRepository.setHash(hashText.text.toString())
            AccountGamesRepository.setAge(ageText.text.toString().toInt())
           //AccountGamesRepository.setAge()  moze i ovako
            //u postavci nije navedena metoda getAge, te sam atribut napravila javnim
//println("AGE OF USER IS " + AccountGamesRepository.age);
            val sharedPreferences =
                context?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
            val keeper = sharedPreferences?.edit()
            if (keeper != null) {
                keeper.putString("hash", hashText.text.toString())
                keeper.putInt("age", ageText.text.toString().toInt())
                keeper.apply()
            }

            findNavController().navigate(
                R.id.homeFragment
            )
        }

        return view
    }
}