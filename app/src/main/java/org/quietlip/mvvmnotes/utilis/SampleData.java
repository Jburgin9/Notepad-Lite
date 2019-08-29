package org.quietlip.mvvmnotes.utilis;

import org.quietlip.mvvmnotes.model.Note;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleData {
    private static final String SAMPLE_TEXT_1  = "111111";
    private static final String SAMPLE_TEXT_2 = "2222222";
    private static final String SAMPLE_TEXT_3 = "3333333";

    private static Date getDate(int diff){
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MILLISECOND, diff);
        return cal.getTime();
    }

    public static List<Note> getNotes(){
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(getDate(0), SAMPLE_TEXT_1, "TEST 1"));
        notes.add(new Note(getDate(-1), SAMPLE_TEXT_2, "TEST 2"));
        notes.add(new Note(getDate(-2), SAMPLE_TEXT_3, "TEST 3"));
        return notes;
    }
}
