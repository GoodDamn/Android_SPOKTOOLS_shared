package spoklab.app.spoktools.fragments.collection;

import androidx.annotation.NonNull;

import spoklab.app.spoktools.activities.collection.ListDetailsPagerActivity;
import spoklab.app.spoktools.fragments.BaseFragment;

public abstract class FragmentListPager<T>
extends BaseFragment<ListDetailsPagerActivity<T>> {

    public final void setDetails(
        int position,
        @NonNull T detailsModel,
        int modelId
    ) {
        getInstanceActivity()
            .setDetails(
                position,
                detailsModel,
                modelId
            );
    }

    public final void notifyItemChanged(
        int position,
        @NonNull T model
    ) {
        onItemChanged(
            position,
            model
        );
    }

    protected abstract void onItemChanged(
        int position,
        @NonNull T model
    );

}
