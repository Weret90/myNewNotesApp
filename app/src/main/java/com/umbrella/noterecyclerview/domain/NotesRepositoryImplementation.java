package com.umbrella.noterecyclerview.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NotesRepositoryImplementation implements NotesRepository {

    public static final NotesRepository INSTANCE = new NotesRepositoryImplementation();
    ArrayList<Note> notes = new ArrayList<>();

    public NotesRepositoryImplementation() {
        notes.add(new Note("id1", "Title1", "https://cdn.pixabay.com/photo/2021/06/16/21/46/parrot-6342271_960_720.jpg", new Date()));
        notes.add(new Note("id2", "Title2", "https://cdn.pixabay.com/photo/2021/06/20/08/28/wheat-grass-6350274_960_720.jpg", new Date()));
        notes.add(new Note("id3", "Title3", "https://cdn.pixabay.com/photo/2021/04/05/14/55/mosque-6153752_960_720.jpg", new Date()));
        notes.add(new Note("id4", "Title4", "https://cdn.pixabay.com/photo/2019/08/08/11/42/butterfly-4392802_960_720.jpg", new Date()));
        notes.add(new Note("id5", "Title5", "https://cdn.pixabay.com/photo/2021/05/10/10/46/yellow-wall-6243164_960_720.jpg", new Date()));
        notes.add(new Note("id6", "Title6", "https://cdn.pixabay.com/photo/2020/05/12/16/16/raspberries-5163781_960_720.jpg", new Date()));
        notes.add(new Note("id7", "Title7", "https://cdn.pixabay.com/photo/2017/06/05/07/59/octopus-2373177_960_720.png", new Date()));
    }

    @Override
    public List<Note> getNotes() {
        return notes;
    }

    @Override
    public void clear() {
        notes.clear();
    }

    @Override
    public Note add(String title, String imageUrl) {
        Note note = new Note(UUID.randomUUID().toString(), title, imageUrl, new Date());
        notes.add(note);
        return note;
    }

    @Override
    public Note update(Note note, String title, Date date) {
        for (int i = 0; i < notes.size(); i++) {
            Note item = notes.get(i);
            if (item.getId().equals(note.getId())) {
                String titleToSet = item.getTitle();
                Date dateToSet = item.getDate();
                if (title != null) {
                    titleToSet = title;
                }
                if (date != null) {
                    dateToSet = date;
                }
                Note newNote = new Note(note.getId(), titleToSet, note.getUrl(), dateToSet);
                notes.remove(i);
                notes.add(i, newNote);
                return newNote;
            }
        }
        return note;
    }

    @Override
    public void remove(Note note) {
        notes.remove(note);
    }
}
