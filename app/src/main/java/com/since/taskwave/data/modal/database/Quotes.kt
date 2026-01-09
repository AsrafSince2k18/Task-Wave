package com.since.taskwave.data.modal.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Quotes")
data class Quotes(

    val author: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val quote: String,
    val time:Long,
    val typeRequest: String,

)
