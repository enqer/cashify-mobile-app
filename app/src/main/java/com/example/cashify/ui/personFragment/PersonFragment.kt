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
import java.lang.Math.ceil
import java.lang.Math.floor


class PersonFragment : Fragment() {

    companion object {
        fun newInstance() = PersonFragment()
    }

    private lateinit var viewModel: PersonViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_person, container, false)


        var picutre: ImageView = root.findViewById(R.id.fpImageView)


        var txtName: TextView = root.findViewById(R.id.fpName)
        var txtIncome: TextView = root.findViewById(R.id.income)
        var txtDate: TextView = root.findViewById(R.id.fpDate)
        var txtContent: TextView = root.findViewById(R.id.fpContent)
        var txtBalance: TextView = root.findViewById(R.id.fpBalance)

        setFragmentResultListener("requestKey") { key, bundle ->
            val name = bundle.getString("n")
            val date = bundle.getString("d")
            val content = bundle.getString("c")
            var balance = bundle.getDouble("b")
            val avatar = bundle.getString("a")


            val s: String = "€"
            if (ceil(balance) == floor(balance)){
                txtBalance.text = balance.toInt().toString() + s
            }else{
                txtBalance.text=balance.toString() + s
            }

            txtName.text=name.toString()
            txtIncome.text=txtBalance.text
            txtDate.text=date.toString()
            txtContent.text=content.toString()


            when(avatar){
                "img1" -> picutre.setImageResource(R.drawable.ic_avatar_bad_breaking)
                "img2" -> picutre.setImageResource(R.drawable.ic_avatar_batman_comics)
                "img3" -> picutre.setImageResource(R.drawable.ic_avatar_dead_monster)
                "img4" -> picutre.setImageResource(R.drawable.ic_avatar_elderly_grandma)
                "img5" -> picutre.setImageResource(R.drawable.ic_avatar_man_muslim)
                "img6" -> picutre.setImageResource(R.drawable.ic_avatar_man_person)
            }
        }





        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PersonViewModel::class.java)
        // TODO: Use the ViewModel

    }

}