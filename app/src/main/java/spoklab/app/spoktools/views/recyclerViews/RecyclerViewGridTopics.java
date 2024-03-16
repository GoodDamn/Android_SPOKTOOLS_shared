package spoklab.app.spoktools.views.recyclerViews;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import spoklab.app.spoktools.Application;
import spoklab.app.spoktools.recyclerview.decorations.GridDecorations;

public final class RecyclerViewGridTopics
extends RecyclerView {

    public RecyclerViewGridTopics(
        @NonNull Context context
    ) {
        super(context);
        init(context);
    }

    public RecyclerViewGridTopics(
        @NonNull Context context,
        @Nullable AttributeSet attrs
    ) {
        super(context, attrs);
        init(context);
    }

    public RecyclerViewGridTopics(
        @NonNull Context context,
        @Nullable AttributeSet attrs,
        int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(
        @NonNull Context context
    ) {
        final GridLayoutManager grid = new
            GridLayoutManager(
                context,
            2
        );

        grid.setOrientation(
            GridLayoutManager.VERTICAL
        );

        final int offset = (int) (
            Application.WIDTH * 0.02f
        );

        addItemDecoration(
            new GridDecorations(
                offset,
                offset,
                Application.WIDTH,
                Application.TOPIC_CONFIG_M
                    .width
            )
        );

        setLayoutManager(grid);
    }

}
