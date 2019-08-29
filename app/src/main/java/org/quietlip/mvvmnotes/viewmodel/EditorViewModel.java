package org.quietlip.mvvmnotes.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.quietlip.mvvmnotes.database.NoteRepository;
import org.quietlip.mvvmnotes.model.Note;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditorViewModel extends AndroidViewModel {
    private static final String TAG = "EDITOR";

    public MutableLiveData<Note> liveNote;
    private NoteRepository repository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public EditorViewModel(@NonNull Application application) {
        super(application);
        liveNote = new MutableLiveData<>();
        repository = NoteRepository.getInstance(getApplication());
    }

    public void loadData(int noteId){
        executor.execute(() -> {
            Note note = repository.getNoteById(noteId);
            liveNote.postValue(note);
        });
    }

}
