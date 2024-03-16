package spoklab.app.spoktools.views.bottomSheets;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import spoklab.app.spoktools.Application;
import spoklab.app.spoktools.models.fileExtensions.FileSPC;
import spoklab.app.spoktools.recyclerview.adapters.topics.TopicsAdapter;

public final class BottomSheetTopicsSelect
extends BottomSheetTopics {

    @NonNull
    private TopicsAdapter mAdapter;

    @NonNull
    private final ArrayList<Integer> mTopicsSelected = new
        ArrayList<>();

    @Nullable
    private OnSelectListener mOnSelectListener;

    @Nullable
    @Override
    protected View onCreateSubview(
        @NonNull Context context
    ) {
        final RecyclerView recyclerViewTopics = new
            RecyclerView(context);

        mAdapter = new TopicsAdapter(
            mTopicsSelected
        );

        recyclerViewTopics.setBackgroundColor(
            0xffff0000
        );

        recyclerViewTopics.setLayoutManager(new LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        ));

        recyclerViewTopics.setAdapter(
            mAdapter
        );

        recyclerViewTopics.setLayoutParams(new ViewGroup.LayoutParams(
            -1,
            Application.TOPIC_CONFIG_M.height
        ));

        return recyclerViewTopics;
    }


    @Override
    public void onClickModel(
        @Nullable FileSPC fileSPC,
        int topicId,
        int position
    ) {
        Context context = getContext();
        if (fileSPC == null || context == null) {
            return;
        }

        int index = mTopicsSelected
            .indexOf(
                topicId
            );

        if (index == -1) {
            mTopicsSelected.add(topicId);
            mAdapter.notifyItemInserted(
                mAdapter.getItemCount()
            );
            return;
        }

        mTopicsSelected.remove(
            index
        );

        mAdapter.notifyItemRemoved(
            index
        );
    }

    @Override
    public void onDismiss(
        @NonNull DialogInterface dialog
    ) {
        if (mOnSelectListener != null) {
            mOnSelectListener.onSelectTopics(
                mTopicsSelected
            );
        }
        super.onDismiss(dialog);
    }

    public void setOnSelectListener(
        @Nullable OnSelectListener l
    ) {
        mOnSelectListener = l;
    }

    public interface OnSelectListener {
        void onSelectTopics(
            @NonNull ArrayList<Integer> topics
        );
    }
}
