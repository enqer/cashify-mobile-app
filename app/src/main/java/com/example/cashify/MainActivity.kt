package com.example.cashify

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import android.window.SplashScreen
import androidx.annotation.RequiresApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.isEmpty
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cashify.databinding.ActivityMainBinding
import com.example.cashify.ui.home.HomeFragment
import com.example.cashify.ui.inputData.inputFragment
import com.example.cashify.ui.personFragment.PersonFragment
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    var isLoan: Boolean = false

    private lateinit var inputName: EditText
    private lateinit var inputBalance: EditText
    private lateinit var inputContent: EditText
    private lateinit var inputAvatar: ImageButton
    private lateinit var radioGroup: RadioGroup

    private lateinit var sqLiteManager: SQLiteManager


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        sqLiteManager = SQLiteManager(this)



        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }


    fun submitPerson(view: View) {
        inputName = findViewById(R.id.inputName)
        inputBalance = findViewById(R.id.inputBalance)
        inputContent = findViewById(R.id.inputContent)
        radioGroup = findViewById(R.id.radioGroup)

        val idRadio = radioGroup.checkedRadioButtonId
        val name = inputName.text.toString()
        val balance = inputBalance.text.toString()
        val content = inputContent.text.toString()
        var avatar = ""
        try{
            avatar = inputAvatar.tag.toString()
        }catch (e: Exception){
            avatar = ""
        }


        if (name.isEmpty() || balance.isEmpty() || content.isEmpty() || avatar.isEmpty() || idRadio == -1)
            Toast.makeText(this, "Please enter required information!", Toast.LENGTH_SHORT).show()
        else{
            //date
            val formatter = SimpleDateFormat("dd-MM-yyyy")
            val date = Date()
            val current = formatter.format(date)
            //parsing double
            var balanceDouble = (balance.toDouble() * 100.0).roundToInt() / 100.0


            val person = Person(name = name, date = current, content = content, balance = balanceDouble,avatar = avatar)
            val tableName: String
            if (idRadio == findViewById<RadioButton>(R.id.inputRadioBtn1).id){
                tableName = "table_cashify"
            }else {
                tableName = "table_cashify_not"
            }

            val status = sqLiteManager.insertPerson(person,tableName)

            if (status > -1){
                Toast.makeText(this, "Person Added!", Toast.LENGTH_SHORT).show()
                
                // przechodzenie do lewej albo wyczyszczenie inputów
            }
            else{
                Toast.makeText(this, "Record not saved", Toast.LENGTH_SHORT).show()
            }
//            parentFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_main, PersonFragment()).commit()
            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_main, HomeFragment()).commit()

        }
    }



    fun addPerson(view: View) {

        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_main, inputFragment()).commit()

    }
    fun selectImg(view: View){
        val imageButton1: ImageButton = findViewById(R.id.imageButton1)
        imageButton1.setBackgroundColor(Color.WHITE)
        val imageButton2: ImageButton = findViewById(R.id.imageButton2)
        imageButton2.setBackgroundColor(Color.WHITE)
        val imageButton3: ImageButton = findViewById(R.id.imageButton3)
        imageButton3.setBackgroundColor(Color.WHITE)
        val imageButton4: ImageButton = findViewById(R.id.imageButton4)
        imageButton4.setBackgroundColor(Color.WHITE)
        val imageButton5: ImageButton = findViewById(R.id.imageButton5)
        imageButton5.setBackgroundColor(Color.WHITE)
        val imageButton6: ImageButton = findViewById(R.id.imageButton6)
        imageButton6.setBackgroundColor(Color.WHITE)

        if (view.id == imageButton1.id)
            imageButton1.setBackgroundColor(Color.parseColor("#545587"))
        else if (view.id == imageButton2.id)
            imageButton2.setBackgroundColor(Color.parseColor("#545587"))
        else if (view.id == imageButton3.id)
            imageButton3.setBackgroundColor(Color.parseColor("#545587"))
        else if (view.id == imageButton4.id)
            imageButton4.setBackgroundColor(Color.parseColor("#545587"))
        else if (view.id == imageButton5.id)
            imageButton5.setBackgroundColor(Color.parseColor("#545587"))
        else if (view.id == imageButton6.id)
            imageButton6.setBackgroundColor(Color.parseColor("#545587"))

        inputAvatar = findViewById(view.id)
    }

}