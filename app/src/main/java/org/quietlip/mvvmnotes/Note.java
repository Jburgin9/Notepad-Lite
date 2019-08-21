package org.quietlip.mvvmnotes;

import androidx.room.Entity;

import java.util.Date;

@Entity
public class Note {
    private int id;
    private Date date;
    private String text;

    public Note() {}

    public Note(Date date, String text) {
        this.date = date;
        this.text = text;
    }

    public Note(int id, Date date, String text) {
        this.id = id;
        this.date = date;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", date=" + date +
                ", text='" + text + '\'' +
                '}';
    }
}
