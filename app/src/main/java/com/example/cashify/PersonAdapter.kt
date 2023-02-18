package com.example.cashify

import android.content.Context


import androidx.recyclerview.widget.RecyclerView
import com.example.cashify.PersonAdapter.PersonViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
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
//        holder.balance.text = (currentItem.balance).toString()
        val s: String = "€"
        if (Math.ceil(currentItem.balance) == Math.floor(currentItem.balance)){
            holder.balance.text = currentItem.balance.toInt().toString() + s
        }else{
            holder.balance.text = (currentItem.balance).toString() + s
        }


        when(currentItem.avatar){
            "img1" -> holder.avatar.setImageResource(R.drawable.ic_avatar_bad_breaking)
            "img2" -> holder.avatar.setImageResource(R.drawable.ic_avatar_batman_comics)
            "img3" -> holder.avatar.setImageResource(R.drawable.ic_avatar_dead_monster)
            "img4" -> holder.avatar.setImageResource(R.drawable.ic_avatar_elderly_grandma)
            "img5" -> holder.avatar.setImageResource(R.drawable.ic_avatar_man_muslim)
            "img6" -> holder.avatar.setImageResource(R.drawable.ic_avatar_man_person)
        }
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
        var avatar: ImageView

        private val context: Context

        init {
            name = itemView.findViewById(R.id.personName)
//            date = itemView.findViewById(R.id.personDate)
//            content = itemView.findViewById(R.id.personContent)
            balance = itemView.findViewById(R.id.personBalance)
            avatar = itemView.findViewById(R.id.personImageView)
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