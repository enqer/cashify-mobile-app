package com.example.pkam

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ContentAdapter(var mContent: ArrayList<Content>):
    RecyclerView.Adapter<ContentAdapter.ContentViewHolder>() {
    private val context: Context? = null
    inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var date: TextView
        //        var date: TextView
//        var content: TextView
        var balance: TextView
        var content: TextView

        private val context: Context

        init {
            date = itemView.findViewById(R.id.fpDate)
//            date = itemView.findViewById(R.id.personDate)
//            content = itemView.findViewById(R.id.personContent)
            balance = itemView.findViewById(R.id.fpBalance)
            content = itemView.findViewById(R.id.fpContent)
            //            itemView.setBackgroundColor(Color.WHITE);
            context = itemView.context

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.data_item, parent, false)
        return ContentViewHolder(v)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val currentItem: Content = mContent[position]

        holder.date.text = currentItem.date
        holder.content.text = currentItem.content
        val s: String = "€"
        if (Math.ceil(currentItem.balance) == Math.floor(currentItem.balance)){
            holder.balance.text = currentItem.balance.toInt().toString() + s
        }else{
            holder.balance.text = (currentItem.balance).toString() + s
        }

    }

    override fun getItemCount(): Int {
        return mContent.size
    }


}