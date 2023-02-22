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
import com.example.cashify.ui.personFragment.PersonFragment
import java.util.ArrayList

class NotificationsFragment : Fragment(), PersonAdapter.OnItemClickListener {

    private lateinit var mRecyclerViewNot : RecyclerView
    private lateinit var mAdapterNot : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>
    private lateinit var mLayoutManagerNot : RecyclerView.LayoutManager

    var personsNot = ArrayList<Person>()
    val newArr = ArrayList<Person>()

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
                newArr.sortByDescending { it.name }
                mAdapterNot.notifyDataSetChanged()
                nameSortNot.text="Name ▲"
            }else{
                newArr.sortBy{ it.name }
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
        newArr.clear()
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
        val clickedItem: Person = newArr[position]
        mAdapterNot.notifyItemChanged(position)


        // data send
        val name = clickedItem.name
        val content = clickedItem.content
        val balance = clickedItem.balance
        val date = clickedItem.date
        val avatar = clickedItem.avatar
         val allBalance = sqLiteManagerNot.sumBalanceByPerson("table_cashify_not", name)
         val which = "table_cashify_not"

        setFragmentResult("requestKey", bundleOf("n" to name,"c" to content, "d" to date, "b" to balance, "a" to avatar, "i" to allBalance, "w" to which))


         parentFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_main, PersonFragment()).commit()
    }

    fun getPersons(){
        sqLiteManagerNot.getAllPeople("table_cashify_not").forEach {
            personsNot.add(it)
            personsNot.sortByDescending { it.balance }


        }
        personsNot.distinctBy { it.name }.forEach{
            it.balance = sqLiteManagerNot.sumBalanceByPerson("table_cashify_not",it.name)
            newArr.add(it)

        }

        mRecyclerViewNot = binding.root.findViewById(R.id.recyclerViewNot)
        mRecyclerViewNot.setHasFixedSize(true)
        mLayoutManagerNot = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        mAdapterNot = PersonAdapter(newArr, this)
        mRecyclerViewNot.layoutManager=mLayoutManagerNot
        mRecyclerViewNot.adapter = mAdapterNot
    }
}