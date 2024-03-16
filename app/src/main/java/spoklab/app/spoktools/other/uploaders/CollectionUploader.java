package spoklab.app.spoktools.other.uploaders;

import androidx.annotation.NonNull;

import spoklab.app.spoktools.staticc.Keys;

public final class CollectionUploader
extends Uploader {

    public CollectionUploader() {
        super(Keys.STORAGE_SLEEP + "/RU");
    }

    public void upload(
        int id,
        byte[] data
    ) {
        upload(
            id + Keys.STORAGE_SCS_EXTENSION,
            data
        );
    }
}
