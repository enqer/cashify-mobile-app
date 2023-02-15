package com.example.cashify.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cashify.Person
import com.example.cashify.PersonAdapter
import com.example.cashify.R
import com.example.cashify.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment(), PersonAdapter.OnItemClickListener{
    private lateinit var mRecyclerView : RecyclerView
    private lateinit var mAdapter : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>
    private lateinit var mLayoutManager : RecyclerView.LayoutManager

    var persons = ArrayList<Person>()
    var idPersons = mutableListOf<String>()

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        getPersons()




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {
//        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()
        val clickedItem: Person = persons[position]
        clickedItem.name = "Clciedk"
        mAdapter.notifyItemChanged(position)
    }

    fun getPersons(){
        val o = Person("Pawel","14.02.2023","za pizze", 21.37)
        persons.add(Person("Pawel","14.02.2023","colka", 21.37))
        mRecyclerView = binding.root.findViewById(R.id.recyclerView)
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        mAdapter = PersonAdapter(persons, this)
        mRecyclerView.layoutManager=mLayoutManager
        mRecyclerView.adapter = mAdapter
    }
}