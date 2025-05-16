package com.ninidze.framework.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ninidze.data.di.DataModule.Companion
import com.ninidze.data.local.NotesDao
import com.ninidze.data.local.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    private const val DB_NAME = "notes.db"

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): NotesDatabase = Room.databaseBuilder(
        context,
        NotesDatabase::class.java,
        DB_NAME,
    )
        .setJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING)
        .build()

    @Provides
    fun provideNotesDao(db: NotesDatabase): NotesDao = db.notesDao()
}