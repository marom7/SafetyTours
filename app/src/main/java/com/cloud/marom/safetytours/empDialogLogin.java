package com.cloud.marom.safetytours;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;



public class empDialogLogin extends AppCompatDialogFragment {
    private EditText editTextEmpid;
    //private EditText editTextPassword;
    private ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialoglogin, null);

        builder.setView(view)
                .setTitle("כניסה למערכת")
                .setIcon(R.mipmap.safety_icon)
                .setNegativeButton("ביטול", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();

                    }
                })
                .setNeutralButton("כניסה", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String empid = editTextEmpid.getText().toString();
                        //String password = editTextPassword.getText().toString();

                        listener.applyTextEmpid(empid);
                         }
                });

        editTextEmpid = view.findViewById(R.id.edit_empid);
        //editTextPassword = view.findViewById(R.id.edit_password);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyTextEmpid(String empId);
    }
}