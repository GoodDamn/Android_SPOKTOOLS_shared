package spoklab.app.spoktools.staticc.utils;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import spoklab.app.spoktools.Application;

public final class StringUtils {

    @NonNull
    public static String fileNameFrom(
        @NonNull Uri uri
    ) {
        final String p = uri.getPath();
        final String subPath = p
            .substring(p.indexOf(":"));

        final int nameIndex = subPath.lastIndexOf("/");

        return subPath
            .substring(
                nameIndex == -1 ?
                    1
                  : nameIndex + 1
                );
    }

    @NonNull
    public static byte[] getBytes(
        @Nullable String input
    ) {
        return input == null ?
            Application.SINGLE_BYTE
          : input.getBytes(
               Application.UTF_8
            );
    }

}
