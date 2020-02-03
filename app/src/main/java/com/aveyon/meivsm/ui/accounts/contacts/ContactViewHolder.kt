package com.aveyon.meivsm.ui.accounts.contacts

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ContactViewHolder(val view: TextView, private val listener: AdapterItemClickListener) : RecyclerView.ViewHolder(view), View.OnClickListener{

    init {
        view.setOnClickListener(this)
        Log.d(this.javaClass.name, "inited")
        Log.d(this.javaClass.name, view.isClickable.toString())
        Log.d(this.javaClass.name, view.hasOnClickListeners().toString())

    }

    override fun onClick(v: View?) {
        Log.d(this.javaClass.name, "clicked")
        listener.onItemClick(view, adapterPosition)
    }

    // parent activity will implement this method to respond to click events
    interface AdapterItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}