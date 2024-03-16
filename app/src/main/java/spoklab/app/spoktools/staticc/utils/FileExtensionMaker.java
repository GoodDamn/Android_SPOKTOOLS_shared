package spoklab.app.spoktools.staticc.utils;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import spoklab.app.spoktools.Application;
import spoklab.app.spoktools.models.fileExtensions.FileSCS;
import spoklab.app.spoktools.models.fileExtensions.FileSPC;

public final class FileExtensionMaker {

    @Nullable
    public static byte[] mkSPC(
        @NonNull FileSPC spc
    ) {
        if (spc.image == null) {
            return null;
        }

        final byte[] title = StringUtils
            .getBytes(
                spc.title
            );

        final byte[] desc = StringUtils
            .getBytes(
                spc.description
            );

        final byte premium = (byte) (
            spc.isPremium ? 1 : 0
        );

        final byte config = (byte) (
            premium << 6 | (spc.categoryId & 0x3f)
        );

        final ByteArrayOutputStream out = new
            ByteArrayOutputStream();

        out.write(
            config
        );

        try {
            out.write(
                ByteUtils.integer(
                    spc.color
                )
            );

            out.write(ByteUtils
                .shortt(
                    desc.length
                )
            );

            out.write(
                desc
            );

            out.write(ByteUtils
                .shortt(
                    title.length
                )
            );

            out.write(
                title
            );

            spc.image.compress(Bitmap.CompressFormat
                    .PNG,
                99,
                out
            );

            final byte[] result = out
                .toByteArray();

            out.close();

            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static byte[] mkSCS(
        @NonNull FileSCS scs
    ) {
        final byte[] title = StringUtils
            .getBytes(scs.title);

        if (title.length > 255) {
            return null;
        }

        final ByteArrayOutputStream baos = new
            ByteArrayOutputStream();

        try {
            baos.write(
                scs.cardType
            );

            baos.write(
                title.length
            );

            baos.write(
                title
            );

            baos.write(ByteUtils
                .integer(
                    scs.topics.size() * 2 // bytes
                )
            );

            for (Integer id: scs.topics) {
                baos.write(ByteUtils
                    .shortt(
                        id
                    )
                );
            }

            final byte[] result = baos.toByteArray();
            baos.close();

            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
