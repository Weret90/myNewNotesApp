package com.umbrella.noterecyclerview.ui.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.umbrella.noterecyclerview.R;
import com.umbrella.noterecyclerview.domain.Note;
import com.umbrella.noterecyclerview.domain.NotesRepository;
import com.umbrella.noterecyclerview.domain.NotesRepositoryImplementation;
import com.umbrella.noterecyclerview.ui.MainActivity;

import java.util.Collections;
import java.util.List;

public class NotesFragment extends Fragment {

    public static final String TAG = "NotesFragment";
    private NotesRepository repository = NotesRepositoryImplementation.INSTANCE;
    private NotesAdapter notesAdapter;

    private int longClickedIndex;
    private Note longClickedNote;

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notesAdapter = new NotesAdapter(this);
        notesAdapter.setListener(new NotesAdapter.OnNoteClickedListener() {
            @Override
            public void onNoteClickedListener(@NonNull Note note) {
                if (requireActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) requireActivity();
                    mainActivity.getRouter().showNoteDetail(note);
                }
                // Toast.makeText(requireContext(), note.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        notesAdapter.setLongClickedListener(new NotesAdapter.OnNoteLongClickedListener() {
            @Override
            public void onNoteLongClickedListener(Note note, int index) {
                longClickedIndex = index;
                longClickedNote = note;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        RecyclerView notesList = view.findViewById(R.id.notes_list);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_add) {
                    Note addNote = repository.add("This is new added Title", "https://cdn.pixabay.com/photo/2021/05/10/10/46/yellow-wall-6243164_960_720.jpg");
                    int index = notesAdapter.add(addNote);
                    notesAdapter.notifyItemInserted(index);  //обеспечивает анимацию при добавлении
                    notesList.scrollToPosition(index);
                    return true;
                }
                if (item.getItemId() == R.id.action_clear) {
                    repository.clear();
                    notesAdapter.setData(Collections.emptyList());
                    notesAdapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });

        // notesList.setLayoutManager(new LinearLayoutManager(requireContext()));
        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
        notesList.setLayoutManager(manager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), manager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_separator));

        notesList.addItemDecoration(dividerItemDecoration);

        List<Note> notes = repository.getNotes();

        notesAdapter.setData(notes);

        notesList.setAdapter(notesAdapter);

        notesAdapter.notifyDataSetChanged();

//        for (Note note : notes) {
//            View itemView = LayoutInflater.from(requireContext()).inflate(R.layout.item_note, containerView, false);
//
//            TextView title = itemView.findViewById(R.id.title);
//            ImageView image = itemView.findViewById(R.id.image);
//
//            title.setText(note.getTitle());
//            Glide.with(this)
//                    .load(note.getUrl())
//                    .centerCrop()
//                    .into(image);
//
//            containerView.addView(itemView);
//        }
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.menu_notes_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_update) {
            return true;
        }
        if (item.getItemId() == R.id.action_delete) {
            repository.remove(longClickedNote);
            notesAdapter.remove(longClickedNote);
            notesAdapter.notifyItemRemoved(longClickedIndex);  //для анимации можно др ты знаешь)
            return true;
        }
        return super.onContextItemSelected(item);
    }
}