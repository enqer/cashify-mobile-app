package com.example.cashify.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cashify.Person
import com.example.cashify.PersonAdapter
import com.example.cashify.R
import com.example.cashify.SQLiteManager
import com.example.cashify.databinding.FragmentDashboardBinding
import java.text.SimpleDateFormat
import java.util.*

class DashboardFragment : Fragment(), PersonAdapter.OnItemClickListener{
    private lateinit var mRecyclerView : RecyclerView
    private lateinit var mAdapter : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>
    private lateinit var mLayoutManager : RecyclerView.LayoutManager

    var persons = ArrayList<Person>()

    var idPersons = mutableListOf<String>()

    private lateinit var sqLiteManager: SQLiteManager


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

        sqLiteManager = SQLiteManager(this.requireContext())

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }


        persons.clear()

        getPersons()


        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // this method is called
                // when the item is moved.
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var position: Int = viewHolder.adapterPosition
                sqLiteManager.deletePerson(persons[position].id)
                persons.removeAt(position)
                mAdapter.notifyDataSetChanged()


            }

        }).attachToRecyclerView(mRecyclerView)


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {
//        Toast.makeText(requireContext(), "Item $position clicked", Toast.LENGTH_SHORT).show()
        val clickedItem: Person = persons[position]
        mAdapter.notifyItemChanged(position)
        //TODO do poprawy najwyżej, strzalka na wyjście z tego, zapisywanie stanu wsm spoko ale idk czy będzie git finalnie
//        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.dash, PersonFragment())?.addToBackStack(null)?.commit()
//        fragmentManager?.beginTransaction()?.add((requireView().parent as ViewGroup).id, PersonFragment())?.commit()
//        fragmentManager?.beginTransaction()?.replace(R.id.dash, PersonFragment())?.commit()



        // bad idea i guess

        // przekazywanie danych
        val name = clickedItem.name
        val content = clickedItem.content
        val balance = clickedItem.balance
        val date = clickedItem.date
        setFragmentResult("requestKey", bundleOf("n" to name,"c" to content, "d" to date, "b" to balance))

//         as per defined in your FragmentContainerView
        val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

// Navigate using the IDs you defined in your Nav Graph
        navController.navigate(R.id.navigation_person)
    }


    fun getPersons(){

        sqLiteManager.getAllPeople().forEach {
            persons.add(it)
        }

        mRecyclerView = binding.root.findViewById(R.id.recyclerView)
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        mAdapter = PersonAdapter(persons, this)
        mRecyclerView.layoutManager=mLayoutManager
        mRecyclerView.adapter = mAdapter
    }


}