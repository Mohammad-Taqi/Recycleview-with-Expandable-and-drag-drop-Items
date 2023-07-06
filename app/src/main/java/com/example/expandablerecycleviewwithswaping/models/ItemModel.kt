package com.example.expandablerecycleviewwithswaping.models
// ItemModel.kt
data class ItemModel(val title: String, var isExpanded: Boolean = false, val expandedList: MutableList<ExpandedItemModel>)
data class ExpandedItemModel(val title: String)
