package com.ninidze.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ninidze.data.local.dto.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = true)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}
