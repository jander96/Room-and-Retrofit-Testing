package com.example.roomtesting.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "item_name") val itemName: String
)