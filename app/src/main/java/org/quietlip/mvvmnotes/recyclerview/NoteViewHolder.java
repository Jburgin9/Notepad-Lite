package org.quietlip.mvvmnotes.recyclerview;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.quietlip.mvvmnotes.R;
import org.quietlip.mvvmnotes.model.Note;
import org.quietlip.mvvmnotes.ui.EditorActivity;
import org.quietlip.mvvmnotes.utilis.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.note_item_title_tv)
    TextView titleTv;

    @BindView(R.id.time_lapsed_tv)
    TextView timeLapsedTv;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void onBind(Note note){
        itemView.setOnClickListener(v -> {
            Intent intent = new Intent(itemView.getContext(), EditorActivity.class);
            intent.putExtra(Constants.NOTE_ID_KEY, note.getId());
            itemView.getContext().startActivity(intent);
        });
    }
}
