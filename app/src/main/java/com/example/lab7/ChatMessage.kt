package com.example.lab7

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ChatMessages")
data class ChatMessage(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var pfpId: Int = -1,

    var message: String = "",

    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val createdTime: String? = null,

    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val lastModifiedTime: String? = null,

    var isReceived: Boolean = false,
)
