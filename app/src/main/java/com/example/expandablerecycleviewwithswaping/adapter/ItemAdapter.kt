package com.example.expandablerecycleviewwithswaping.adapter

// ItemAdapter.kt
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.expandablerecycleviewwithswaping.R
import com.example.expandablerecycleviewwithswaping.databinding.ItemListBinding
import com.example.expandablerecycleviewwithswaping.models.ItemModel

class ItemAdapter(private val items: MutableList<ItemModel>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    var itemTouchHelper: ItemTouchHelper? = null
    private var expandedItemPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemListBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_list, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun onItemMove(fromPosition: Int, toPosition: Int) {
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

    inner class ViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
//                val item = items[position]
                expandedItemPosition = if (position == expandedItemPosition) {
                    -1 // Collapse the currently expanded item
                } else {
                    position // Expand the clicked item
                }
                notifyDataSetChanged() // Notify the adapter of the item changes
            }
        }

        fun bind(item: ItemModel) {
            binding.viewModel = item
            val isExpanded = adapterPosition == expandedItemPosition
            binding.isExpanded = isExpanded
            binding.executePendingBindings()

            if (isExpanded) {
                val expandedRecyclerView = binding.expandedRecyclerView
                expandedRecyclerView.adapter = ExpandedItemAdapter(item.expandedList)
                itemTouchHelper?.attachToRecyclerView(null)

                itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                    0
                ) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        val fromPosition = viewHolder.adapterPosition
                        val toPosition = target.adapterPosition
                        (expandedRecyclerView.adapter as ExpandedItemAdapter).onItemMove(
                            fromPosition,
                            toPosition
                        )
                        return true
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        Log.d("CheckingSwiped", "onSwiped: $adapterPosition")
                    }

                    override fun onMoved(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        fromPos: Int,
                        target: RecyclerView.ViewHolder,
                        toPos: Int,
                        x: Int,
                        y: Int
                    ) {
                        Log.d("CheckingSwiped", "onMoved: $fromPos ::: $toPos")

                        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y)
                    }

                    override fun isLongPressDragEnabled(): Boolean {
                        return true
                    }
                })

                itemTouchHelper?.attachToRecyclerView(expandedRecyclerView)
            } else {
                binding.expandedRecyclerView.adapter = null
            }
        }
    }

}
