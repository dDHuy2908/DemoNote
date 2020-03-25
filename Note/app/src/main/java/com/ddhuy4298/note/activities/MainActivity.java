package com.ddhuy4298.note.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;

import com.ddhuy4298.note.R;
import com.ddhuy4298.note.adapters.NoteAdapter;
import com.ddhuy4298.note.callbacks.MainActionModeCallBack;
import com.ddhuy4298.note.dao.NoteDatabase;
import com.ddhuy4298.note.databinding.ActivityMainBinding;
import com.ddhuy4298.note.listener.NoteClickedListener;
import com.ddhuy4298.note.models.Note;
import com.ddhuy4298.note.utils.DeviceNameUtils;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NoteClickedListener, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    public static final String EXTRA_NOTE_UPDATE = "note_update";

    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;

    private ArrayList<Note> data = new ArrayList<>();
    private NoteAdapter adapter;
    private int checked = 0;
    private MainActionModeCallBack actionModeCallBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

//        setSupportActionBar(binding.toolbar);
//        binding.toolbar.setTitle("Note");
//        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));

        binding.fab.setOnClickListener(this);

        loadNote();
    }

    private void loadNote() {
        adapter = new NoteAdapter(getLayoutInflater());
        data.clear();
        data.addAll(NoteDatabase.getInstance(this).getNoteDao().getNote());
        if (adapter != null) {
            adapter.setData(data);
            if (data.size() == 0) {
                binding.tvEmptyNotes.setVisibility(View.VISIBLE);
            } else {
                binding.tvEmptyNotes.setVisibility(View.GONE);
            }

            adapter.setListener(this);
        }
        binding.rvNote.setAdapter(adapter);
    }

    @Override
    public void onNoteClickedListener(Note note) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra(EXTRA_NOTE_UPDATE, note.getNoteDate());
        startActivity(intent);
    }

    @Override
    public boolean onNoteLongClickedListener(Note note) {
        note.setChecked(true);
        adapter.setMultipleCheck(true);
        checked = 1;

        adapter.setListener(new NoteClickedListener() {
            @Override
            public void onNoteClickedListener(Note note) {
                note.setChecked(!note.isChecked());

                if (note.isChecked()) checked++;
                else checked--;

                if (checked > 1) actionModeCallBack.changeShareItemVisible(false);
                else actionModeCallBack.changeShareItemVisible(true);

                if (checked == 0) actionModeCallBack.getActionMode().finish();

                actionModeCallBack.setCountItem(checked + "/" + data.size());

                adapter.notifyDataSetChanged();
            }

            @Override
            public boolean onNoteLongClickedListener(Note note) {
                return false;
            }
        });

        actionModeCallBack = new MainActionModeCallBack() {
            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.action_mode_delete)
                    onDeleteMultiNotes();
                else if (menuItem.getItemId() == R.id.action_mode_share)
                    onShareNote();

                actionMode.finish();
                return false;
            }

        };

        startActionMode(actionModeCallBack);
        binding.fab.setVisibility(View.GONE);
        actionModeCallBack.setCountItem(checked + "/" + data.size());

        return true;
    }

    @Override
    public void onActionModeFinished(android.view.ActionMode mode) {
        super.onActionModeFinished(mode);

        adapter.setMultipleCheck(false); // uncheck the notes
        adapter.setListener(this);
        binding.fab.setVisibility(View.VISIBLE);
    }

    private void onDeleteMultiNotes() {
        if (adapter.getCheckedNotes().size() != 0) {
            for (Note note : adapter.getCheckedNotes()) {
                NoteDatabase.getInstance(this).getNoteDao().deleteNote(note);
            }
            loadNote();
            Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No note to delete!", Toast.LENGTH_SHORT).show();
        }
    }

    private void onShareNote() {
        Note note = adapter.getCheckedNotes().get(0);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        String content = note.getNoteContent()
                + "\n"
                + format.format(note.getNoteDate())
                + "\n"
                + DeviceNameUtils.getDeviceName();
        intent.putExtra(Intent.EXTRA_TEXT, content);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNote();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
