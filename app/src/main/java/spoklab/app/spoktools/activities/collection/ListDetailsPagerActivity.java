package spoklab.app.spoktools.activities.collection;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import spoklab.app.spoktools.activities.pager.PagerActivity;
import spoklab.app.spoktools.fragments.collection.DetailsFragment;
import spoklab.app.spoktools.fragments.collection.FragmentListPager;

public abstract class ListDetailsPagerActivity<T>
extends PagerActivity {

    private int mCollectionDetailsPosition;

    @NonNull
    @Override
    protected final Fragment[] onSetFragments() {
        return new Fragment[]{
                onSourceFragment(),
                onDetailFragment()
        };
    }

    public final void notifyItemChanged(
        @NonNull T f
    ) {
        if (mFragments == null) {
            return;
        }

        ((FragmentListPager<T>) mFragments[0])
            .notifyItemChanged(
                mCollectionDetailsPosition,
                f
            );
    }

    public final void setDetails(
        int position,
        @NonNull T collection,
        int modelId
    ) {
        if (mFragments == null) {
            return;
        }

        mCollectionDetailsPosition = position;

        ((DetailsFragment<T>) mFragments[1])
            .setDetails(
                collection,
                modelId
            );
    }

    @NonNull
    protected abstract FragmentListPager<T> onSourceFragment();

    @NonNull
    protected abstract DetailsFragment<T> onDetailFragment();
}
