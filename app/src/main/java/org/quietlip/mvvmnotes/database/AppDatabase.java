package org.quietlip.mvvmnotes.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import org.quietlip.mvvmnotes.utilis.DateConverter;
import org.quietlip.mvvmnotes.model.Note;

@Database(entities = Note.class, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "NotesDatabase.db";
    private static volatile AppDatabase instance;
    public static final Object LOCK = new Object();

    public abstract NoteDao noteDao();

    public static AppDatabase getInstance(Context context) {
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
