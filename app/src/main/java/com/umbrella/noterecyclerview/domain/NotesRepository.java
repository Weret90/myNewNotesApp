package com.umbrella.noterecyclerview.domain;

import java.util.Date;
import java.util.List;

public interface NotesRepository {
    void getNotes(Callback<List<Note>> callback);

    void clear();

    void add(String title, String imageUrl, Callback<Note> callback);

    void remove(Note note, Callback<Object> callback);

    Note update(Note note, String title, Date date);
}
