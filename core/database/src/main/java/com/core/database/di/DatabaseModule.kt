package com.core.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.core.database.NotesDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideProductsDb(context: Context): NotesDb {
        return Room.databaseBuilder(context, NotesDb::class.java, "NotesDb")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // Pre-populate database with sample data
                    db.execSQL("INSERT INTO notes (content) VALUES ('Welcome to your notes app! This is your first note.')")
                    db.execSQL("INSERT INTO notes (content) VALUES ('You can add, edit, and delete notes here.')")
                    db.execSQL("INSERT INTO notes (content) VALUES ('Try creating your own note by tapping the add button!')")
                    db.execSQL("INSERT INTO notes (content) VALUES ('This app uses Room database to store your notes locally.')")
                    db.execSQL("INSERT INTO notes (content) VALUES ('Your notes are saved automatically and will persist between app sessions.')")
                }
            })
            .build()
    }
}
