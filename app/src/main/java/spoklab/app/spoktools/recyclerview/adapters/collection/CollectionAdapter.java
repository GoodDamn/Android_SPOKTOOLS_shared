package spoklab.app.spoktools.recyclerview.adapters.collection;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import spoklab.app.spoktools.listeners.recyclerview.topics.OnClickModelListener;
import spoklab.app.spoktools.listeners.recyclerview.topics.OnLongClickModelListener;
import spoklab.app.spoktools.models.fileExtensions.FileSCS;
import spoklab.app.spoktools.models.fileExtensions.FileSPC;
import spoklab.app.spoktools.recyclerview.adapters.BaseAdapter;
import spoklab.app.spoktools.recyclerview.viewholders.ViewHolderCollection;

public class CollectionAdapter
extends BaseAdapter<
    FileSCS,
    ViewHolderCollection
> {

    public CollectionAdapter(
        @NonNull ArrayList<Integer> collections
    ) {
        super(collections);
    }

    @NonNull
    @Override
    public ViewHolderCollection onCreateViewHolder(
        @NonNull ViewGroup parent,
        int viewType
    ) {
        return new ViewHolderCollection(
            parent.getContext()
        );
    }
}
