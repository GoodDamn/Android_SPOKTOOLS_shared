package spoklab.app.spoktools.models.card;

import androidx.annotation.NonNull;

import spoklab.app.spoktools.layoutParams.BaseLayoutParams;

public final class TopicConfig {
    @NonNull
    public final BaseLayoutParams layoutParams;

    public final float fontSizeTitle;
    public final float fontSizeDesc;
    public final int paddingHorizontal;
    public final int paddingBottom;
    public final int width;
    public final int height;
    public final int offsetBetween;
    public final int offsetLineTitle;
    public final int offsetLineDesc;

    public TopicConfig(
        @NonNull BaseLayoutParams params,
        float titleSize,
        float descSize,
        int padHorizontal,
        int padBottom,
        int offsetBetween,
        float offsetLine
    ) {
        layoutParams = params;
        fontSizeTitle = titleSize;
        fontSizeDesc = descSize;
        paddingHorizontal = padHorizontal;
        paddingBottom = padBottom;
        this.width = params.width;
        this.height = params.height;
        this.offsetBetween = offsetBetween;
        offsetLineTitle = (int) (offsetLine * fontSizeTitle);
        offsetLineDesc = (int) (offsetLine * fontSizeDesc);
    }
}
