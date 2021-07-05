package com.umbrella.noterecyclerview.ui.notes;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.umbrella.noterecyclerview.R;
import com.umbrella.noterecyclerview.RouterHolder;
import com.umbrella.noterecyclerview.domain.Callback;
import com.umbrella.noterecyclerview.domain.Note;
import com.umbrella.noterecyclerview.domain.NotesFireStoreReporitory;
import com.umbrella.noterecyclerview.domain.NotesRepository;
import com.umbrella.noterecyclerview.ui.MainRouter;
import com.umbrella.noterecyclerview.update.UpdateNoteFragment;

import java.util.Collections;
import java.util.List;

public class NotesFragment extends Fragment {

    public static final String TAG = "NotesFragment";
    private NotesRepository repository = NotesFireStoreReporitory.INSTANCE;
    private NotesAdapter notesAdapter;

    private int longClickedIndex;
    private Note longClickedNote;

    private boolean isLoading = false;

    private ProgressBar progressBar;

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notesAdapter = new NotesAdapter(this);

        isLoading = true;

        repository.getNotes(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> result) {
                notesAdapter.setData(result);
                notesAdapter.notifyDataSetChanged();

                isLoading = false;

                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        notesAdapter.setListener(new NotesAdapter.OnNoteClickedListener() {
            @Override
            public void onNoteClickedListener(@NonNull Note note) {
                if (requireActivity() instanceof RouterHolder) {
                    MainRouter router = ((RouterHolder) requireActivity()).getMainRouter();
                    router.showNoteDetail(note);
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

        getParentFragmentManager().setFragmentResultListener(UpdateNoteFragment.UPDATE_RESULT, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if (result.containsKey(UpdateNoteFragment.ARG_NOTE)) {
                    Note note = result.getParcelable(UpdateNoteFragment.ARG_NOTE);
                    notesAdapter.update(note);
                    notesAdapter.notifyItemChanged(longClickedIndex);
                }
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

//        DefaultItemAnimator animator = new DefaultItemAnimator();
//        animator.setAddDuration(5000L);
//        animator.setRemoveDuration(7000L);
//        notesList.setItemAnimator(animator);  длительность анимаций

        progressBar = view.findViewById(R.id.progress);
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_add) {
                    repository.add("This is new added Title", "https://cdn.pixabay.com/photo/2021/05/10/10/46/yellow-wall-6243164_960_720.jpg", new Callback<Note>() {
                        @Override
                        public void onSuccess(Note result) {
                            int index = notesAdapter.add(result);
                            notesAdapter.notifyItemInserted(index);  //обеспечивает анимацию при добавлении
                            notesList.scrollToPosition(index);
                        }
                    });

                    return true;
                }
                if (item.getItemId() == R.id.action_clear) {
                    showDeleteAllNotesAlertDialog();
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
            if (requireActivity() instanceof RouterHolder) {
                MainRouter router = ((RouterHolder) requireActivity()).getMainRouter();
                router.showEditNote(longClickedNote);
            }
            return true;
        }
        if (item.getItemId() == R.id.action_delete) {
            showDeleteOneNoteAlertDialog();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    public void showDeleteOneNoteAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setTitle(R.string.alert_dialog_delete)
                .setMessage(R.string.alert_dialog_delete_message)
                .setCancelable(false)
                .setPositiveButton(R.string.btn_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        repository.remove(longClickedNote, new Callback<Object>() {
                            @Override
                            public void onSuccess(Object result) {
                                notesAdapter.remove(longClickedNote);
                                notesAdapter.notifyItemRemoved(longClickedIndex);
                            }
                        });
                    }
                })
                .setNegativeButton(R.string.btn_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(requireContext(), "Удаление отменено", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();
    }

    private void showDeleteAllNotesAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setTitle(R.string.alert_dialog_delete_all)
                .setMessage(R.string.alert_dialog_delete_all_message)
                .setCancelable(false)
                .setPositiveButton(R.string.btn_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        repository.clear();
                        notesAdapter.setData(Collections.emptyList());
                        notesAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.btn_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(requireContext(), "Удаление отменено", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();
    }
}