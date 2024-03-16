package spoklab.app.spoktools;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;

import com.google.firebase.FirebaseApp;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import spoklab.app.spoktools.activities.finall.MainActivity;
import spoklab.app.spoktools.cache.CacheData;
import spoklab.app.spoktools.layoutParams.BaseLayoutParams;
import spoklab.app.spoktools.models.card.TopicConfig;
import spoklab.app.spoktools.models.fileExtensions.FileSCS;
import spoklab.app.spoktools.models.fileExtensions.FileSPC;

public final class Application
extends android.app.Application {

    private static final String TAG = "Application";
    
    public static int WIDTH;
    public static int HEIGHT;

    public static float CORNER_RADIUS_CARD;

    public static TopicConfig TOPIC_CONFIG_M;
    public static TopicConfig TOPIC_CONFIG_B;

    public static String PATH_CACHE_DIR;

    public static String LANGUAGE_CODE;

    public static Typeface FONT_OPEN_SANS_BOLD;

    public static HashMap<Integer, CacheData<FileSPC>>
        CACHE_PREVIEWS;

    public static HashMap<Integer, FileSCS>
        CACHE_COLLECTIONS;

    public static Charset UTF_8;

    // If it calls, It needs to be called only on a single thread
    // not parallel at the same time
    public static byte[] BUFFER_MB;

    public static byte[] SINGLE_BYTE;

    @Override
    public void onCreate() {
        super.onCreate();

        final Context context = getApplicationContext();

        DisplayMetrics metrics = getResources()
                .getDisplayMetrics();

        BUFFER_MB = new byte[1024*1024];
        SINGLE_BYTE = new byte[1];

        PATH_CACHE_DIR = getCacheDir().toString() + "/";

        WIDTH = metrics.widthPixels;
        HEIGHT = metrics.heightPixels;

        final int heightCard = (int) (WIDTH * 0.503f);
        final int widthCardM = (int) (WIDTH * 0.403f);
        final int widthCardB = (int) (WIDTH * 0.847f);
        final int offsetBetween = (int) (heightCard * 0.04f);

        TOPIC_CONFIG_M = new TopicConfig(
            new BaseLayoutParams(
                widthCardM,
                heightCard
            ),
            widthCardM * 0.12f,
            widthCardM * 0.066f,
            (int) (widthCardM * 0.083f),
            (int) (heightCard * 0.113f),
            offsetBetween,
            0.2f
        );

        TOPIC_CONFIG_B = new TopicConfig(
            new BaseLayoutParams(
                widthCardB,
                heightCard
            ),
            widthCardB * 0.063f,
            widthCardB * 0.037f,
            (int) (widthCardB * 0.083f),
            (int) (heightCard * 0.113f),
            offsetBetween,
            0.2f
        );

        CORNER_RADIUS_CARD = heightCard * 0.1f;

        CACHE_PREVIEWS = new HashMap<>();
        CACHE_COLLECTIONS = new HashMap<>();

        UTF_8 = StandardCharsets.UTF_8;

        LANGUAGE_CODE = "";

        FONT_OPEN_SANS_BOLD = ResourcesCompat
            .getFont(
                context,
                R.font.open_sans_bold
            );

        FirebaseApp.initializeApp(
            context
        );

    }


}
