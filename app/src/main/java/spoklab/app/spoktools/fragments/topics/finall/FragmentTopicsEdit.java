package spoklab.app.spoktools.fragments.topics.finall;

import android.net.Uri;

import androidx.activity.result.ActivityResultCallback;
import androidx.annotation.NonNull;

import spoklab.app.spoktools.activities.BaseActivity;
import spoklab.app.spoktools.activities.collection.ListDetailsPagerActivity;
import spoklab.app.spoktools.fragments.BaseFragment;
import spoklab.app.spoktools.fragments.topics.FragmentDetailsPaged;
import spoklab.app.spoktools.fragments.topics.edit.finall.FragmentTopicsEditContent;
import spoklab.app.spoktools.fragments.topics.edit.finall.FragmentTopicsEditPreviews;
import spoklab.app.spoktools.models.fileExtensions.FileSPC;

public final class FragmentTopicsEdit
extends FragmentDetailsPaged<FileSPC> {

    @NonNull
    @Override
    protected BaseFragment<BaseActivity>[] onPageFragments() {
        return new BaseFragment[] {
            new FragmentTopicsEditPreviews(),
            new FragmentTopicsEditContent()
        };
    }

}
