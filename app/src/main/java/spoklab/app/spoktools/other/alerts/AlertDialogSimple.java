package spoklab.app.spoktools.other.alerts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public final class AlertDialogSimple
extends DialogFragment {

    @Nullable
    private String message;

    @Nullable
    private String messagePositive;

    @Nullable
    private String messageNegative;

    @Nullable
    private DialogInterface.OnClickListener mPositiveListener;

    @Nullable
    private DialogInterface.OnClickListener mNegativeListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(
        @Nullable Bundle savedInstanceState
    ) {
        final Context context = getContext();

        final AlertDialog.Builder builder = new
            AlertDialog.Builder(context);

        return builder.setMessage(message)
            .setPositiveButton(
                messagePositive,
                mPositiveListener
            ).setNegativeButton(
                messageNegative,
                mNegativeListener
            ).create();
    }

    public void setPositiveButton(
        @NonNull String title,
        @Nullable DialogInterface.OnClickListener listener
    ) {
        messagePositive = title;
        mPositiveListener = listener;
    }

    public void setNegativeButton(
        @NonNull String title,
        @Nullable DialogInterface.OnClickListener listener
    ) {
        messageNegative = title;
        mNegativeListener = listener;
    }

    public void setMessage(
        @NonNull String msg
    ) {
        message = msg;
    }

    public static AlertDialogSimple create(
        @NonNull String message,
        @Nullable String msgNegative,
        @Nullable DialogInterface.OnClickListener listener
    ) {
        final AlertDialogSimple alert = new
            AlertDialogSimple();

        alert.message = message;
        alert.messageNegative = msgNegative;
        alert.mNegativeListener = listener;
        return alert;
    }

}
