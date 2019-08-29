package org.quietlip.mvvmnotes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.quietlip.mvvmnotes.R;
import org.quietlip.mvvmnotes.model.Note;
import org.quietlip.mvvmnotes.recyclerview.NotesAdapter;
import org.quietlip.mvvmnotes.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "PROUD";

    //Binding Views
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
//    @BindView(R.id.main_toolbar)
//    Toolbar toolbar;
    @BindView(R.id.add_fab)
    FloatingActionButton addFab;

    //OnClick
    @OnClick(R.id.add_fab)
    void fabClickEventHandler() {
        Intent intent = new Intent(MainActivity.this, EditorActivity.class);
        startActivity(intent);
    }

    private List<Note> notesData = new ArrayList<>();
    private NotesAdapter adapter;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initViewModel() {
        final Observer<List<Note>> notesObserver = notes -> {
            notesData.clear();
            notesData.addAll(notes);
            if (adapter == null) {
                adapter = new NotesAdapter(notesData, MainActivity.this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        };
        mainViewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);
        mainViewModel.notes.observe(this, notesObserver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_sample_data:
                addSampleData();
                return true;
            case R.id.action_delete_all:
                deleteAllNotes();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    private void addSampleData(){
        mainViewModel.addSampleData();
    }

    private void deleteAllNotes(){
        mainViewModel.deleteAllNotes();
    }
}
