package com.umbrella.noterecyclerview.domain;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotesFireStoreReporitory implements NotesRepository {

    public static final NotesRepository INSTANCE = new NotesFireStoreReporitory();

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private final static String NOTES = "notes";
    private final static String DATE = "date";
    private final static String TITLE = "title";
    private final static String IMAGE = "image";

    @Override
    public void getNotes(Callback<List<Note>> callback) {
        firebaseFirestore.collection(NOTES)
                .orderBy(DATE, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<Note> result = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String title = (String) document.get(TITLE);
                                String image = (String) document.get(IMAGE);
                                Date date = ((Timestamp) document.get(DATE)).toDate();
                                result.add(new Note(document.getId(), title, image, date));
                            }
                            callback.onSuccess(result);
                        } else {
                            task.getException();
                        }
                    }
                });
    }

    @Override
    public void clear() {

    }

    @Override
    public Note add(String title, String imageUrl) {
        return null;
    }

    @Override
    public void remove(Note note) {

    }

    @Override
    public Note update(Note note, String title, Date date) {
        return null;
    }
}
