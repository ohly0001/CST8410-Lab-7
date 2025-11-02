package com.example.lab7

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ChatMessage::class], version = 1)
abstract class ChatMessageDatabase : RoomDatabase() {
{
    abstract fun getMyDAO(): ChatMessageDAO
}