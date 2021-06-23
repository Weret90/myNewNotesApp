package com.umbrella.noterecyclerview.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.umbrella.noterecyclerview.R;
import com.umbrella.noterecyclerview.ui.info.InfoFragment;
import com.umbrella.noterecyclerview.ui.notes.NotesFragment;

public class MainRouter {

    private FragmentManager fragmentManager;

    public MainRouter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void showNotes() {
        fragmentManager.beginTransaction().replace(R.id.container, NotesFragment.newInstance(), NotesFragment.TAG).commit();
    }

    public void showInfo() {
        fragmentManager.beginTransaction().replace(R.id.container, InfoFragment.newInstance(), InfoFragment.TAG).commit();
    }
}
