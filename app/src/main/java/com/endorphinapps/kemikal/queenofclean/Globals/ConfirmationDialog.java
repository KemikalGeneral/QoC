package com.endorphinapps.kemikal.queenofclean.Globals;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

public class ConfirmationDialog extends DialogFragment {

    /**
     * Message for the dialog
     */
    private String message;

    /**
     * Interface for the positive and negative button clicks
     */
    public interface ConfirmationDialogListener {
        void dialogPositiveClick(DialogFragment dialogFragment);

        void dialogNegativeClick(DialogFragment dialogFragment);
    }

    ConfirmationDialogListener confirmationDialogListener;

    /**
     * Get dialog message
     *
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set dialog message
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            confirmationDialogListener = (ConfirmationDialogListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement ConfirmationDialogListener");
        }
    }

    /**
     * Create and return a yes or no dialog box
     *
     * @param savedInstanceState
     * @return a dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getMessage())
                .setPositiveButton("Yes please!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        confirmationDialogListener.dialogPositiveClick(ConfirmationDialog.this);
                    }
                })
                .setNegativeButton("NO, thanks!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        confirmationDialogListener.dialogNegativeClick(ConfirmationDialog.this);
                    }
                });
        return builder.create();
    }
}
