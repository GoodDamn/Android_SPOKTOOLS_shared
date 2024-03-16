package spoklab.app.spoktools.activities.finall.editor;

import androidx.annotation.NonNull;

import spoklab.app.spoktools.activities.collection.ListDetailsPagerActivity;
import spoklab.app.spoktools.fragments.collection.DetailsFragment;
import spoklab.app.spoktools.fragments.collection.FragmentListPager;
import spoklab.app.spoktools.fragments.topics.finall.FragmentListPagerTopics;
import spoklab.app.spoktools.fragments.topics.finall.FragmentTopicsEdit;
import spoklab.app.spoktools.models.fileExtensions.FileSPC;

public final class TopicsActivity
extends ListDetailsPagerActivity<FileSPC> {

    private static final String TAG = "TopicsActivity";

    @NonNull
    @Override
    protected FragmentListPager<FileSPC> onSourceFragment() {
        return new FragmentListPagerTopics();
    }

    @NonNull
    @Override
    protected DetailsFragment<FileSPC> onDetailFragment() {
        return new FragmentTopicsEdit();
    }

}
