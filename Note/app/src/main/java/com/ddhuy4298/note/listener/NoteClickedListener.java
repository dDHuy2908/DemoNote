package com.ddhuy4298.note.listener;

import android.view.View;

import com.ddhuy4298.note.models.Note;

public interface NoteClickedListener {
    void onNoteClickedListener(Note note);
    boolean onNoteLongClickedListener(Note note);
}
