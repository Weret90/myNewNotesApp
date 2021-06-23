package com.umbrella.noterecyclerview.ui.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.umbrella.noterecyclerview.R;
import com.umbrella.noterecyclerview.domain.Note;
import com.umbrella.noterecyclerview.domain.NotesRepository;
import com.umbrella.noterecyclerview.domain.NotesRepositoryImplementation;

import java.util.List;

public class NotesFragment extends Fragment {

    public static final String TAG = "NotesFragment";

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    private NotesRepository repository = NotesRepositoryImplementation.INSTANCE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout containerView = view.findViewById(R.id.notes_container);
        List<Note> notes = repository.getNotes();
        for (Note note : notes) {
            View itemView = LayoutInflater.from(requireContext()).inflate(R.layout.item_note, containerView, false);

            TextView title = itemView.findViewById(R.id.title);
            ImageView image = itemView.findViewById(R.id.image);

            title.setText(note.getTitle());
            Glide.with(this)
                    .load(note.getUrl())
                    .centerCrop()
                    .into(image);

            containerView.addView(itemView);
        }
    }
}