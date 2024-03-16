package spoklab.app.spoktools.fragments.topics;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResultCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import spoklab.app.spoktools.activities.BaseActivity;
import spoklab.app.spoktools.fragments.BaseFragment;
import spoklab.app.spoktools.fragments.collection.DetailsFragment;
import spoklab.app.spoktools.listeners.fragments.topic.ModelDetailsListener;
import spoklab.app.spoktools.other.lambda.Lambda;
import spoklab.app.spoktools.staticc.utils.ViewUtils;

public abstract class FragmentDetailsPaged<MODEL>
extends DetailsFragment<MODEL>
implements ActivityResultCallback<Uri> {

    private static final String TAG = "FragmentDetailsPaged";

    @NonNull
    private BaseFragment<BaseActivity>[] mFragments;

    @NonNull
    private ViewPager2 mViewPager;

    @Nullable
    @Override
    protected final View onCreateView(
        @NonNull Context context
    ) {
        mFragments = onPageFragments();

        final LinearLayout layout = new
            LinearLayout(context);

        final Button btnClose = ViewUtils
            .buttonClose(context);

        mViewPager = new
            ViewPager2(context);

        layout.setOrientation(
            LinearLayout.VERTICAL
        );

        btnClose.setOnClickListener(
            this::onClickBtnClose
        );

        mViewPager.setBackgroundColor(
            0xffff0000
        );

        mViewPager.setOffscreenPageLimit(
            mFragments.length
        );

        mViewPager.setAdapter(new FragmentStateAdapter(
            getChildFragmentManager(),
            getLifecycle()
        ) {
            @NonNull
            @Override
            public Fragment createFragment(
                int position
            ) {
                return mFragments[position];
            }

            @Override
            public int getItemCount() {
                return mFragments.length;
            }
        });

        layout.addView(
            btnClose
        );

        layout.addView(
            mViewPager
        );

        return layout;
    }

    @Override
    public final void onReceiveModelDetails(
        @NonNull MODEL model,
        int topicId
    ) {
        fragmentIterator(f -> {
            if (!(f instanceof ModelDetailsListener)) {
                return;
            }

            ((ModelDetailsListener<MODEL>)f)
                .onReceiveModelDetails(
                    model,
                    topicId);
        });
    }

    @Override
    public final void onActivityResult(
        final Uri result
    ) {
        final Fragment focusFragment = mFragments[
            mViewPager.getCurrentItem()
        ];

        if (!(focusFragment instanceof ActivityResultCallback)) {
            return;
        }

        ((ActivityResultCallback<Uri>)focusFragment)
            .onActivityResult(result);
    }

    private void fragmentIterator(
        Lambda<BaseFragment<BaseActivity>> lambda
    ) {
        for (final BaseFragment<BaseActivity> f: mFragments) {
            lambda.onExecute(f);
        }
    }

    protected void onClickBtnClose(
        View view
    ) {
        getInstanceActivity()
            .backFragment();
    }

    @NonNull
    protected abstract BaseFragment<BaseActivity>[]
                    onPageFragments();
}
