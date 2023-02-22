package com.example.cashify.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cashify.R
import com.example.cashify.SQLiteManager
import com.example.cashify.databinding.FragmentHomeBinding
import kotlin.math.roundToInt

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private lateinit var sqLiteManager: SQLiteManager

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        sqLiteManager = SQLiteManager(this.requireContext())

        val homeLentTo: TextView = root.findViewById(R.id.homeLentTo)
        val homeLentFrom: TextView = root.findViewById(R.id.homeLentFrom)

        var lentTo = sqLiteManager.sumBalance("table_cashify")
        var lentFrom = sqLiteManager.sumBalance("table_cashify_not")

        lentTo = (lentTo * 100.0).roundToInt() / 100.0
        lentFrom = (lentFrom * 100.0).roundToInt() / 100.0

        if (Math.ceil(lentFrom) == Math.floor(lentFrom)){
            homeLentFrom.text = lentFrom.toInt().toString() + "€"
        }else{
            homeLentFrom.text = lentFrom.toString() + "€"
        }

        if (Math.ceil(lentTo) == Math.floor(lentTo)){
            homeLentTo.text = lentTo.toInt().toString() + "€"
        }else{
            homeLentTo.text = lentTo.toString() + "€"
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}