package com.example.cashify


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import java.util.zip.Inflater


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
//        var txtDate: TextView = root.findViewById(R.id.fpDate)
//        var txtContent: TextView = root.findViewById(R.id.fpContent)

        setFragmentResultListener("requestKey") { key, bundle ->
            val name = bundle.getString("n")
            val date = bundle.getString("d")
            val content = bundle.getString("c")
            val balance = bundle.getDouble("b")
            txtName.text=name.toString()
            txtIncome.text=balance.toString()
//            txtDate.text=date.toString()
//            txtContent.text=content.toString()
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