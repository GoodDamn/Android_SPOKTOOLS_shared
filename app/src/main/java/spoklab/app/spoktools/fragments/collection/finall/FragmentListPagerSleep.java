package spoklab.app.spoktools.fragments.collection.finall;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import spoklab.app.spoktools.Application;
import spoklab.app.spoktools.downloaders.CollectionDownloader;
import spoklab.app.spoktools.fragments.collection.FragmentListPager;
import spoklab.app.spoktools.listeners.CollectionDownloadListener;
import spoklab.app.spoktools.listeners.recyclerview.topics.OnClickModelListener;
import spoklab.app.spoktools.listeners.recyclerview.topics.OnLongClickModelListener;
import spoklab.app.spoktools.models.fileExtensions.FileSCS;
import spoklab.app.spoktools.other.alerts.AlertDialogSimple;
import spoklab.app.spoktools.recyclerview.adapters.collection.AddCollectionAdapter;
import spoklab.app.spoktools.recyclerview.adapters.collection.CollectionAdapter;
import spoklab.app.spoktools.staticc.Keys;
import spoklab.app.spoktools.staticc.StorageApp;
import spoklab.app.spoktools.staticc.utils.RemoteStorageUtils;

public final class FragmentListPagerSleep
extends FragmentListPager<FileSCS>
implements OnClickModelListener<FileSCS>,
    OnLongClickModelListener<FileSCS>,
    CollectionDownloadListener<Integer> {

    private static final String TAG = "FragmentSleepCollection";

    @NonNull
    private RecyclerView mRecyclerView;

    @NonNull
    private AddCollectionAdapter mAdapter;

    private final CollectionDownloader mDownloader = new
            CollectionDownloader(
                Keys.STORAGE_SLEEP,
                StorageApp.collectionUrl(
                    Keys.STORAGE_SLEEP,
                    Keys.STORAGE_SLEEP
                ), Keys.STORAGE_SLEEP
            );

    @Override
    protected View onCreateView(
        @NonNull Context context
    ) {
        mRecyclerView = new RecyclerView(
                context
        );

        mRecyclerView.setHasFixedSize(
            true
        );

        mRecyclerView.setLayoutManager(new LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        ));

        mDownloader.setDownloadListener(
            this
        );

        mDownloader.start();

        return mRecyclerView;
    }

    @Override
    public void onClickModel(
        @Nullable FileSCS fileSCS,
        int modelId,
        int adapterPosition
    ) {
        if (fileSCS == null) {
            return;
        }

        setDetails(
            adapterPosition,
            fileSCS,
            modelId
        );

        getInstanceActivity()
            .nextFragment();
    }

    @Override
    public void onLongClickModel(
        int position,
        int collectionId,
        @Nullable FileSCS collection
    ) {
        if (collection == null) {
            toast("I don't have any collection to perform this action :(");
            return;
        }

        AlertDialogSimple.create(
            "Do you want to delete '"+collection.title+"'?",
            "Yes",
            (dialog,w) -> {
                RemoteStorageUtils.deleteCollection(
                    collectionId,
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

    @Override
    public void onSuccessDownload(
        @NonNull ArrayList<Integer> collections
    ) {
        mAdapter = new AddCollectionAdapter(
            collections
        );

        mAdapter.setOnClickModelListener(
            this
        );

        mAdapter.setOnLongClickModelListener(
            this
        );

        mRecyclerView.setAdapter(
            mAdapter
        );
    }

    @Override
    public void onFailDownloadCollection(
        Exception e
    ) {
        toast("Fail collection downloading");
    }

    @Override
    protected void onItemChanged(
        int position,
        @NonNull FileSCS f
    ) {
        mAdapter.notifyItemChanged(
            position
        );
    }
}
