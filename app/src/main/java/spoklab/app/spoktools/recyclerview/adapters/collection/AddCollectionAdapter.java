package spoklab.app.spoktools.recyclerview.adapters.collection;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import spoklab.app.spoktools.Application;
import spoklab.app.spoktools.enums.CardType;
import spoklab.app.spoktools.enums.CardTypeByte;
import spoklab.app.spoktools.models.fileExtensions.FileSCS;
import spoklab.app.spoktools.recyclerview.viewholders.ViewHolderCollection;

public class AddCollectionAdapter
extends CollectionAdapter
implements View.OnClickListener {

    protected static final int VIEW_TYPE_ADD = 1;
    protected static final int VIEW_TYPE_DEFAULT = 0;

    public AddCollectionAdapter(
        @NonNull ArrayList<Integer> collections
    ) {
        super(collections);
    }

    @Override
    public int getItemViewType(
        int position
    ) {
        if (position == getItemCount()-1) {
            return VIEW_TYPE_ADD;
        }

        return VIEW_TYPE_DEFAULT;
    }

    @NonNull
    @Override
    public ViewHolderCollection onCreateViewHolder(
        @NonNull ViewGroup parent,
        int viewType
    ) {
        if (viewType == VIEW_TYPE_DEFAULT) {
            return super.onCreateViewHolder(
                parent,
                viewType
            );
        }

        final ViewHolderCollection holder = new
            ViewHolderCollection(
                parent.getContext()
            );

        holder.itemView
            .setBackgroundColor(
                0xffff0000
            );

        holder.itemView
            .setOnClickListener(
                this
            );

        return holder;
    }

    @Override
    public void onBindViewHolder(
        @NonNull ViewHolderCollection viewHolderCollection,
        int position
    ) {
        if (position >= mIds.size()) {
            return;
        }

        super.onBindViewHolder(
            viewHolderCollection,
            position
        );
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    @Override
    public void onClick(
        View v
    ) {
        final int newPosition = mIds.size();
        final int newId = mIds.get(
            newPosition - 1
        ) + 1;

        mIds.add(newId);

        Application
            .CACHE_COLLECTIONS
            .put(newId,
                new FileSCS(
                    "New Collection",
                    new ArrayList<>(),
                    CardTypeByte.M
                        .value()
                )
            );

        notifyItemInserted(
            newPosition
        );
    }
}
