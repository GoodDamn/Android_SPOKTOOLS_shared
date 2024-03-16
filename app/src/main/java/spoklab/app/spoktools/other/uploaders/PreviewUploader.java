package spoklab.app.spoktools.other.uploaders;

import androidx.annotation.NonNull;

import spoklab.app.spoktools.enums.CardType;
import spoklab.app.spoktools.staticc.Keys;

public final class PreviewUploader
extends Uploader {

    public PreviewUploader() {
        super(Keys.STORAGE_TOPICS);
    }

    public void upload(
        int id,
        @NonNull CardType cardType,
        @NonNull byte[] data
    ) {
        upload(
            id+"/"+cardType.toExtension(),
            data
        );
    }
}
