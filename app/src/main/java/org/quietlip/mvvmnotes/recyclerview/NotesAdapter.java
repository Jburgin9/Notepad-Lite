package org.quietlip.mvvmnotes.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.quietlip.mvvmnotes.R;
import org.quietlip.mvvmnotes.model.Note;
import org.quietlip.mvvmnotes.ui.EditorActivity;
import org.quietlip.mvvmnotes.utilis.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private final List<Note> notesList;
    private final Context context;

    public NotesAdapter(List<Note> notesList, Context context) {
        this.notesList = notesList;
        this.context = context;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item_layout,
                parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        final Note note = notesList.get(position);
        holder.titleTv.setText(note.getTitle());
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        long postTime = note.getDate().getTime();
        int diff = (int) (System.currentTimeMillis() - postTime) + 1000;
        String text = formatter.format(new Date(diff));
        holder.timeLapsedTv.setText("Posted " + diff + " ago" );

        holder.editBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditorActivity.class);
            intent.putExtra(Constants.NOTE_ID_KEY, note.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
}
