package spoklab.app.spoktools.views.bottomSheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import spoklab.app.spoktools.Application;
import spoklab.app.spoktools.listeners.recyclerview.topics.OnClickModelListener;
import spoklab.app.spoktools.models.fileExtensions.FileSPC;
import spoklab.app.spoktools.recyclerview.adapters.topics.TopicsAdapter;
import spoklab.app.spoktools.views.recyclerViews.RecyclerViewGridTopics;

public abstract class BottomSheetTopics
extends BottomSheetDialogFragment
implements OnClickModelListener<FileSPC> {

    @Nullable
    @Override
    public final View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        final Context context = getContext();

        if (context == null) {
            return null;
        }

        final RecyclerViewGridTopics recyclerView = new
            RecyclerViewGridTopics(context);

        recyclerView.setBackgroundColor(
            0xffffff00
        );

        TopicsAdapter.allTopics(adapter -> {
            adapter.setOnClickModelListener(
                BottomSheetTopics.this
            );

            recyclerView.setAdapter(
                adapter
            );
        });

        View subview = onCreateSubview(
            context
        );

        if (subview == null) {
            return recyclerView;
        }

        recyclerView.setPadding(
            0,
            subview.getLayoutParams()
                .height,
            0,
            0
        );

        final FrameLayout layout = new
            FrameLayout(context);

        layout.addView(
            recyclerView,
            -1,
            Application.HEIGHT
        );

        layout.addView(
            subview
        );

        return layout;
    }

    @Nullable
    protected abstract View onCreateSubview(
        @NonNull Context context
    );

}
