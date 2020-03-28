package com.ddhuy4298.note.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ddhuy4298.note.models.Note;

@Database(entities = {Note.class}, version = 2, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public static NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, NoteDatabase.class, "Note")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract NoteDao getNoteDao();
}
