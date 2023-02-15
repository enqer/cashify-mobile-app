package com.example.cashify

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.NavHostFragment

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


        var txtName: TextView = root.findViewById(R.id.fpName)
        var txtIncome: TextView = root.findViewById(R.id.income)
        var txtDate: TextView = root.findViewById(R.id.fpDate)
        var txtContent: TextView = root.findViewById(R.id.fpContent)

        setFragmentResultListener("requestKey") { key, bundle ->
            val name = bundle.getString("n")
            val date = bundle.getString("d")
            val content = bundle.getString("c")
            val balance = bundle.getDouble("b")
            txtName.text=name.toString()
            txtIncome.text=balance.toString()
            txtDate.text=date.toString()
            txtContent.text=content.toString()
        }




        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PersonViewModel::class.java)
        // TODO: Use the ViewModel


    }

}