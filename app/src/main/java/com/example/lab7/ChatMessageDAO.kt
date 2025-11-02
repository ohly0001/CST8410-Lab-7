package com.example.lab7

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

interface ChatMessageDAO
{
    @Insert
    fun insertMessage(message:ChatMessage): Long

    @Update
    fun updateMessage(message:ChatMessage): Unit

    @Upsert
    fun upsertMessage(message:ChatMessage): Unit

    @Query("SELECT * FROM ChatMessage")
    fun selectAllMessages(): List<ChatMessage>

    @Delete
    fun deleteMessage(message:ChatMessage): Unit
}