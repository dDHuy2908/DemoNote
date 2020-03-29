package com.ddhuy4298.note.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.ddhuy4298.note.R;
import com.ddhuy4298.note.dao.NoteDatabase;
import com.ddhuy4298.note.databinding.ActivityEditNoteBinding;
import com.ddhuy4298.note.models.Note;

import java.text.SimpleDateFormat;

import static com.ddhuy4298.note.activities.MainActivity.EXTRA_NOTE_UPDATE;

public class EditNoteActivity extends AppCompatActivity {

    private ActivityEditNoteBinding binding;

    private Note note;
    private SimpleDateFormat format = new SimpleDateFormat("HH:mm dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_note);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        binding.toolbar.setNavigationIcon(R.drawable.ic_back_16dp);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent().hasExtra(EXTRA_NOTE_UPDATE)) {
            int noteId = getIntent().getExtras().getInt(EXTRA_NOTE_UPDATE, 0);
            note = NoteDatabase.getInstance(this).getNoteDao().getNoteById(noteId);
            binding.edtNoteContent.setText(note.getNoteContent());
            binding.tvNoteDate.setText(format.format(note.getNoteDate()));
        } else {
            binding.tvNoteDate.setText(format.format(System.currentTimeMillis()));
            binding.edtNoteContent.setFocusable(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save) {
            saveNote();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNote() {
        String content = binding.edtNoteContent.getText().toString();
        if (!content.isEmpty()) {
            if (note == null) {
                note = new Note();
                note.setNoteContent(content);
                NoteDatabase.getInstance(this).getNoteDao().insertNote(note);
            } else {
                note.setNoteContent(content);
                note.setNoteDate(System.currentTimeMillis());
                NoteDatabase.getInstance(this).getNoteDao().updateNote(note);
            }
            finish();
        }
    }
}
