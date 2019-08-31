package org.quietlip.mvvmnotes.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.quietlip.mvvmnotes.R;
import org.quietlip.mvvmnotes.model.Note;
import org.quietlip.mvvmnotes.utilis.Constants;
import org.quietlip.mvvmnotes.utilis.Helper;
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
    TextInputEditText noteTitleEt;

    @BindView(R.id.text_input_layout)
    TextInputLayout textInputLayout;

    @BindView(R.id.editor_coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    BottomSheetBehavior bottomSheetBehavior;

    private boolean newNote;
    private boolean isBackPressed = false;

    @OnClick(R.id.save_note_option)
    void saveClickHandler() {
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
        listener();

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
                if (note != null) {
                    Log.d(TAG, "onChanged: " + note.getId());
                    noteDisplayEt.setText(note.getText());
                    noteTitleEt.setText(note.getTitle());
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            newNote = true;
        } else {
            setTitle("Edit Note");
            int noteId = extras.getInt(Constants.NOTE_ID_KEY);
            editorViewModel.loadData(noteId);
        }
    }


    @Override
    public void onBackPressed() {
        if (!isBackPressed) {
            if (TextUtils.isEmpty(noteDisplayEt.getText().toString()) && TextUtils.isEmpty(noteTitleEt.getText().toString())) {
                finish();
            } else {
                //make user double click to exit and give them warning to save or lose changes
                isBackPressed = true;
                Helper.makeSnackbar(coordinatorLayout, "Press back again to save & exit note");
//                Toast.makeText(this, "press back again to save and leave", Toast.LENGTH_SHORT)
//                .show();
            }
        } else {
            saveNote();
            finish();

        }
    }

    public void saveNote() {
        /*
         *Use helper to  make snackbar message for now. Look into RxJava to see if there is a way
         * to call a method to make the database call and give back a result in an on complete. The
         * on complete can be passed from the vm to the activity which would then result the message
         * on either success or failure
         */
        Helper.makeSnackbar(coordinatorLayout, "Note saved");
        editorViewModel.saveNote(noteDisplayEt.getText().toString(),
                noteTitleEt.getText().toString());
    }

    private void actionBarSetup() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
    }

    private void listener() {
        noteDisplayEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged: ");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: ");
                    saveNoteBtn.setEnabled(!TextUtils.isEmpty(noteDisplayEt.getText().toString()) && !TextUtils.isEmpty(noteTitleEt.getText().toString()));
                    deleteNoteBtn.setEnabled(!TextUtils.isEmpty(noteDisplayEt.getText().toString()) && !TextUtils.isEmpty(noteTitleEt.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: ");

            }
        });
    }
}
