package com.ddhuy4298.note.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddhuy4298.note.databinding.ItemNoteBinding;
import com.ddhuy4298.note.listener.NoteClickedListener;
import com.ddhuy4298.note.models.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private ArrayList<Note> data;
    private LayoutInflater inflater;
    private NoteClickedListener listener;
    private boolean multipleCheck = false;

    public NoteAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void setData(ArrayList<Note> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setListener(NoteClickedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNoteBinding binding = ItemNoteBinding.inflate(inflater, parent, false);
        return new NoteHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        holder.binding.setItem(data.get(position));
        if (listener != null) {
            holder.binding.setListener(listener);
        }
        if (multipleCheck) {
            holder.binding.checkbox.setVisibility(View.VISIBLE); // show checkBox if multiMode on
            holder.binding.checkbox.setChecked(data.get(position).isChecked());
        } else holder.binding.checkbox.setVisibility(View.GONE); // hide checkBox if multiMode off
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class NoteHolder extends RecyclerView.ViewHolder {

        private ItemNoteBinding binding;

        public NoteHolder(@NonNull ItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setMultipleCheck(boolean multipleCheck) {
        this.multipleCheck = multipleCheck;
        if (!multipleCheck) {
            for (Note note : data) {
                note.setChecked(false);
            }
        }
        notifyDataSetChanged();
    }

    public ArrayList<Note> getCheckedNotes() {
        ArrayList<Note> checkedNotes = new ArrayList<>();
        for (Note note : data) {
            if (note.isChecked())
                checkedNotes.add(note);
        }
        return checkedNotes;
    }
}
