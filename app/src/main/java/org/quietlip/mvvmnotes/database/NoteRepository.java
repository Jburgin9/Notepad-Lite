package org.quietlip.mvvmnotes.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import org.quietlip.mvvmnotes.model.Note;
import org.quietlip.mvvmnotes.utilis.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NoteRepository {
    private static NoteRepository instance;
    public LiveData<List<Note>> notes;
    private AppDatabase database;
    private Executor executor = Executors.newSingleThreadExecutor();

    private NoteRepository(Context context) {
        database = AppDatabase.getInstance(context);
        notes = getAllNotes();
    }

    public static NoteRepository getInstance(Context context) {
        if (instance == null) {
            instance = new NoteRepository(context);
        }
        return instance;
    }

    private LiveData<List<Note>> getAllNotes() {
        return database.noteDao().getAllNotes();
    }

    public void addSampleData() {
        executor.execute(() ->
                database.noteDao().insertNotes(SampleData.getNotes()));
    }

    public void deleteAllNotes() {
        executor.execute(() ->
                database.noteDao().deleteAll());
    }

    public Note getNoteById(int noteId) {
        return database.noteDao().getNotebyId(noteId);
    }

    public void insertNote(Note note) {
        executor.execute(() -> database.noteDao().insertNote(note));
    }

    public void deleteNote(Note note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.noteDao().deleteNote(note);
            }
        });
    }

}
