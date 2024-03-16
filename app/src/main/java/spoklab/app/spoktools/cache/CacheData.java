package spoklab.app.spoktools.cache;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageMetadata;

public final class CacheData<T>
extends CacheFile<T> {

    private static final int maxSize =  5 * 1024 * 1024;

    public CacheData(
        @NonNull String pathStorage,
        @NonNull String localPath
    ) {
        super(
            pathStorage,
            localPath,
            false,
            false
        );
    }

    public CacheData(
        @NonNull String pathStorage,
        @NonNull String localPath,
        boolean withCache,
        boolean backgroundLoad
    ) {
        super(
            pathStorage,
            localPath,
            withCache,
            backgroundLoad
        );
    }

    @Override
    protected void onUpdateCache() {
        mReference
            .getBytes(maxSize)
            .addOnSuccessListener(CacheData.this::onSuccess)
            .addOnFailureListener(this);
    }

    private void onSuccess(
        byte[] data
    ) {
        if (mCacheFileListener != null) {
            mCacheFileListener.onNetCache(data);
        }
    }

    @Override
    protected void onCacheNotExpired() {

    }
}
