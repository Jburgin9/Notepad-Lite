package org.quietlip.mvvmnotes;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "PROUD";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<Note> notesData = new ArrayList<>();
    private NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        notesData.addAll(SampleData.getNotes());
        for (Note note: notesData) {
            Log.d(TAG, note.toString() );

        }

        initRecyclerView();

    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotesAdapter(notesData, this);
        recyclerView.setAdapter(adapter);
    }
}
