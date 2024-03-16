package spoklab.app.spoktools.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import spoklab.app.spoktools.Application;
import spoklab.app.spoktools.models.card.TopicConfig;

public final class TopicView
extends View {

    private static final String TAG = "TopicView";

    private boolean mIsPremium;

    @NonNull
    private final TopicConfig mConfig;

    @Nullable
    private String mTitle;

    @Nullable
    private String mDescription;

    @Nullable
    private Bitmap mBitmapImage;

    @Nullable
    private PositionedText[] mPartTitle;

    @Nullable
    private PositionedText[] mPartDesc;

    @NonNull
    private final Paint mPaintTitle;

    @NonNull
    private final Paint mPaintDesc;

    @NonNull
    private final RectF mRectFClip;

    @NonNull
    private final Path mPathClip;

    public TopicView(
        @NonNull Context context
    ) {
        this(
            context,
            Application.TOPIC_CONFIG_M
        );
    }

    public TopicView(
        @NonNull Context context,
        @NonNull TopicConfig topicConfig
    ) {
        super(context);

        mConfig = topicConfig;

        mPaintTitle = new Paint();
        mPaintDesc = new Paint();

        mPaintTitle.setTypeface(
            Application.FONT_OPEN_SANS_BOLD
        );

        mPaintDesc.setTypeface(
            Application.FONT_OPEN_SANS_BOLD
        );

        mIsPremium = false;

        mPathClip = new Path();

        mRectFClip = new RectF(
            0,
            0,
            topicConfig.width,
            topicConfig.height
        );

        mPaintTitle.setTextSize(
            topicConfig.fontSizeTitle
        );

        mPaintDesc.setTextSize(
            topicConfig.fontSizeDesc
        );

    }

    @ColorInt
    public int getTitleColor() {
        return mPaintTitle.getColor();
    }

    @Nullable
    public Bitmap getBitmapImage() {
        return mBitmapImage;
    }

    public void setDescColor(
        @ColorInt int color
    ) {
        mPaintDesc.setColor(
            color
        );
    }

    public void setTitleColor(
        @ColorInt int color
    ) {
        mPaintTitle.setColor(
            color
        );
    }

    public void setBitmapImage(
        @Nullable final Bitmap bitmap
    ) {
        mBitmapImage = bitmap;
    }

    public void setTitle(
        @Nullable String title
    ) {
        mTitle = title;
    }

    public void setDescription(
        @Nullable String desc
    ) {
        mDescription = desc;
    }

    public void setPremiumState(
        boolean premium
    ) {
        mIsPremium = premium;
    }

    public void calculatePositions() {
        mPartTitle = null;
        mPartDesc = null;

        float y = mConfig.height - mConfig.paddingBottom;

        if (mDescription != null) {
            String[] partsDesc = mDescription.split("\n");
            mPartDesc = new PositionedText[partsDesc.length];
            for (byte i = (byte) (partsDesc.length - 1); i >= 0; i--) {
                mPartDesc[i] = new PositionedText(
                    y,
                    partsDesc[i]
                );

                y -= mPaintDesc.getTextSize() + mConfig.offsetLineDesc;
            }
        }

        y -= mConfig.offsetBetween;

        if (mTitle != null) {
            String[] partsTitle = mTitle.split("\n");
            mPartTitle = new PositionedText[partsTitle.length];
            for (byte i = (byte) (partsTitle.length - 1); i >= 0; i--) {
                mPartTitle[i] = new PositionedText(
                    y,
                    partsTitle[i]
                );

                y -= mPaintTitle.getTextSize() + mConfig.offsetLineTitle;
            }
        }

    }

    public void update() {
        invalidate();
    }

    @Override
    public void draw(
        Canvas canvas
    ) {
        super.draw(canvas);

        mPathClip.reset();
        mPathClip.addRoundRect(
            mRectFClip,
            Application.CORNER_RADIUS_CARD,
            Application.CORNER_RADIUS_CARD,
            Path.Direction.CW
        );

        mPathClip.close();

        canvas.clipPath(
            mPathClip
        );

        if (mBitmapImage == null) {
            canvas.drawColor(
                0xff08193A
            );
        } else {
            canvas.drawBitmap(
                mBitmapImage,
                0f,
                0f,
                mPaintTitle
            );
        }

        if (mPartTitle != null) {
            for (PositionedText t: mPartTitle) {
                canvas.drawText(
                    t.text,
                    mConfig.paddingHorizontal,
                    t.y,
                    mPaintTitle
                );
            }
        }

        if (mPartDesc != null) {
            for (PositionedText t: mPartDesc) {
                canvas.drawText(
                    t.text,
                    mConfig.paddingHorizontal,
                    t.y,
                    mPaintDesc
                );
            }
        }


        if (mIsPremium) {
            canvas.drawColor(0x77000000);
        }

    }


    private static class PositionedText {
        public float y;
        public String text;

        public PositionedText(
            float y,
            String text
        ) {
            this.y = y;
            this.text = text;
        }
    }

}
