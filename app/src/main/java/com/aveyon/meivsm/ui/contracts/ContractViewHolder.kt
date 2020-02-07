package com.aveyon.meivsm.ui.contracts

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.aveyon.meivsm.R

class ContractViewHolder(
    private var view: CardView,
    private var itemClickListener: AdapterItemClickListener
) : RecyclerView.ViewHolder(view) {

    init {
        view.setOnClickListener { v ->
            itemClickListener.onItemClick(v, adapterPosition)
        }
    }

    fun setText(text: String) {
        view.findViewById<TextView>(R.id.contract_category).text = text
    }

    // parent activity needs to implement this method to respond to click events
    interface AdapterItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}