package com.example.cashify.ui.personFragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.example.cashify.R


class PersonFragment : Fragment() {

    companion object {
        fun newInstance() = PersonFragment()
    }

    private lateinit var viewModel: PersonViewModel

//    lateinit var name: String
//    lateinit var date: String
//    lateinit var content: String
//    var balance: Double = 0.0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_person, container, false)

//        val bundle = this.arguments
//        if (bundle != null){
//            name = bundle.get("name") as String
//            date = bundle.get("date") as String
//            content = bundle.get("content") as String
//            balance = bundle.get("balance") as Double
//            Log.d("te",date)
//            Log.d("te","tetete")
//
//        }
        var picutre: ImageView = root.findViewById(R.id.fpImageView)


        var txtName: TextView = root.findViewById(R.id.fpName)
        var txtIncome: TextView = root.findViewById(R.id.income)
//        var txtDate: TextView = root.findViewById(R.id.fpDate)
//        var txtContent: TextView = root.findViewById(R.id.fpContent)

        setFragmentResultListener("requestKey") { key, bundle ->
            val name = bundle.getString("n")
            val date = bundle.getString("d")
            val content = bundle.getString("c")
            val balance = bundle.getDouble("b")
            val avatar = bundle.getString("a")

            txtName.text=name.toString()
            txtIncome.text=balance.toString()
//            txtDate.text=date.toString()
//            txtContent.text=content.toString()

            when(avatar){
                "img1" -> picutre.setImageResource(R.drawable.ic_avatar_bad_breaking)
                "img2" -> picutre.setImageResource(R.drawable.ic_avatar_batman_comics)
                "img3" -> picutre.setImageResource(R.drawable.ic_avatar_dead_monster)
                "img4" -> picutre.setImageResource(R.drawable.ic_avatar_elderly_grandma)
                "img5" -> picutre.setImageResource(R.drawable.ic_avatar_man_muslim)
                "img6" -> picutre.setImageResource(R.drawable.ic_avatar_man_person)
            }
        }



//        val scroll = root.findViewById(R.id.) as ScrollView
//        val scroll: ScrollView = root.
//        val inflater = getSystemService<Any>(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
//        val view: View = inflater.inflate(R.layout.tile, null)
//        scroll.addView(view)
        //TODO dynamic add to scrollView


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PersonViewModel::class.java)
        // TODO: Use the ViewModel

    }

}