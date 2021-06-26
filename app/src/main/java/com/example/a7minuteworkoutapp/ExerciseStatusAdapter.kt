package com.example.a7minuteworkoutapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minuteworkoutapp.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(private val items: ArrayList<ExerciseModel>, val context: Context): RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    class ViewHolder(val binding : ItemExerciseStatusBinding):RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model:ExerciseModel = items[position]
        holder.binding.tvItem.text = model.id.toString()

        if(model.isSelected){
            holder.binding.tvItem.background = ContextCompat.getDrawable(context,R.drawable.item_circular_thin_color_accent_border)
            holder.binding.tvItem.setTextColor(Color.parseColor("#212121"))
        } else  if (model.isCompleted){
            holder.binding.tvItem.background = ContextCompat.getDrawable(context,R.drawable.item_circular_color_accent_background)
            holder.binding.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
        }else{
            holder.binding.tvItem.background = ContextCompat.getDrawable(context,R.drawable.item_circular_color_gray_background)
            holder.binding.tvItem.setTextColor(Color.parseColor("#212121"))
        }
    }

}