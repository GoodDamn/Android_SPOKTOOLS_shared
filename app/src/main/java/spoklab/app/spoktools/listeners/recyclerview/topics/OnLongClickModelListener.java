package spoklab.app.spoktools.listeners.recyclerview.topics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface OnLongClickModelListener<MODEL> {
    void onLongClickModel(
        int position,
        int modelId,
        @Nullable MODEL model
    );
}
