package spoklab.app.spoktools.recyclerview.viewholders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import spoklab.app.spoktools.Application;
import spoklab.app.spoktools.cache.CacheData;
import spoklab.app.spoktools.enums.CardType;
import spoklab.app.spoktools.listeners.cache.CacheFileListener;
import spoklab.app.spoktools.listeners.recyclerview.topics.OnClickModelListener;
import spoklab.app.spoktools.listeners.recyclerview.topics.OnLongClickModelListener;
import spoklab.app.spoktools.models.fileExtensions.FileSPC;
import spoklab.app.spoktools.staticc.Keys;
import spoklab.app.spoktools.staticc.StorageApp;
import spoklab.app.spoktools.staticc.utils.UtilsFileExtension;
import spoklab.app.spoktools.views.TopicView;

public final class ViewHolderTopic
extends ViewHolderIdentified<FileSPC>
implements CacheFileListener {

    private static final String TAG = "ViewHolderTopic";

    @Nullable
    private CacheData<FileSPC> mCache;

    @NonNull
    private final TopicView mTopicView;

    @NonNull
    private CardType mCardType;

    @NonNull
    private String mLanguage;

    public ViewHolderTopic(
            @NonNull Context context
    ) {
        super(new TopicView(context));
        mTopicView = (TopicView) itemView;
        mTopicView.setLayoutParams(Application
                .TOPIC_CONFIG_M
                .layoutParams
                .copy()
        );

        mCardType = CardType.M;
    }

    @Override
    protected void onLoad(
        int topicId
    ) {
        mCardType = CardType.M;
        mLanguage = Application.LANGUAGE_CODE;

        final boolean notHasCache =
            mCache == null
                || mCache.getCacheObject() == null;

        if (topicId == mRecycledId && !notHasCache) {
            showView(
                mCache.getCacheObject()
            );
            return;
        }

        if (Application
            .CACHE_PREVIEWS
            .containsKey(topicId)
        ) {
            mRecycledId = topicId;
            mCache = Application
                .CACHE_PREVIEWS
                .get(topicId);
            showView(
                mCache.getCacheObject()
            );
            return;
        }

        if (mCache == null || topicId != mRecycledId) {
            mRecycledId = topicId;

            mCache = createCache(
                topicId
            );

            mCache.setCacheListener(this);
        }

        mCache.load();
    }

    @Override
    protected void onClickModel(
        @NonNull OnClickModelListener<FileSPC> listener
    ) {
        if (mCache == null) {
            return;
        }

        listener.onClickModel(
            mCache.getCacheObject(),
            mRecycledId,
            getAdapterPosition()
        );
    }

    @Override
    protected void onLongClickModel(
        @NonNull OnLongClickModelListener<FileSPC> listener
    ) {
        if (mCache == null) {
            return;
        }

        listener.onLongClickModel(
            getAdapterPosition(),
            mRecycledId,
            mCache.getCacheObject()
        );
    }

    @Override
    public void onFileCache(
        @Nullable byte[] data
    ) {
        if (data == null || mCache == null) {
            return;
        }

        final FileSPC spc = UtilsFileExtension
            .spc(
                data,
                Application
                    .TOPIC_CONFIG_M
                    .layoutParams
            );

        mCache.setCacheObject(
            spc
        );

        Application
            .CACHE_PREVIEWS
            .put(mRecycledId, mCache);

        showView(spc);
    }

    @Override
    public void onNetCache(
        @Nullable byte[] data
    ) {
        if (data == null) {
            return;
        }

        StorageApp.preview(
            mRecycledId,
            mCardType,
            mLanguage,
            data
        );

        onFileCache(data);
    }

    @Override
    public void onErrorCache() {
        if (mCache == null) {
            return;
        }

        final FileSPC spc = new FileSPC(
            "No title info",
            "No description info",
            null,
            false,
            (byte)1,
            0xff000000
        );

        mCache.setCacheObject(spc);
        showView(spc);
    }

    private void showView(
        @NonNull FileSPC f
    ) {
        mTopicView.setTitle(f.title);
        mTopicView.setDescription(f.description);
        mTopicView.calculatePositions();

        mTopicView.setTitleColor(f.color);
        mTopicView.setDescColor(f.color);

        mTopicView.setPremiumState(f.isPremium);
        mTopicView.setBitmapImage(f.image);

        mTopicView.update();
    }


    public static CacheData<FileSPC> createCache(
        int id
    ) {
        return new CacheData<>(
            Keys.STORAGE_TOPICS
                + id
                + "/"
                + CardType.M.toExtension(),
            StorageApp.previewUrl(
                id,
                CardType.M,
                Application.LANGUAGE_CODE
            )
        );
    }
}
