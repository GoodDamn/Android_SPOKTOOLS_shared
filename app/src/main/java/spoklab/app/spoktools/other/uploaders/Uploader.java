package spoklab.app.spoktools.other.uploaders;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import spoklab.app.spoktools.enums.CardType;
import spoklab.app.spoktools.staticc.Keys;

public class Uploader {

    @NonNull
    private final StorageReference mReferencePreview;

    @Nullable
    private OnSuccessListener<UploadTask.TaskSnapshot> mOnSuccessListener;

    @Nullable
    private OnProgressListener<UploadTask.TaskSnapshot> mOnProgressListener;

    public Uploader(
        @NonNull String rootFolder
    ) {
        mReferencePreview = FirebaseStorage
            .getInstance()
            .getReference(
                rootFolder
            );
    }

    public final void setOnSuccessListener(
        @Nullable OnSuccessListener<UploadTask.TaskSnapshot> listener
    ) {
        mOnSuccessListener = listener;
    }

    public final void setOnProgressListener(
        @Nullable OnProgressListener<UploadTask.TaskSnapshot> listener
    ) {
        mOnProgressListener = listener;
    }

    protected void upload(
        @NonNull String path,
        @NonNull byte[] data
    ) {
        final UploadTask task = mReferencePreview
            .child(path)
            .putBytes(data);

        if (mOnSuccessListener != null) {
            task.addOnSuccessListener(
                mOnSuccessListener
            );
        }

        if (mOnProgressListener != null) {
            task.addOnProgressListener(
                mOnProgressListener
            );
        }
    }
}
