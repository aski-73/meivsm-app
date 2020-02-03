package com.aveyon.meivsm.ui.accounts.contacts

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aveyon.meivsm.db.Contact
import java.lang.RuntimeException
import java.util.*


class ContactAdapter(private var itemClickListener: ContactViewHolder.AdapterItemClickListener) :
    RecyclerView.Adapter<ContactViewHolder>() {

    var dataSet: List<Contact> = LinkedList()

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(recyclerView: ViewGroup, viewType: Int): ContactViewHolder {
        var view =
            LayoutInflater.from(recyclerView.context).inflate(
                com.aveyon.meivsm.R.layout.recyclerview_row_contact,
                recyclerView,
                false
            )

//        view = DataBindingUtil.infl

        return ContactViewHolder(
            view as TextView, itemClickListener
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.view.text = dataSet[position].name
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}