package com.umbrella.noterecyclerview.update;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.umbrella.noterecyclerview.R;
import com.umbrella.noterecyclerview.RouterHolder;
import com.umbrella.noterecyclerview.domain.Note;
import com.umbrella.noterecyclerview.domain.NotesRepository;
import com.umbrella.noterecyclerview.domain.NotesRepositoryImplementation;
import com.umbrella.noterecyclerview.ui.MainRouter;

import java.util.Calendar;
import java.util.Date;

public class UpdateNoteFragment extends Fragment {

    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String TAG = "UpdateNoteFragment";
    public static final String UPDATE_RESULT = "UPDATE_RESULT";
    private final NotesRepository repository = NotesRepositoryImplementation.INSTANCE;
    private int selectedYear = -1;
    private int selectedMonthYear = -1;
    private int selectedDayOfMonth = -1;


    public static UpdateNoteFragment newInstance(Note note) {
        UpdateNoteFragment fragment = new UpdateNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Note note = getArguments().getParcelable(ARG_NOTE);

        Toolbar toolbar = view.findViewById(R.id.toolbar);

        EditText title = view.findViewById(R.id.title);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_done) {
                    Date selectedDate = null;
                    if (selectedYear != -1 && selectedDayOfMonth != -1 && selectedMonthYear != -1) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(note.getDate());
                        calendar.set(Calendar.YEAR, selectedYear);
                        calendar.set(Calendar.MONTH, selectedMonthYear);
                        calendar.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth);
                        selectedDate = calendar.getTime();
                    }
                    Note result = repository.update(note, title.getText().toString(), selectedDate);
                    if (requireActivity() instanceof RouterHolder) {
                        MainRouter router = ((RouterHolder) getActivity()).getMainRouter();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(ARG_NOTE, result);
                        getParentFragmentManager().setFragmentResult(UPDATE_RESULT, bundle);
                        router.back();
                    }
                    return true;
                }
                return false;
            }
        });


        title.setText(note.getTitle());

        DatePicker datePicker = view.findViewById(R.id.picker);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(note.getDate());

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                selectedYear = year;
                selectedDayOfMonth = monthOfYear;
                selectedDayOfMonth = dayOfMonth;
            }
        });
    }
}
