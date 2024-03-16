package spoklab.app.spoktools.listeners.fragments.topic;

import androidx.annotation.NonNull;

public interface ModelDetailsListener<T> {
    void onReceiveModelDetails(
        @NonNull T model,
        int modelId
    );
}
