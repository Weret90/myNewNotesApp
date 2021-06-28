package com.umbrella.noterecyclerview.update;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.umbrella.noterecyclerview.R;
import com.umbrella.noterecyclerview.domain.Note;

import java.util.Calendar;

public class UpdateNoteFragment extends Fragment {

    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String TAG = "UpdateNoteFragment";


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

        EditText title = view.findViewById(R.id.title);
        title.setText(note.getTitle());

        DatePicker datePicker = view.findViewById(R.id.picker);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(note.getDate());

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            }
        });
    }
}
