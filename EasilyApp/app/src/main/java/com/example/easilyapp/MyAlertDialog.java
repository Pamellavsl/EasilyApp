package com.example.easilyapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class MyAlertDialog {

    private EditText editTextCode;
    private String code;
    private AlertDialog alertDialog;

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }

    public String getCode() {
        return code;
    }

    public MyAlertDialog() {
    }

    public void createDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.alert_dialog_aluno, null);
        editTextCode = view.findViewById(R.id.edit_text_codigo_aluno);

        builder.setView(view)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setCode(editTextCode.getText().toString());
                    }
                })
                .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog = builder.create();
    }

    private void setCode(String code){
        this.code = code;
    }
}
