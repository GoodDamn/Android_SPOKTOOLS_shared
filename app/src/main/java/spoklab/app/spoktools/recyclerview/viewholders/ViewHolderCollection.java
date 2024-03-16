package spoklab.app.spoktools.recyclerview.viewholders;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;

import spoklab.app.spoktools.Application;
import spoklab.app.spoktools.listeners.recyclerview.topics.OnClickModelListener;
import spoklab.app.spoktools.listeners.recyclerview.topics.OnLongClickModelListener;
import spoklab.app.spoktools.models.fileExtensions.FileSCS;
import spoklab.app.spoktools.staticc.utils.RemoteStorageUtils;
import spoklab.app.spoktools.staticc.utils.UtilsFileExtension;

public final class ViewHolderCollection
extends ViewHolderIdentified<FileSCS>
implements OnSuccessListener<byte[]> {

    private static final String TAG = "ViewHolderCollection";

    @NonNull
    private final TextView mTextView;

    @Nullable
    private FileSCS mCollection;

    public ViewHolderCollection(
            @NonNull Context context
    ) {
        super(new TextView(context));
        mTextView = (TextView) itemView;
        mTextView.setGravity(
            Gravity.CENTER
        );

        mTextView.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            Application.HEIGHT * 0.04f
        );

        mTextView.setLayoutParams(new ViewGroup.LayoutParams(
            Application.WIDTH,
            -2 // WRAP_CONTENT
        ));
    }

    private void showCollection() {
        mTextView.setText(
            mCollection.title
        );
    }

    @Override
    protected void onLoad(
        int collectionId
    ) {
        if (mCollection != null && mRecycledId == collectionId) {
            showCollection();
            return;
        }

        mRecycledId = collectionId;

        if (Application.CACHE_COLLECTIONS
            .containsKey(mRecycledId)
        ) {
            mCollection = Application
                .CACHE_COLLECTIONS
                .get(mRecycledId);
            showCollection();
            return;
        }

        RemoteStorageUtils.downloadCollection(
            mRecycledId,
            this
        );
    }

    @Override
    public void onSuccess(
        final byte[] data
    ) {
        mCollection = UtilsFileExtension
            .scs(data);

        Application.CACHE_COLLECTIONS
                .put(mRecycledId, mCollection);

        mTextView.setText(
            mCollection.title
        );
    }

    @Override
    protected void onLongClickModel(
        @NonNull OnLongClickModelListener<FileSCS> listener
    ) {
        listener.onLongClickModel(
            getAdapterPosition(),
            mRecycledId,
            mCollection
        );
    }

    @Override
    protected void onClickModel(
        @NonNull OnClickModelListener<FileSCS> listener
    ) {
        listener.onClickModel(
            mCollection,
            mRecycledId,
            getAdapterPosition()
        );
    }
}
