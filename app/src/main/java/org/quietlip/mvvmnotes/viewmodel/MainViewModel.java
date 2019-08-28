package org.quietlip.mvvmnotes.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.quietlip.mvvmnotes.database.NoteRepository;
import org.quietlip.mvvmnotes.model.Note;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    public LiveData<List<Note>> notes;
    private NoteRepository noteRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        noteRepository = NoteRepository.getInstance(getApplication());
        notes = noteRepository.notes;
    }

    public void addSampleData(){
        noteRepository.addSampleData();
    }

    public void deleteAllNotes(){
        noteRepository.deleteAllNotes();
    }

}
