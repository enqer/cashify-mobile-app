package com.example.cashify

import android.content.Context


import androidx.recyclerview.widget.RecyclerView
import com.example.cashify.PersonAdapter.PersonViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.example.cashify.R
import android.widget.TextView


class PersonAdapter(var mPerson: ArrayList<Person>,var mItemListener: OnItemClickListener) :
    RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {
    private val context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.person_item, parent, false)
        return PersonViewHolder(v)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
//        mPerson[position]

        val currentItem: Person = mPerson[position]
        holder.name.text = currentItem.name
//        holder.date.text = currentItem.date
//        holder.content.text = currentItem.content
        holder.balance.text = (currentItem.balance).toString()
//        holder.itemView.setOnClickListener {
//            mItemListener?.onItemClick(currentItem)
//        }
    }

    override fun getItemCount(): Int {
        return mPerson.size
    }

//    interface ItemClickListener {
//        fun onItemClick(person: Person)
//    }

    inner class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        var name: TextView
//        var date: TextView
//        var content: TextView
        var balance: TextView
        private val context: Context

        init {
            name = itemView.findViewById(R.id.personName)
//            date = itemView.findViewById(R.id.personDate)
//            content = itemView.findViewById(R.id.personContent)
            balance = itemView.findViewById(R.id.personBalance)
            //            itemView.setBackgroundColor(Color.WHITE);
            context = itemView.context
            itemView.setOnClickListener(this) // do wywalenia?
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position!= RecyclerView.NO_POSITION){
                mItemListener.onItemClick(position)
            }

        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }


}