package spoklab.app.spoktools.models.fileExtensions;

import android.graphics.Bitmap;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

public final class FileSPC {

    public final byte categoryId;

    public final boolean isPremium;

    @ColorInt
    public final int color;

    @Nullable
    public final String title;

    @Nullable
    public final String description;

    @Nullable
    public final Bitmap image;

    public FileSPC(
            @Nullable String title,
            @Nullable String description,
            @Nullable Bitmap image,
            boolean isPremium,
            byte categoryId,
            @ColorInt int color
    ) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.isPremium = isPremium;
        this.categoryId = categoryId;
        this.color = color;
    }
}
