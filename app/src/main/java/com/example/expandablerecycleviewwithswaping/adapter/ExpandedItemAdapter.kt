package com.example.expandablerecycleviewwithswaping.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.expandablerecycleviewwithswaping.databinding.ItemExpandedBinding
import com.example.expandablerecycleviewwithswaping.models.ExpandedItemModel

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
}

class ExpandedItemAdapter(private val items: MutableList<ExpandedItemModel>) :
    RecyclerView.Adapter<ExpandedItemAdapter.ViewHolder>(), ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemExpandedBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                items[i] = items.set(i + 1, items[i])
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                items[i] = items.set(i - 1, items[i])
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    inner class ViewHolder(private val binding: ItemExpandedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ExpandedItemModel) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}