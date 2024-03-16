package spoklab.app.spoktools.staticc.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;

public final class BitmapUtils {

    @Nullable
    public static Bitmap getBitmapUri(
        @NonNull Context context,
        @NonNull Uri uri
    ) {
        final ContentResolver resolver = context
            .getContentResolver();

        if (resolver == null) {
            return null;
        }

        InputStream is = null;
        Bitmap bitmapResult = null;

        try {
            is = resolver
                .openInputStream(uri);

            bitmapResult = BitmapFactory
                .decodeStream(is);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (is != null) {
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmapResult;
    }

}
