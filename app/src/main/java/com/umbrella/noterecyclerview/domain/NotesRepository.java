package com.umbrella.noterecyclerview.domain;

import java.util.List;

public interface NotesRepository {
    List<Note> getNotes();

    void clear();

    Note add(String title, String imageUrl);

    void remove(Note note);
}
