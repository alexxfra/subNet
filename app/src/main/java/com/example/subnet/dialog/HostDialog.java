package com.example.subnet.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.subnet.R;

import java.util.ArrayList;

/**
 * @author Daniel
 * @version 1.0
 *
 * This class is used to create our dialog and manages the inflation of the dialog with needed views.
 */
public class HostDialog extends AppCompatDialogFragment{
    private DialogListener listener;
    private ArrayList<EditText> editTexts = new ArrayList<>();
    private ArrayList<Integer> networkSize = new ArrayList<>();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        int hostNum = getArguments().getInt("num");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        final LinearLayout populateMe = view.findViewById(R.id.populateVlsm);
        for(int i = 0; i<hostNum;i++){
            TextView tv = new TextView(view.getContext());
            tv.setText(getContext().getString(R.string.subnetNumber, Integer.toString(i+1)));
            populateMe.addView(tv);
            EditText editText = new EditText(view.getContext());
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editTexts.add(editText);
            populateMe.addView(editText);
        }

        builder.setView(view)
                .setTitle("Network")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            for (EditText editText : editTexts){
                                networkSize.add(Integer.parseInt(editText.getText().toString()));
                            }
                            listener.applyHosts(networkSize);
                        }
                        catch (Exception e){
                            Toast.makeText(getContext(),"Fields can't be empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch(ClassCastException e){
            throw new ClassCastException(context.toString() + "debug");
        }
    }

    public interface DialogListener {
        void applyHosts(ArrayList<Integer> networkSize);
    }
}
