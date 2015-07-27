package com.projects.dfg_team.weather;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by georgetsd on 26/7/15.
 */
public class AlertDialogFragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.error_title).setMessage(R.string.error_message).setPositiveButton(R.string.error_ok_button_text, null);

        AlertDialog dialog = builder.create();

        return dialog;
    }

    /*public void updateValues(String error_title, String error_message, String ok_button_text){
        error_title = R.string.error_title;
        error_message = R.string.error_message;
        ok_button_text = R.string.error_ok_button_text;
    }
*/
}
