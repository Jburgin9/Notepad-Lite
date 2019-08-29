package org.quietlip.mvvmnotes.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.quietlip.mvvmnotes.R;
import org.quietlip.mvvmnotes.model.Note;
import org.quietlip.mvvmnotes.utilis.Constants;
import org.quietlip.mvvmnotes.viewmodel.EditorViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditorActivity extends AppCompatActivity {
    private static final String TAG = "JAFFY";
    private EditorViewModel editorViewModel;

    @BindView(R.id.note_display_edit_text)
    EditText noteDisplayEt;

    @BindView(R.id.delete_note_option)
    MaterialButton deleteNoteBtn;

    @BindView(R.id.save_note_option)
    MaterialButton saveNoteBtn;

    @BindView(R.id.editor_note_title_et)
    TextInputEditText noteTitleTv;

    @BindView(R.id.text_input_layout)
    TextInputLayout textInputLayout;

    BottomSheetBehavior bottomSheetBehavior;

    private boolean newNote;
    private boolean isBackPressed = false;

    @OnClick(R.id.save_note_option)
    void saveClickHandler(){
        saveNote();
        //show animation during save and snackbar after success
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        actionBarSetup();
        ButterKnife.bind(this);
        initViewModel();

        View bottomSheet = findViewById(R.id.editor_tray_bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

    }
//initViewModel
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
                    noteTitleTv.setText(note.getTitle());
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            newNote = true;
        } else {
            setTitle("Edit Note");
            int noteId = extras.getInt(Constants.NOTE_ID_KEY);
            editorViewModel.loadData(noteId);
        }
    }


    @Override
    public void onBackPressed() {
        if(!isBackPressed){
            if (TextUtils.isEmpty(noteDisplayEt.getText().toString()) && TextUtils.isEmpty(noteTitleTv.getText().toString())){
                finish();
            } else {
                //make user double click to exit and give them warning to save or lose changes
                isBackPressed = true;
                Toast.makeText(this, "press back again to save and leave", Toast.LENGTH_SHORT).show();
            }
        } else {
            saveNote();
            finish();

        }
    }

    public void saveNote(){
        editorViewModel.saveNote(noteDisplayEt.getText().toString(), noteTitleTv.getText().toString());
    }

    private void actionBarSetup(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
    }
}
