package com.aveyon.meivsm.ui.contracts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.aveyon.meivsm.web3.GenericContractInterface
import java.util.*

class ContractAdapter(private var itemClickListener: ContractViewHolder.AdapterItemClickListener) :
    RecyclerView.Adapter<ContractViewHolder>() {
    var dataSet: List<GenericContractInterface> = LinkedList()


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContractViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(
                com.aveyon.meivsm.R.layout.recyclerview_contract_row,
                parent,
                false
            )
        return ContractViewHolder(view as CardView, itemClickListener)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ContractViewHolder, position: Int) {
        holder.setText(dataSet[position].category.toUpperCase())
    }
}