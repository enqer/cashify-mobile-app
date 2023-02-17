package com.example.cashify

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cashify.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    var isLoan: Boolean = false

    private lateinit var inputName: EditText
    private lateinit var inputBalance: EditText
    private lateinit var inputContent: EditText
    private lateinit var inputAvatar: ImageButton

    private lateinit var sqLiteManager: SQLiteManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        sqLiteManager = SQLiteManager(this)

        // stuff
//        initView()



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

    fun getPeople(){
        val personList = sqLiteManager.getAllPeople()
        Log.e("test", "${personList.size}")
    }


    fun submitPerson(view: View) {
        inputName = findViewById(R.id.inputName)
        inputBalance = findViewById(R.id.inputBalance)
        inputContent = findViewById(R.id.inputContent)


        val name = inputName.text.toString()
        val balance = inputBalance.text.toString()
        val content = inputContent.text.toString()
        var avatar = ""
        try{
            avatar = inputAvatar.tag.toString()
        }catch (e: Exception){
            avatar = ""
        }


        if (name.isEmpty() || balance.isEmpty() || content.isEmpty() || avatar.isEmpty())
            Toast.makeText(this, "Please enter required information!", Toast.LENGTH_SHORT).show()
        else{
            val formatter = SimpleDateFormat("dd-MM-yyyy")
            val date = Date()
            val current = formatter.format(date)
            val person = Person(name = name, date = current, content = content, balance = balance.toDouble(),avatar = avatar)
            val status = sqLiteManager.insertPerson(person)

            if (status > -1){
                Toast.makeText(this, "Person Added!", Toast.LENGTH_SHORT).show()
                
                // przechodzenie do lewej albo wyczyszczenie inputów
            }
            else{
                Toast.makeText(this, "Record not saved", Toast.LENGTH_SHORT).show()
            }
        }
        getPeople()
    }


    fun addPerson(view: View) {

        if (view.id == R.id.homeBtn1)
            isLoan=true
        else if (view.id == R.id.homeBtn2)
            isLoan=false

        val navHostFragment = supportFragmentManager?.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.navigation_input)

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

    private fun initView(){
        inputName = findViewById(R.id.inputName)
        inputBalance = findViewById(R.id.inputBalance)
        inputContent = findViewById(R.id.inputContent)
//        inputAvatar = findViewById(R.id.inputAvatar)

    }

}