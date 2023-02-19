package com.example.cashify.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import com.example.cashify.databinding.FragmentNotificationsBinding
import java.util.ArrayList

class NotificationsFragment : Fragment(), PersonAdapter.OnItemClickListener {

    private lateinit var mRecyclerViewNot : RecyclerView
    private lateinit var mAdapterNot : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>
    private lateinit var mLayoutManagerNot : RecyclerView.LayoutManager

    var personsNot = ArrayList<Person>()

    private lateinit var sqLiteManagerNot: SQLiteManager

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sqLiteManagerNot = SQLiteManager(this.requireContext())

        // sorting list by name or balance account
        val nameSortNot: TextView = root.findViewById(R.id.nameSortNot)
        nameSortNot.setOnClickListener{
            if (nameSortNot.text.equals("Name ▼"))
            {
                personsNot.sortByDescending { it.name }
                mAdapterNot.notifyDataSetChanged()
                nameSortNot.text="Name ▲"
            }else{
                personsNot.sortBy{ it.name }
                mAdapterNot.notifyDataSetChanged()
                nameSortNot.text="Name ▼"
            }

        }
        val balanceSortNot: TextView = root.findViewById(R.id.balanceSortNot)
        balanceSortNot.setOnClickListener{
            if (balanceSortNot.text.equals("Balance ▼")){
                personsNot.sortBy { it.balance }
                mAdapterNot.notifyDataSetChanged()
                balanceSortNot.text="Balance ▲"
            }else{
                personsNot.sortByDescending { it.balance }
                mAdapterNot.notifyDataSetChanged()
                balanceSortNot.text="Balance ▼"
            }
        }


        personsNot.clear()

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
                sqLiteManagerNot.deletePerson(personsNot[position].id, "table_cashify_not")
                personsNot.removeAt(position)
                mAdapterNot.notifyDataSetChanged()


            }

        }).attachToRecyclerView(mRecyclerViewNot)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

     override fun onItemClick(position: Int) {
//        Toast.makeText(requireContext(), "Item $position clicked", Toast.LENGTH_SHORT).show()
        val clickedItem: Person = personsNot[position]
        mAdapterNot.notifyItemChanged(position)
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
        val avatar = clickedItem.avatar
        setFragmentResult("requestKey", bundleOf("n" to name,"c" to content, "d" to date, "b" to balance, "a" to avatar))

//         as per defined in your FragmentContainerView
        val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

// Navigate using the IDs you defined in your Nav Graph
        navController.navigate(R.id.navigation_person)
    }

    fun getPersons(){
        sqLiteManagerNot.getAllPeople("table_cashify_not").forEach {
            personsNot.add(it)
            personsNot.sortByDescending { it.balance }


        }
        mRecyclerViewNot = binding.root.findViewById(R.id.recyclerViewNot)
        mRecyclerViewNot.setHasFixedSize(true)
        mLayoutManagerNot = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        mAdapterNot = PersonAdapter(personsNot, this)
        mRecyclerViewNot.layoutManager=mLayoutManagerNot
        mRecyclerViewNot.adapter = mAdapterNot
    }
}