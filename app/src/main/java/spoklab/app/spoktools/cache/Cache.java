package spoklab.app.spoktools.cache;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import spoklab.app.spoktools.listeners.cache.CacheFileListener;
import spoklab.app.spoktools.staticc.StorageApp;

public abstract class Cache<T>
implements OnFailureListener {

    private static final String TAG = "Cache";

    @Nullable
    protected CacheFileListener mCacheFileListener;

    @NonNull
    protected final StorageReference mReference;

    @NonNull
    protected final String mPathToSave;

    protected final boolean mIsDirectory;

    @Nullable
    private T mCacheObject;

    public Cache(
            @NonNull String pathStorage,
            @NonNull String localPath,
            boolean isDirectory
    ) {

        mReference = FirebaseStorage
                .getInstance()
                .getReference(
                        pathStorage
                );

        mPathToSave = localPath;
        mIsDirectory = isDirectory;
    }

    public void setCacheObject(
        @Nullable T object
    ) {
        mCacheObject = object;
    }

    @Nullable
    public T getCacheObject() {
        return mCacheObject;
    }

    protected void checkMeta() {
        checkMeta(null);
    }

    protected void checkMeta(
            @Nullable String childRef
    ) {
        final StorageReference ref =
            childRef == null ?
            mReference
          : mReference.child(childRef);

        ref.getMetadata()
            .addOnSuccessListener(
                Cache.this::onSuccess
            ).addOnFailureListener(
                this
            );
    }

    @Override
    public void onFailure(
            @NonNull Exception e
    ) {
        if (mCacheFileListener != null) {
            mCacheFileListener.onErrorCache();
        }
    }

    public void setCacheListener(
        @Nullable CacheFileListener listener
    ) {
        mCacheFileListener = listener;
    }

    private void onSuccess(
        StorageMetadata metadata
    ) {

        long localTime = StorageApp
            .time(mPathToSave);

        long netTime = metadata
            .getUpdatedTimeMillis();

        if (localTime >= netTime) {
            onCacheNotExpired();
            return;
        }

        if (mIsDirectory) {
            if (StorageApp
                .mkdir(mPathToSave)
            ) {
                Log.d(TAG, "onSuccess: DIRECTORY IS CREATED: " + mPathToSave);
            }

            StorageApp.time(
                mPathToSave,
                netTime
            );
        }
        onUpdateCache();
    }

    protected abstract void onUpdateCache();
    protected abstract void onCacheNotExpired();

}
