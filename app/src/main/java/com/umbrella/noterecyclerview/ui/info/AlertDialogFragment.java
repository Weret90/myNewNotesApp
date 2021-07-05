package com.umbrella.noterecyclerview.ui.info;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.umbrella.noterecyclerview.R;

public class AlertDialogFragment extends DialogFragment {

    public static final String TAG = "AlertDialogFragment";
    public static final String RESULT = "RESULT";
    public static final String ARG_TITLE = "ARG_TITLE";

    public static AlertDialogFragment newInstance(int title) {
        AlertDialogFragment fragment = new AlertDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setTitle(getArguments().getInt(ARG_TITLE))
                .setMessage(R.string.alert_dialog_message)
                .setIcon(R.drawable.ic_baseline_clear_24)
                .setCancelable(false)
                .setPositiveButton(R.string.btn_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getParentFragmentManager().setFragmentResult(RESULT, null);
                    }
                });
//                .setNegativeButton(R.string.btn_negative, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(requireContext(), "Negative", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setNeutralButton(R.string.btn_neutral, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(requireContext(), "Neutral", Toast.LENGTH_SHORT).show();
//                    }
//                });
        return builder.create();
    }
}
