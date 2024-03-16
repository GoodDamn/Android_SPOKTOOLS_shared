package spoklab.app.spoktools.fragments.collection;

import androidx.annotation.NonNull;

import spoklab.app.spoktools.activities.collection.ListDetailsPagerActivity;
import spoklab.app.spoktools.fragments.BaseFragment;
import spoklab.app.spoktools.listeners.fragments.topic.ModelDetailsListener;

public abstract class DetailsFragment<T>
extends BaseFragment<ListDetailsPagerActivity<T>>
implements ModelDetailsListener<T> {

    public final void setDetails(
            @NonNull T model,
            int modelId
    ) {
        onReceiveModelDetails(
            model,
            modelId
        );
    }

    public final void notifyItemChanged(
        @NonNull T model
    ) {
        getInstanceActivity()
            .notifyItemChanged(model);
    }

}
