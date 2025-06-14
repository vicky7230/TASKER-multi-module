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
                    // Pre-populate database with tags
                    db.execSQL("INSERT INTO tags (name, color) VALUES ('Work', '#61DEA4')")
                    db.execSQL("INSERT INTO tags (name, color) VALUES ('Shopping', '#F45E6D')")
                    db.execSQL("INSERT INTO tags (name, color) VALUES ('Family', '#006CFF')")
                    db.execSQL("INSERT INTO tags (name, color) VALUES ('Personal', '#B678FF')")

                    // Pre-populate database with sample data
                    db.execSQL("INSERT INTO notes (content, tagId, timestamp) VALUES ('Welcome to your notes app! This is your first note.','1','" + System.currentTimeMillis() + "')")
                    db.execSQL("INSERT INTO notes (content, tagId, timestamp) VALUES ('You can add, edit, and delete notes here.','2','" + System.currentTimeMillis() + "')")
                    db.execSQL("INSERT INTO notes (content, tagId, timestamp) VALUES ('Try creating your own note by tapping the add button!','3','" + System.currentTimeMillis() + "')")
                    db.execSQL("INSERT INTO notes (content, tagId, timestamp) VALUES ('This app uses Room database to store your notes locally.','4','" + System.currentTimeMillis() + "')")
                    db.execSQL("INSERT INTO notes (content, tagId, timestamp) VALUES ('Your notes are saved automatically and will persist between app sessions.','1','" + System.currentTimeMillis() + "')")
                }
            })
            .build()
    }
}
