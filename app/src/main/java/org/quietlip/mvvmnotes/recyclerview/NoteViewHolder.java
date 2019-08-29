package org.quietlip.mvvmnotes.recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.quietlip.mvvmnotes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.edit_btn)
    FloatingActionButton editBtn;

    @BindView(R.id.note_item_title_tv)
    TextView titleTv;

    @BindView(R.id.time_lapsed_tv)
    TextView timeLapsedTv;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
