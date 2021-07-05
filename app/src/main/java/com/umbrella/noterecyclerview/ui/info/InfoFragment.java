package com.umbrella.noterecyclerview.ui.info;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.umbrella.noterecyclerview.R;

public class InfoFragment extends Fragment {

    public static final String TAG = "InfoFragment";

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getChildFragmentManager().setFragmentResultListener(AlertDialogFragment.RESULT, getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Toast.makeText(requireContext(), "Positive", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.alert_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        view.findViewById(R.id.list_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListDialog();
            }
        });

        view.findViewById(R.id.single_choice_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleChoiceDialog();
            }
        });

        view.findViewById(R.id.multi_choice_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMultiChoiceDialog();
            }
        });

        view.findViewById(R.id.custom_view_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });

        view.findViewById(R.id.alert_dialog_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogFragment();
            }
        });

        view.findViewById(R.id.dialog_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFragment();
            }
        });
    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setTitle(R.string.alert_dialog_title)
                .setMessage(R.string.alert_dialog_message)
                .setIcon(R.drawable.ic_baseline_clear_24)
                .setCancelable(false)
                .setPositiveButton(R.string.btn_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(requireContext(), "Positive", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.btn_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(requireContext(), "Negative", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton(R.string.btn_neutral, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(requireContext(), "Neutral", Toast.LENGTH_SHORT).show();
                    }
                });

        builder.show();
    }

    public void showListDialog() {
        String[] items = getResources().getStringArray(R.array.options);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setTitle(R.string.alert_dialog_title)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(requireContext(), items[which], Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();
    }

    public void showSingleChoiceDialog() {
        String[] items = getResources().getStringArray(R.array.options);
        int[] selected = {-1};
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setSingleChoiceItems(items, selected[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selected[0] = which;
                    }
                })
                .setPositiveButton(R.string.select, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (selected[0] != -1) {
                            Toast.makeText(requireContext(), items[selected[0]], Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        builder.show();
    }

    public void showMultiChoiceDialog() {
        String[] items = getResources().getStringArray(R.array.options);
        boolean[] checked = new boolean[items.length];
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setMultiChoiceItems(items, checked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checked[which] = isChecked;
                    }
                })
                .setPositiveButton(R.string.select, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < checked.length; i++) {
                            if (checked[i]) {
                                stringBuilder.append("[" + items[i] + "] ");
                            }
                        }
                        Toast.makeText(requireContext(), stringBuilder, Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();
    }

    public void showCustomDialog() {
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit, null, false);
        EditText editText = view.findViewById(R.id.edit_name);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setTitle(R.string.notes_title)
                .setView(view)
                .setPositiveButton(R.string.select, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(requireContext(), editText.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();
    }

    public void showAlertDialogFragment() {
        AlertDialogFragment.newInstance(R.string.alert_dialog_title)
                .show(getChildFragmentManager(), CustomDialogFragment.TAG);

    }

    public void showDialogFragment() {
        CustomDialogFragment.newInstance()
                .show(getChildFragmentManager(), AlertDialogFragment.TAG);
    }
}

