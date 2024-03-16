package spoklab.app.spoktools.staticc.utils;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import spoklab.app.spoktools.enums.CardType;
import spoklab.app.spoktools.other.lambda.Lambda;
import spoklab.app.spoktools.staticc.Keys;

public final class RemoteStorageUtils {

    private static final String TAG = "RemoteStorageUtils";

    private static final int maxSizeCollection = 1024; // 1 Kb

    @NonNull
    private static final FirebaseStorage mStorage = FirebaseStorage
        .getInstance();

    private static final StorageReference mTopicReference = mStorage
        .getReference("Trainings");

    private static final StorageReference mSleepCollectionRef = mStorage
        .getReference("Sleep/RU");

    public static void deletePreview(
        int previewId,
        @Nullable Lambda<Void> success
    ) {
        final String path = previewId +"/";
        final Task<Void> task = mTopicReference
            .child(path + CardType.M.toExtension())
            .delete();

        mTopicReference
            .child(path+CardType.B.toExtension())
            .delete();

        if (success == null) {
            return;
        }

        task.addOnSuccessListener(
            success::onExecute
        );
    }

    public static void deleteCollection(
        int collectionId,
        @Nullable Lambda<Void> success
    ) {
        final Task<Void> task = mSleepCollectionRef
            .child(collectionId + Keys.STORAGE_SCS_EXTENSION)
            .delete();

        if (success == null) {
            return;
        }

        task.addOnSuccessListener(
            success::onExecute
        );
    }

    public static void downloadCollection(
        int collectionId,
        @NonNull OnSuccessListener<byte[]> successListener
    ) {
        mSleepCollectionRef
            .child(collectionId + Keys.STORAGE_SCS_EXTENSION)
            .getBytes(maxSizeCollection)
            .addOnSuccessListener(successListener);
    }

}
