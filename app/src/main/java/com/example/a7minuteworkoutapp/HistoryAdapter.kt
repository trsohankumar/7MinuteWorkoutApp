package com.example.a7minuteworkoutapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minuteworkoutapp.data.History
import com.example.a7minuteworkoutapp.databinding.ItemHistoryRowBinding

class HistoryAdapter(val context: Context):RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){
    private var items = emptyList<History>()
    class ViewHolder(val binding: ItemHistoryRowBinding):RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryRowBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date: String = items[position].completed_date

        holder.binding.tvPosition.text = (position + 1).toString()
        holder.binding.tvItem.text = date

        // Updating the background color according to the odd/even positions in list.
        if (position % 2 == 0) {
            holder.binding.llHistoryItemMain.setBackgroundColor(
                Color.parseColor("#EBEBEB")
            )
        } else {
            holder.binding.llHistoryItemMain.setBackgroundColor(
                Color.parseColor("#FFFFFF")
            )
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(history : List<History>){
        this.items = history
        notifyDataSetChanged()
    }

}