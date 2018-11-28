package com.team7.cmput301.android.theirisproject.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.R;

/**
 * Dialog fragment that confirms with the user if the selected location is the location to set
 * for the record
 *
 * @author VinnyLuu
 */
public class AddGeoLocationDialogFragment extends DialogFragment {


    private AddGeoLocationDialogListener listener;

    // Interface that communicates with calling activity on the option the user has selected
    public interface AddGeoLocationDialogListener {
        void onFinishGeoLocation(boolean success);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        listener = (AddGeoLocationDialogListener) getActivity();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(R.string.geolocation_frag_title)
        .setMessage(R.string.geolocation_frag_message)
        .setPositiveButton(R.string.geolocation_frag_confirm_message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onFinishGeoLocation(true);
                dialog.dismiss();
            }
        })
        .setNegativeButton(R.string.geolocation_frag_deny_message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onFinishGeoLocation(false);
                dialog.dismiss();
            }
        }).create();
        return alertDialogBuilder.create();
    }
}
