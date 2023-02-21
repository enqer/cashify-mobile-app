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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cashify.*
import java.lang.Math.ceil
import java.lang.Math.floor


class PersonFragment : Fragment() {

    private lateinit var mRecyclerView : RecyclerView
    private lateinit var mAdapter : RecyclerView.Adapter<ContentAdapter.ContentViewHolder>
    private lateinit var mLayoutManager : RecyclerView.LayoutManager
    var contents = ArrayList<Content>()

    private lateinit var sqLiteManager: SQLiteManager


    companion object {
        fun newInstance() = PersonFragment()
    }

    private lateinit var viewModel: PersonViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_person, container, false)

        sqLiteManager = SQLiteManager(this.requireContext())
        mRecyclerView = root.findViewById(R.id.recyclerViewPerson)

        var picutre: ImageView = root.findViewById(R.id.fpImageView)


        var txtName: TextView = root.findViewById(R.id.fpName)
        var txtIncome: TextView = root.findViewById(R.id.income)
//        var txtDate: TextView = root.findViewById(R.id.fpDate)
//        var txtContent: TextView = root.findViewById(R.id.fpContent)
//        var txtBalance: TextView = root.findViewById(R.id.fpBalance)

        setFragmentResultListener("requestKey") { key, bundle ->
            val name = bundle.getString("n")
//            val date = bundle.getString("d")
//            val content = bundle.getString("c")
//            var balance = bundle.getDouble("b")
            val avatar = bundle.getString("a")
            val allBalance = bundle.getDouble("i")
//            val t = bundle.getStringArrayList("o")
            val which = bundle.getString("w")


            val s: String = "€"
//            if (ceil(balance) == floor(balance)){
//                txtBalance.text = balance.toInt().toString() + s
//            }else{
//                txtBalance.text=balance.toString() + s
//            }
            if (ceil(allBalance) == floor(allBalance)) {
                txtIncome.text = allBalance.toInt().toString() + s
            } else {
                txtIncome.text = allBalance.toString() + s
            }

            txtName.text = name.toString()
//            txtIncome.text=allBalance.toString()
//            txtDate.text=date.toString()
//            txtContent.text=content.toString()


            when (avatar) {
                "img1" -> picutre.setImageResource(R.drawable.ic_avatar_bad_breaking)
                "img2" -> picutre.setImageResource(R.drawable.ic_avatar_batman_comics)
                "img3" -> picutre.setImageResource(R.drawable.ic_avatar_dead_monster)
                "img4" -> picutre.setImageResource(R.drawable.ic_avatar_elderly_grandma)
                "img5" -> picutre.setImageResource(R.drawable.ic_avatar_man_muslim)
                "img6" -> picutre.setImageResource(R.drawable.ic_avatar_man_person)
            }


            contents.clear()

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
                    if (which != null) {
                        sqLiteManager.deleteContent(contents[position].content, which)
                        val b = allBalance - contents[position].balance
                        if (ceil(b) == floor(b)) {
                            txtIncome.text = b.toInt().toString() + s
                        } else {
                            txtIncome.text = b.toString() + s
                        }

                    }
                    contents.removeAt(position)
                    mAdapter.notifyDataSetChanged()


                }

            }).attachToRecyclerView(mRecyclerView)



            val newArr = ArrayList<Person>()
//            sqLiteManager.getAllPeople("table_cashify").forEach {
//                contents.add(it)
//                contents.sortByDescending { it.balance }
//
//            }

            if (name != null) {
                if (which != null) {
                    sqLiteManager.getAllContent(which,name).forEach {
                        contents.add(it)
                        contents.sortByDescending { it.balance }
                    }
                }
            }




            mRecyclerView.setHasFixedSize(true)
            mLayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            mAdapter = ContentAdapter(contents)
            mRecyclerView.layoutManager = mLayoutManager
            mRecyclerView.adapter = mAdapter
        }


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PersonViewModel::class.java)
        // TODO: Use the ViewModel

    }



}