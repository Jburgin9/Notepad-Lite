package org.quietlip.mvvmnotes.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.quietlip.mvvmnotes.R;
import org.quietlip.mvvmnotes.model.Note;
import org.quietlip.mvvmnotes.utilis.Constants;
import org.quietlip.mvvmnotes.viewmodel.EditorViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditorActivity extends AppCompatActivity {
    private static final String TAG = "JAFFY";
    private EditorViewModel editorViewModel;

    @BindView(R.id.note_display_edit_text)
    EditText noteDisplayEt;


    private boolean newNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.ic_check_save_note);
        actionBar.setDisplayShowTitleEnabled(false);

        Log.d(TAG, "onCreate: ");
        ButterKnife.bind(this);
        initViewModel();
        //Toast.makeText(this, "editor activity", Toast.LENGTH_SHORT).show();
    }

    private void initViewModel() {
        Log.d(TAG, "initViewModel: ");
        editorViewModel = ViewModelProviders.of(this)
                .get(EditorViewModel.class);

        editorViewModel.liveNote.observe(this, new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                if(note != null){
                    Log.d(TAG, "onChanged: " + note.getId());
                    noteDisplayEt.setText(note.getText());
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            setTitle("New note");
            newNote = true;
        } else {
            setTitle("Edit Note");
            int noteId = extras.getInt(Constants.NOTE_ID_KEY);
            editorViewModel.loadData(noteId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.check_save_option:
                saveNote();
                return true;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        saveNote();
        finish();
    }

    public void saveNote(){
        editorViewModel.saveNote(noteDisplayEt.getText().toString());
    }
}
