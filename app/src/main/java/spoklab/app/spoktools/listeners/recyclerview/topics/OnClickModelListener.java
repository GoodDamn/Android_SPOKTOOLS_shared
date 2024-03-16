package spoklab.app.spoktools.listeners.recyclerview.topics;

import androidx.annotation.Nullable;

public interface OnClickModelListener<MODEL> {
    void onClickModel(
        @Nullable MODEL model,
        int modelId,
        int adapterPosition
    );
}
