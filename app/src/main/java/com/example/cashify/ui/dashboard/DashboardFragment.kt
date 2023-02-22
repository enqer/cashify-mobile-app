package com.example.cashify.ui.dashboard

import android.content.Intent
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
import com.example.cashify.databinding.FragmentDashboardBinding
import com.example.cashify.ui.personFragment.PersonFragment
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DashboardFragment : Fragment(), PersonAdapter.OnItemClickListener{
    private lateinit var mRecyclerView : RecyclerView
    private lateinit var mAdapter : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>
    private lateinit var mLayoutManager : RecyclerView.LayoutManager

    var persons = ArrayList<Person>()
    val newArr = ArrayList<Person>()

    private lateinit var sqLiteManager: SQLiteManager


    private var _binding: FragmentDashboardBinding? = null

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

        // sorting list by name or balance account
        val nameSort: TextView = root.findViewById(R.id.nameSort)
        nameSort.setOnClickListener{
            if (nameSort.text.equals("Name ▼"))
            {
                newArr.sortByDescending { it.name }
                mAdapter.notifyDataSetChanged()
                nameSort.text="Name ▲"
            }else{
                newArr.sortBy{ it.name }
                mAdapter.notifyDataSetChanged()
                nameSort.text="Name ▼"
            }

        }
        val balanceSort: TextView = root.findViewById(R.id.balanceSort)
        balanceSort.setOnClickListener{
            if (balanceSort.text.equals("Balance ▼")){
                newArr.sortBy { it.balance }
                mAdapter.notifyDataSetChanged()
                balanceSort.text="Balance ▲"
            }else{
                newArr.sortByDescending { it.balance }
                mAdapter.notifyDataSetChanged()
                balanceSort.text="Balance ▼"
            }
        }




        persons.clear()
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
                sqLiteManager.deletePerson(newArr[position].id,"table_cashify")
                newArr.removeAt(position)
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
        val clickedItem: Person = newArr[position]
        mAdapter.notifyItemChanged(position)

        // data send
        val name = clickedItem.name
        val content = clickedItem.content
        val balance = clickedItem.balance
        val date = clickedItem.date
        val avatar = clickedItem.avatar
        val allBalance = sqLiteManager.sumBalanceByPerson("table_cashify", name)
        val which = "table_cashify"

        setFragmentResult("requestKey", bundleOf("n" to name,"c" to content, "d" to date, "b" to balance, "a" to avatar, "i" to allBalance, "w" to which))


        parentFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_main, PersonFragment()).commit()

    }


    fun getPersons(){

        sqLiteManager.getAllPeople("table_cashify").forEach {
            persons.add(it)
            persons.sortByDescending { it.balance }

        }

        persons.distinctBy { it.name }.forEach{
            it.balance = sqLiteManager.sumBalanceByPerson("table_cashify",it.name)
            newArr.add(it)

        }


        mRecyclerView = binding.root.findViewById(R.id.recyclerView)
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        mAdapter = PersonAdapter(newArr, this)
        mRecyclerView.layoutManager=mLayoutManager
        mRecyclerView.adapter = mAdapter
    }




}