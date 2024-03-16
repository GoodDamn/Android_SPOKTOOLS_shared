package spoklab.app.spoktools.activities.pager;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import good.damn.traceview.views.BlockedViewPager;
import spoklab.app.spoktools.activities.BaseActivity;
import spoklab.app.spoktools.fragments.BaseFragment;

public abstract class PagerActivity
extends BaseActivity {

    private static final String TAG = "PagerActivity";
    
    @Nullable
    protected Fragment[] mFragments;

    @NonNull
    private ViewPager2 mViewPager;

    @Override
    public final void onCreate(
        @Nullable Bundle savedInstanceState
    ) {
        super.onCreate(savedInstanceState);

        mFragments = onSetFragments();

        mViewPager = new ViewPager2(
                this
        );

        mViewPager.setId(ViewCompat
                .generateViewId()
        );

        mViewPager.setOffscreenPageLimit(
            mFragments.length
        );

        mViewPager.setUserInputEnabled(
            false
        );

        mViewPager.setAdapter(new FragmentStateAdapter(
                this
        ) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return mFragments[position];
            }

            @Override
            public int getItemCount() {
                return mFragments.length;
            }
        });

        setContentView(mViewPager);
    }

    @Override
    public final void onActivityResult(
        Uri result
    ) {
        if (mFragments == null || result == null) {
            return;
        }

        final Fragment focusFragment = mFragments[
            mViewPager.getCurrentItem()
        ];

        if (!(focusFragment instanceof ActivityResultCallback)) {
            return;
        }

        ((ActivityResultCallback<Uri>)focusFragment)
            .onActivityResult(result);
    }

    public final void backFragment() {
        if (mFragments == null) {
            return;
        }

        int back = mViewPager.getCurrentItem() - 1;

        if (back < 0) {
            return;
        }

        mViewPager.setCurrentItem(
                back
        );
    }

    public final void nextFragment() {
        if (mFragments == null) {
            Log.d(TAG, "nextFragment: FRAGMENTS IS NULL");
            return;
        }

        int next = mViewPager.getCurrentItem() + 1;

        if (next >= mFragments.length) {
            Log.d(TAG, "nextFragment: next > frag.length: " + next + " " + mFragments.length);
            return;
        }

        mViewPager.setCurrentItem(
                next
        );
    }

    @NonNull
    protected abstract Fragment[] onSetFragments();
}
