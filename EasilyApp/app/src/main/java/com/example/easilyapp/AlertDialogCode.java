package com.example.easilyapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AlertDialogCode extends DialogFragment {

    private EditText editText;
    private String code;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.alert_dialog_aluno, null);
        editText = view.findViewById(R.id.edit_text_codigo_aluno);

        AlertDialog.Builder alertaDiologBuilder = new AlertDialog.Builder(getActivity());
        alertaDiologBuilder.setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        code = editText.getText().toString();
                        Log.i("Codigo", code);


                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });


        return alertaDiologBuilder.create();
    }

    public String getCode() {
        return code;
    }

}
