package spoklab.app.spoktools.staticc;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import spoklab.app.spoktools.Application;
import spoklab.app.spoktools.enums.CardType;
import spoklab.app.spoktools.models.fileExtensions.FileSCS;
import spoklab.app.spoktools.models.fileExtensions.FileSPC;
import spoklab.app.spoktools.staticc.utils.UtilsFileExtension;

public final class StorageApp {

    private static final String TAG = "StorageApp";

    public static String mDirCollection = "collection";
    public static String mDirSleep = "sleep";
    public static String mDirCollectionSleep = mDirCollection + "/" + mDirSleep;
    public static String mDirPreviews = "preview";
    public static String mDirContent = "content";

    public static void time(
            @NonNull String path,
            long time1970
    ) {
        final File file = new File(path);
        if (file.setLastModified(time1970)) {
            Log.d(TAG, "time: TIME IS MODIFIED FOR: " + file.getName());
        }
    }

    public static long time(
            @NonNull String path
    ) {
        return new File(path)
                .lastModified();
    }

    @Nullable
    public static FileSPC preview(
        int id,
        CardType type,
        @NonNull String lang
    ) {
        byte[] data = load(previewUrl(id, type, lang));
        if (data == null) {
            return null;
        }

        return UtilsFileExtension
            .spc(
                data,
                Application
                    .TOPIC_CONFIG_M
                    .layoutParams
            );
    }

    public static void preview(
        int id,
        CardType type,
        @NonNull String lang,
        @NonNull byte[] data
    ) {
        save(
            tospc(id,type,lang),
            mDirPreviews,
            data
        );
    }

    @Nullable
    public static FileSCS collection(
        @NonNull String dir,
        @NonNull String fileName
    ) {
        byte[] data = load(collectionUrl(dir,fileName));
        if (data == null) {
            return null;
        }
        return UtilsFileExtension.scs(data);
    }

    public static void collection(
            @NonNull String dir,
            int id,
            @NonNull String lang,
            @NonNull byte[] data
    ) {
        save(
            toscs(id,lang),
            mDirCollection + "/"+dir,
            data
        );
    }

    public static String previewUrl(
        int id,
        CardType type,
        @NonNull String lang
    ) {
        return rootPath(mDirPreviews+"/"+tospc(id,type,lang));
    }

    @NonNull
    public static String collectionUrl(
        @NonNull String dir,
        @NonNull String fileName
    ) {
        return rootPath(mDirCollection+"/"+dir+"/"+fileName);
    }

    public static String toskc(
            int id,
            @NonNull String lang
    ) {
        return id+lang+".skc";
    }

    public static String tospc(
            int id,
            CardType type,
            @NonNull String lang
    ) {
        return id+lang+type+".spc";
    }

    public static String toscs(
            int id,
            @NonNull String lang
    ) {
        return id+lang+".scs";
    }

    @NonNull
    public static File rootFile(
            @NonNull String path
    ) {
        return new File(rootPath(path));
    }

    @NonNull
    public static String rootPath(
            @NonNull String path
    ) {
        return Application.PATH_CACHE_DIR + path;
    }

    @Nullable
    public static byte[] file(
        @NonNull String path
    ) {
        return file(new File(path));
    }

    @Nullable
    public static byte[] file(
        @NonNull File file
    ) {
        FileInputStream fis = null;
        byte[] data = null;

        try {
            fis = new FileInputStream(file);

            final ByteArrayOutputStream baos = new
                    ByteArrayOutputStream();
            int n;
            while((n = fis.read(Application.BUFFER_MB)) != -1) {
                baos.write(
                        Application.BUFFER_MB,
                        0,
                        n
                );
            }
            data = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (fis != null) {
                fis.close();
            }
        } catch (IOException e) {e.printStackTrace();}

        return data;
    }

    public static boolean exists(
        @NonNull String path
    ) {
        return new File(path)
            .exists();
    }

    public static boolean mkdir(
        @NonNull String path
    ) {
        return new File(path)
            .mkdir();
    }

    private static void save(
        @NonNull String file,
        @NonNull String root,
        @NonNull byte[] data
    ) {

        final File dir = rootFile(root);

        if (dir.mkdirs()) {
            Log.d(TAG, "save: DIR IS CREATED: " + dir.getName());
        }

        mkfile(dir+"/"+file,
                data
        );
    }

    private static void mkfile(
            @NonNull String fullPath,
            @NonNull byte[] data
    ) {
        final File f = new File(fullPath);
        FileOutputStream fos = null;

        try {
            if (!f.exists() && f.createNewFile()) {
                Log.d(TAG, "save: FILE IS CREATED: " + f.getName());
            }
            fos = new FileOutputStream(f);
            fos.write(data);
            Log.d(TAG, "save: FILE IS SAVED TO " + f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (fos != null) {
                fos.close();
            }
        } catch (IOException ignored) {}
    }

    @Nullable
    private static byte[] load(
            @NonNull String path
    ) {
        File file = new File(path);
        if (!file.exists()) {
            Log.d(TAG, "load: FILE NOT EXISTS " + file.getName());
            return null;
        }
        return file(file);
    }
}
