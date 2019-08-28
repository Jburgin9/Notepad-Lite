package org.quietlip.mvvmnotes.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

    @BindView(R.id.note_display_text_view)
    TextView noteDisplayEt;

    private boolean newNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
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
}
