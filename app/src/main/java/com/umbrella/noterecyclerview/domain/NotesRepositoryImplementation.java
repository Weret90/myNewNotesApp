package com.umbrella.noterecyclerview.domain;

import java.util.ArrayList;
import java.util.List;

public class NotesRepositoryImplementation implements NotesRepository {

    public static final NotesRepository INSTANCE = new NotesRepositoryImplementation();

    @Override
    public List<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        notes.add(new Note("id1", "Title1", "https://cdn.pixabay.com/photo/2021/06/16/21/46/parrot-6342271_960_720.jpg"));
        notes.add(new Note("id2", "Title2", "https://cdn.pixabay.com/photo/2021/06/20/08/28/wheat-grass-6350274_960_720.jpg"));
        notes.add(new Note("id3", "Title3", "https://cdn.pixabay.com/photo/2021/04/05/14/55/mosque-6153752_960_720.jpg"));
        notes.add(new Note("id4", "Title4", "https://cdn.pixabay.com/photo/2019/08/08/11/42/butterfly-4392802_960_720.jpg"));
        notes.add(new Note("id5", "Title5", "https://cdn.pixabay.com/photo/2021/05/10/10/46/yellow-wall-6243164_960_720.jpg"));
        notes.add(new Note("id6", "Title6", "https://cdn.pixabay.com/photo/2020/05/12/16/16/raspberries-5163781_960_720.jpg"));
        notes.add(new Note("id7", "Title7", "https://cdn.pixabay.com/photo/2017/06/05/07/59/octopus-2373177_960_720.png"));

        for (int i = 0; i < 1000; i++) {
            notes.add(new Note("id" + (8 + i), "Title" + (8 + i), "https://cdn.pixabay.com/photo/2017/06/05/07/59/octopus-2373177_960_720.png"));
        }

        return notes;
    }
}
