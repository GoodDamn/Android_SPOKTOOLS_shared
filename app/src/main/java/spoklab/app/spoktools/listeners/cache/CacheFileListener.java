package spoklab.app.spoktools.listeners.cache;

import androidx.annotation.Nullable;

public interface CacheFileListener {

    void onErrorCache();

    void onFileCache(
        @Nullable byte[] data
    );

    void onNetCache(
        @Nullable byte[] data
    );

}
