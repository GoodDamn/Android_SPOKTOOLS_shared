package spoklab.app.spoktools.recyclerview.decorations;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public final class GridDecorations
extends RecyclerView.ItemDecoration {

    private static final String TAG = "GridDecoration";

    private final int mVertical;
    private final int mLeftL;
    private final int mLeftR;

    public GridDecorations(
        int vertical,
        int horizontal,
        float srcWidth,
        int spanCount,
        int holderWidth
    ) {
        mVertical = vertical;
        final int cellWidth = (int) (srcWidth / spanCount);

        if (holderWidth > cellWidth) {
            throw new IllegalStateException(
                "holderWidth > (srcWidth / +"+spanCount+"+): "
               + holderWidth+" > "+cellWidth
            );
        }

        mLeftL = cellWidth - horizontal - holderWidth;
        mLeftR = horizontal;
    }

    public GridDecorations(
        int vertical,
        int horizontal,
        float srcWidth,
        int holderWidth
    ) {
        this(
            vertical,
            horizontal,
            srcWidth,
            2,
            holderWidth
        );
    }

    @Override
    public void getItemOffsets(
        @NonNull Rect outRect,
        @NonNull View view,
        @NonNull RecyclerView parent,
        @NonNull RecyclerView.State state
    ) {
        final int adapterPosition = parent
            .getChildAdapterPosition(
                view
            );

        if (adapterPosition % 2 == 0) {
            outRect.set(
                mLeftL,
                mVertical,
                0,
                mVertical
            );
            return;
        }

        outRect.set(
            mLeftR,
            mVertical,
            0,
            mVertical
        );
    }
}
