package com.ddhuy4298.note.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ddhuy4298.note.R;
import com.ddhuy4298.note.adapters.NoteAdapter;
import com.ddhuy4298.note.databinding.ActivityMainBinding;
import com.ddhuy4298.note.models.Note;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ADD = 1;
    private ActivityMainBinding binding;

    private ArrayList<Note> data = new ArrayList<>();
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //FloatingActionButton
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewNote();
            }
        });

        //RecyclerView
        adapter = new NoteAdapter(getLayoutInflater());
        binding.rvNote.setAdapter(adapter);
        adapter.setData(data);
        if (data.size() == 0) {
            binding.tvEmptyNotes.setVisibility(View.VISIBLE);
        } else {
            binding.tvEmptyNotes.setVisibility(View.GONE);
        }
    }

    private void addNewNote() {
        Intent intent = new Intent(this, EditNoteActivity.class);
        startActivityForResult(intent, REQUEST_ADD);
    }
}
