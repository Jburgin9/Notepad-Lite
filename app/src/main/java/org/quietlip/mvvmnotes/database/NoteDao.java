package org.quietlip.mvvmnotes.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import org.quietlip.mvvmnotes.model.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNotes(List<Note> notes);

    @Delete
    void deleteNote(Note note);

    @Query("SELECT * FROM notes WHERE id = :id")
    Note getNotebyId(int id);

    @Query("SELECT * FROM notes ORDER BY date DESC")
    LiveData<List<Note>> getAllNotes();

    @Query("DELETE FROM notes")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM notes")
    int getCount();



}
