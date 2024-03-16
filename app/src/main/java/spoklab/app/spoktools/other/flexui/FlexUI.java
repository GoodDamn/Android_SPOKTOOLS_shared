package spoklab.app.spoktools.other.flexui;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import spoklab.app.spoktools.Application;

public final class FlexUI {

    public static void bounds(
        @NonNull View view,
        float width,
        float height,
        float x,
        float y
    ) {
        final ViewGroup.MarginLayoutParams params = new
            ViewGroup.MarginLayoutParams(
            (int) (width * Application.WIDTH),
            (int) (height * Application.HEIGHT)
        );

        params.leftMargin = (int) (x * Application.WIDTH);
        params.topMargin = (int) (y * Application.HEIGHT);

        view.setLayoutParams(
            params
        );
    }

    public static void bounds(
        @NonNull View view,
        int width,
        int height,
        float x,
        float y
    ) {
        final ViewGroup.MarginLayoutParams params = new
            ViewGroup.MarginLayoutParams(
            width,
            height
        );

        params.leftMargin = (int) (x * Application.WIDTH);
        params.topMargin = (int) (y * Application.HEIGHT);

        view.setLayoutParams(
            params
        );
    }

    public static void size(
        @NonNull View view,
        float width,
        float height
    ) {
        view.setLayoutParams(new ViewGroup.MarginLayoutParams(
            (int) (width * Application.WIDTH),
            (int) (height * Application.HEIGHT)
        ));
    }

}
