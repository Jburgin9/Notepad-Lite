package org.quietlip.mvvmnotes;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quietlip.mvvmnotes.database.AppDatabase;
import org.quietlip.mvvmnotes.database.NoteDao;
import org.quietlip.mvvmnotes.model.Note;
import org.quietlip.mvvmnotes.utilis.SampleData;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private static final String TAG = "JUnit";
    private AppDatabase appDatabase;
    private NoteDao noteDao;

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        noteDao = appDatabase.noteDao();
        Log.d(TAG, "createDb: ");
    }

    @After
    public void closeDb(){
        appDatabase.close();
        Log.d(TAG, "closeDb: ");
    }

    @Test
    public void createAndRetrieveNotes(){
        noteDao.insertNotes(SampleData.getNotes());
        int count = noteDao.getCount();
        Log.d(TAG, "createAndRetrieveNotes: " + count);
        assertEquals(SampleData.getNotes().size(), count);
    }

    @Test
    public void compareStrings(){
        noteDao.insertNotes(SampleData.getNotes());
        Note original = SampleData.getNotes().get(0);
        Note fromDb = noteDao.getNotebyId(1);
        assertEquals(original.getText(), fromDb.getText());
        assertEquals(1, fromDb.getId());
    }
}
