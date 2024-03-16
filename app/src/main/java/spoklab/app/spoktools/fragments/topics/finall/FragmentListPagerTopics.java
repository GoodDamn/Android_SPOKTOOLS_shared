package spoklab.app.spoktools.fragments.topics.finall;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import spoklab.app.spoktools.enums.CardType;
import spoklab.app.spoktools.fragments.collection.FragmentListPager;
import spoklab.app.spoktools.listeners.recyclerview.topics.OnClickModelListener;
import spoklab.app.spoktools.listeners.recyclerview.topics.OnLongClickModelListener;
import spoklab.app.spoktools.models.fileExtensions.FileSPC;
import spoklab.app.spoktools.other.alerts.AlertDialogSimple;
import spoklab.app.spoktools.recyclerview.adapters.topics.TopicsAdapter;
import spoklab.app.spoktools.recyclerview.adapters.topics.TopicsAdapterAdd;
import spoklab.app.spoktools.staticc.utils.RemoteStorageUtils;
import spoklab.app.spoktools.views.recyclerViews.RecyclerViewGridTopics;

public final class FragmentListPagerTopics
extends FragmentListPager<FileSPC>
implements OnClickModelListener<FileSPC>,
    OnLongClickModelListener<FileSPC> {

    @NonNull
    private TopicsAdapterAdd mAdapter;

    @Nullable
    @Override
    protected View onCreateView(
        @NonNull Context context
    ) {
        final RecyclerViewGridTopics recyclerView = new
            RecyclerViewGridTopics(context);

        TopicsAdapter.allTopicsList(list -> {

            mAdapter = new TopicsAdapterAdd(
                list
            );

            mAdapter.setOnLongClickModelListener(
                FragmentListPagerTopics.this
            );

            mAdapter.setOnClickModelListener(
                FragmentListPagerTopics.this
            );

            recyclerView.setAdapter(
                mAdapter
            );
        });

        return recyclerView;
    }

    @Override
    protected void onItemChanged(
        int position,
        @NonNull FileSPC f
    ) {
        mAdapter.changeItemAt(
            position,
            f
        );
    }

    @Override
    public void onClickModel(
        @Nullable FileSPC fileSPC,
        int topicId,
        int position
    ) {
        if (fileSPC == null) {
            return;
        }

        setDetails(
            position,
            fileSPC,
            topicId
        );

        getInstanceActivity()
            .nextFragment();
    }

    @Override
    public void onLongClickModel(
        final int position,
        final int topicId,
        @Nullable FileSPC preview
    ) {
        if (preview == null) {
            toast("I don't have any preview for this action :(");
            return;
        }
        AlertDialogSimple.create(
            "Do you want to delete '" + preview.title + "'?",
            "Yes",
            (dialog, which) -> {
                RemoteStorageUtils.deletePreview(
                    topicId,
                    (unused) -> mAdapter.deleteItemAt(
                        position
                    )
                );
            }
        ).show(
            getChildFragmentManager(),
            "dialog"
        );
    }
}
