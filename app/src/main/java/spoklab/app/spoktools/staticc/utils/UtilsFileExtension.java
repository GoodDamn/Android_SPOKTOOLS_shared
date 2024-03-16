package spoklab.app.spoktools.staticc.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import spoklab.app.spoktools.layoutParams.BaseLayoutParams;
import spoklab.app.spoktools.models.fileExtensions.FileSCS;
import spoklab.app.spoktools.models.fileExtensions.FileSPC;

public final class UtilsFileExtension {

    private static final String TAG = "UtilsFileExtension";

    private static final BitmapFactory.Options mBitmapOptions = new
            BitmapFactory.Options();

    @NonNull
    public static FileSPC spc(
            byte[] data,
            BaseLayoutParams decodedSize
    ) {
        final byte config = data[0];
        final boolean isPremium = (config & 0xff) >> 6 == 1;
        final byte categoryId = (byte) (config & 0x3f);

        @ColorInt
        final int color = ByteUtils
            .integer(
                data,
                1
            );

        final short descLen = ByteUtils
            .shortt(
                data,
                5
            );

        int pos = 7 + descLen;

        final String description = new String(
            data,
            7,
            descLen,
            StandardCharsets.UTF_8
        );

        final short titleLen = ByteUtils
            .shortt(data, pos);

        pos += 2;

        final String title = new String(
            data,
            pos,
            titleLen,
            StandardCharsets.UTF_8
        );

        pos += titleLen;

        mBitmapOptions.inJustDecodeBounds = false;
        mBitmapOptions.inSampleSize = 2;
        mBitmapOptions.outWidth = decodedSize.width;
        mBitmapOptions.outHeight = decodedSize.height;

        final Bitmap bitmap = BitmapFactory
            .decodeByteArray(
                data,
                pos,
                data.length - pos,
                mBitmapOptions
            );

        final Bitmap upScaledBitmap = Bitmap
            .createScaledBitmap(
                bitmap,
                decodedSize.width,
                decodedSize.height,
                false
            );

        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }

        return new FileSPC(
            title,
            description,
            upScaledBitmap,
            isPremium,
            categoryId,
            color
        );
    }

    @NonNull
    public static FileSCS scs(
            byte[] data
    ) {

        byte cardType = data[0];

        final byte titleLen = data[1];

        final String title = new String(
            data,
            2,
            titleLen,
            StandardCharsets.UTF_8
        );

        int pos = 2 + titleLen;

        final int topicsLen = ByteUtils
            .integer(
                data,
                pos
            );

        pos += 4;

        final ArrayList<Integer> topics = new ArrayList<>();
        final int b = pos + topicsLen;

        while (pos < b) {
            topics.add((int) ByteUtils
                .shortt(
                    data,
                    pos
                )
            );

            pos += 2;
        }

        return new FileSCS(
            title,
            topics,
            cardType
        );
    }

}
