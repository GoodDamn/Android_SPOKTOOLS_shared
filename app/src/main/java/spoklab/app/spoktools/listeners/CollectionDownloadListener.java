package spoklab.app.spoktools.listeners;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import spoklab.app.spoktools.models.fileExtensions.FileSCS;

public interface CollectionDownloadListener<T> {

    void onSuccessDownload(
        @NonNull ArrayList<T> collections
    );

    void onFailDownloadCollection(
        Exception e
    );

}
