package spoklab.app.spoktools.staticc.utils;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

public final class ViewUtils {

    @NonNull
    public static Button buttonClose(
        @NonNull Context context
    ) {
        final Button btnClose = new
            Button(context);

        btnClose.setText("Close");
        btnClose.setLayoutParams(new ViewGroup.LayoutParams(
            -2, // wrap content
            -2
        ));

        return btnClose;
    }

}
