package org.quietlip.mvvmnotes;

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
        notes.add(new Note(1, getDate(0), SAMPLE_TEXT_1));
        notes.add(new Note(2, getDate(-1), SAMPLE_TEXT_2));
        notes.add(new Note(3, getDate(-2), SAMPLE_TEXT_3));
        return notes;
    }
}
