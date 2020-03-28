package com.ddhuy4298.note.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ddhuy4298.note.models.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM Note")
    List<Note> getNote();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note ... note);

    @Update
    void updateNote(Note ... note);

    @Delete
    void deleteNote(Note ... note);

    @Query("SELECT * FROM Note WHERE noteId = :id")
    Note getNoteById(int id);
}
