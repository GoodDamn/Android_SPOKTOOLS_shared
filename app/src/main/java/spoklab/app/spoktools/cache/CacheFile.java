package spoklab.app.spoktools.cache;

import androidx.annotation.NonNull;

import spoklab.app.spoktools.staticc.StorageApp;

public abstract class CacheFile<T>
extends Cache<T> {

    protected final boolean mWithCache;
    protected final boolean mBackgroundLoad;
    protected boolean mFirstLoad;

    public CacheFile(
        @NonNull String pathStorage,
        @NonNull String localPath,
        boolean withCache,
        boolean backgroundLoad
    ) {
        super(
            pathStorage,
            localPath,
            false
        );
        mWithCache = withCache;
        mBackgroundLoad = backgroundLoad;

        mFirstLoad = true;
    }

    public void load() {
        loadCache();
        checkMeta();
    }

    private void loadCache() {

        boolean exists = StorageApp
            .exists(mPathToSave);

        mFirstLoad = !exists;

        if (mFirstLoad) {
            return;
        }

        byte[] cache = StorageApp
            .file(mPathToSave);

        if (mCacheFileListener == null) {
            return;
        }

        mCacheFileListener.onFileCache(
            cache
        );
    }

}
