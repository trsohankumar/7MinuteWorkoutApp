package com.example.a7minuteworkoutapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class History (
    @PrimaryKey(autoGenerate = true)
    val id :Int,
    val completed_date:String
)

