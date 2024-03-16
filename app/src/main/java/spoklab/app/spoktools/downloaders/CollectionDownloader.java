package spoklab.app.spoktools.downloaders;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import spoklab.app.spoktools.cache.Cache;
import spoklab.app.spoktools.listeners.CollectionDownloadListener;
import spoklab.app.spoktools.models.fileExtensions.FileSCS;
import spoklab.app.spoktools.staticc.utils.UtilsFileExtension;

public final class CollectionDownloader
extends Cache<Void>
implements OnSuccessListener<ListResult> {

    private static final String TAG = "CollectionDownloader";

    @Nullable
    private ArrayList<Integer> mCollections;

    @Nullable
    private CollectionDownloadListener<Integer> mDownloadListener;

    @NonNull
    private final String mType;

    private final String mLanguage;

    public CollectionDownloader(
        @NonNull final String pathStorage,
        @NonNull final String localPath,
        @NonNull final String type
    ) {
        super(
            pathStorage,
            localPath,
            true
        );

        mType = type;
        mLanguage = "RU";
    }

    public void start() {
        FirebaseStorage.getInstance()
            .getReference(mType+"/"+mLanguage)
            .listAll()
            .addOnSuccessListener(
                this
            );
    }

    public void setDownloadListener(
        @NonNull final CollectionDownloadListener<Integer> l
    ) {
        mDownloadListener = l;
    }

    @Override
    public void onSuccess(
        final ListResult listResult
    ) {
        List<StorageReference> list = listResult
            .getItems();

        if (list.isEmpty()) {
            Log.d(TAG, "onSuccess: DOWNLOAD_LIST_IS_EMPTY");
            return;
        }

        mCollections = new ArrayList<>();

        for (StorageReference ref: list) {
            final String name = ref.getName();
            final int i = name.indexOf(".");
            if (i == -1) {
                continue;
            }

            final int id = Integer.parseInt(
                name.substring(0,i)
            );

            Log.d(TAG, "downloadCollection: ID: "
                + name
                + " "
                + id);
            mCollections.add(
                id
            );
        }

        if (mDownloadListener == null) {
            return;
        }

        mDownloadListener.onSuccessDownload(
            mCollections
        );
    }

    @Override
    protected void onUpdateCache() {

    }

    @Override
    protected void onCacheNotExpired() {

    }
}
