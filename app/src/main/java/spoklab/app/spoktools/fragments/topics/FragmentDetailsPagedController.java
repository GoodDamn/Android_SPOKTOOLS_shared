package spoklab.app.spoktools.fragments.topics;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import spoklab.app.spoktools.activities.BaseActivity;
import spoklab.app.spoktools.fragments.BaseFragment;

public abstract class FragmentDetailsPagedController<
    ACTIVITY extends BaseActivity,
    MODEL,
    PARENT_FRAGMENT extends FragmentDetailsPaged<MODEL>
> extends BaseFragment<ACTIVITY> {

    private PARENT_FRAGMENT mDetailsPagedFragment;

    @Override
    public void onCreate(
        @Nullable Bundle savedInstanceState
    ) {
        super.onCreate(savedInstanceState);
        mDetailsPagedFragment = (PARENT_FRAGMENT) getParentFragment();
    }

    protected final void notifyItemChanged(
        @NonNull MODEL model
    ) {
        mDetailsPagedFragment
            .notifyItemChanged(
                model
            );
    }

}
