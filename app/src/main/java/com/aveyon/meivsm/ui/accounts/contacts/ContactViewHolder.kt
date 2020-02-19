package com.aveyon.meivsm.ui.accounts.contacts

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ContactViewHolder(val view: TextView, private val listener: AdapterItemClickListener) :
    RecyclerView.ViewHolder(view), View.OnClickListener {

    init {
        view.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        listener.onContactItemClick(view, adapterPosition)
    }

    // parent activity needs to implement this method to respond to click events
    interface AdapterItemClickListener {
        fun onContactItemClick(view: View, position: Int)
    }
}