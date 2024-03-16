package spoklab.app.spoktools.activities.finall.editor;

import androidx.annotation.NonNull;

import spoklab.app.spoktools.activities.collection.ListDetailsPagerActivity;
import spoklab.app.spoktools.fragments.collection.DetailsFragment;
import spoklab.app.spoktools.fragments.collection.FragmentListPager;
import spoklab.app.spoktools.fragments.collection.finall.FragmentSleepDetails;
import spoklab.app.spoktools.fragments.collection.finall.FragmentListPagerSleep;
import spoklab.app.spoktools.models.fileExtensions.FileSCS;

public final class SleepActivity
extends ListDetailsPagerActivity<FileSCS> {

    @NonNull
    @Override
    protected FragmentListPager<FileSCS> onSourceFragment() {
        return new FragmentListPagerSleep();
    }

    @NonNull
    @Override
    protected DetailsFragment<FileSCS> onDetailFragment() {
        return new FragmentSleepDetails();
    }
}
