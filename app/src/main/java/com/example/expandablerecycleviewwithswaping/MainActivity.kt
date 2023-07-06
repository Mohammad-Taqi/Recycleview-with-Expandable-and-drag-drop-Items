package com.example.expandablerecycleviewwithswaping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expandablerecycleviewwithswaping.adapter.ItemAdapter
import com.example.expandablerecycleviewwithswaping.databinding.ActivityMainBinding
import com.example.expandablerecycleviewwithswaping.models.ExpandedItemModel
import com.example.expandablerecycleviewwithswaping.models.ItemModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = mutableListOf(
            ItemModel(
                "Item 1",
                false,
                mutableListOf(
                    ExpandedItemModel("Expanded Item 1-1"),
                    ExpandedItemModel("Expanded Item 1-2"),
                    ExpandedItemModel("Expanded Item 1-3")
                )
            ),
            ItemModel(
                "Item 2",
                false,
                mutableListOf(
                    ExpandedItemModel("Expanded Item 2-1"),
                    ExpandedItemModel("Expanded Item 2-2"),
                    ExpandedItemModel("Expanded Item 2-3"),
                    ExpandedItemModel("Expanded Item 2-4")
                )
            ),
            ItemModel(
                "Item 3",
                false,
                mutableListOf(
                    ExpandedItemModel("Expanded Item 3-1"),
                    ExpandedItemModel("Expanded Item 3-2"),
                    ExpandedItemModel("Expanded Item 3-3"),
                    ExpandedItemModel("Expanded Item 3-4"),
                    ExpandedItemModel("Expanded Item 3-5"),
                )
            ),
            ItemModel(
                "Item 4",
                false,
                mutableListOf(
                    ExpandedItemModel("Expanded Item 4-1"),
                    ExpandedItemModel("Expanded Item 4-2"),
                    ExpandedItemModel("Expanded Item 4-3")
                )
            ),
            ItemModel(
                "Item 5",
                false,
                mutableListOf(
                    ExpandedItemModel("Expanded Item 5-1"),
                    ExpandedItemModel("Expanded Item 5-2"),
                    ExpandedItemModel("Expanded Item 5-3"),
                    ExpandedItemModel("Expanded Item 5-4"),
                    ExpandedItemModel("Expanded Item 5-5"),
                    ExpandedItemModel("Expanded Item 5-6")
                )
            )
        )
        adapter = ItemAdapter(items)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
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
                adapter.onItemMove(fromPosition, toPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

            override fun isLongPressDragEnabled(): Boolean {
                return true
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }
}