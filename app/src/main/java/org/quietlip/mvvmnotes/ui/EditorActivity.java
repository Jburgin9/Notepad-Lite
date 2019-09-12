package org.quietlip.mvvmnotes.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
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

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//TODO Auto save feature for notes, so even if the app crashes, after every keystroke it auto saves

public class EditorActivity extends AppCompatActivity {
    private static final String TAG = "JAFFY";
    private EditorViewModel editorViewModel;

    @BindView(R.id.note_display_edit_text)
    EditText noteDisplayEt;

    @BindView(R.id.delete_note_option)
    MaterialButton deleteNoteBtn;

    @BindView(R.id.save_note_option)
    MaterialButton saveNoteBtn;

    @BindView(R.id.bold_text_option)
    MaterialButton boldTextBtn;

    @BindView(R.id.editor_note_title_et)
    TextInputEditText noteTitleEt;

    @BindView(R.id.text_input_layout)
    TextInputLayout textInputLayout;

    @BindView(R.id.editor_coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    BottomSheetBehavior bottomSheetBehavior;

    private boolean newNote;
    private boolean isBackPressed = false;
    private Executor executor = Executors.newSingleThreadExecutor();

    @OnClick(R.id.save_note_option)
    void saveClickHandler() {
        saveNote();
        finish();
        //show animation during save and snackbar after success
    }

    @OnClick(R.id.delete_note_option)
    void deleteClickHandler() {
        if (TextUtils.isEmpty(noteDisplayEt.getText().toString()) &&
                TextUtils.isEmpty(noteTitleEt.getText().toString())) {
            finish();
        } else {
            deleteNote();
            finish();
        }
    }

    @OnClick(R.id.bold_text_option)
    void boldTextHandler() {
        Log.d(TAG, "boldTextHandler: ");

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        actionBarSetup();
        ButterKnife.bind(this);
        initViewModel();
        // listener();

        View bottomSheet = findViewById(R.id.editor_tray_bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                SpannableString str = new SpannableString(s);
//                Log.d(TAG, "boldOnTextChanged: " + str);
//                StyleSpan ss = new StyleSpan(Typeface.BOLD);
//                int endOfString = str.length();
//                str.setSpan(ss, noteDisplayEt.getSelectionStart(), endOfString, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                Log.d(TAG, "stringBold: " + str);
                Log.d(TAG, "onTextChanged: autosave");
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        saveNote();
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
                StyleSpan ss = new StyleSpan(Typeface.BOLD);
                int endOfString = s.length();
//                s.setSpan(ss, noteDisplayEt.getSelectionStart(), endOfString, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        };
        noteDisplayEt.addTextChangedListener(textWatcher);

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

    public void deleteNote() {
        editorViewModel.deleteNote();

        //Snackbar doesn't survive past Editor activity. Try implementing a coordinator layout
        //in main activity
        Helper.makeSnackbar(coordinatorLayout, "Note Deleted");
    }


    private void listener() {
        TextWatcher enableSaveAndDelete = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged: ");

            }

            /*
             *Bug... it only takes note field for the buttons to be active
             */

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: ");
                saveNoteBtn.setEnabled(!TextUtils.isEmpty(noteDisplayEt.getText().toString()) && !TextUtils.isEmpty(noteTitleEt.getText().toString()));
                deleteNoteBtn.setEnabled(!TextUtils.isEmpty(noteDisplayEt.getText().toString()) && !TextUtils.isEmpty(noteTitleEt.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: ");
                if (s.length() > 0) {
                    saveNoteBtn.setEnabled(true);
                    deleteNoteBtn.setEnabled(true);
                } else {
                    saveNoteBtn.setEnabled(false);
                    deleteNoteBtn.setEnabled(false);
                }
            }
        };

        noteDisplayEt.addTextChangedListener(enableSaveAndDelete);
    }

    private void actionBarSetup() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
    }

}
