package spoklab.app.spoktools.recyclerview.adapters.topics;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

import spoklab.app.spoktools.recyclerview.viewholders.ViewHolderTopic;

public final class TopicsAdapterAdd
extends TopicsAdapter
implements View.OnClickListener {

    private static final String TAG = "TopicsAdapterAdd";

    private static final int VIEW_TYPE_TOPIC = 0;
    private static final int VIEW_TYPE_ADD = 1;

    public TopicsAdapterAdd(
        @NonNull final List<Integer> list
    ) {
        super(list);
    }

    @Override
    public int getItemViewType(
        int position
    ) {
        if (position == getItemCount() - 1) {
            return VIEW_TYPE_ADD;
        }

        return VIEW_TYPE_TOPIC;
    }

    @NonNull
    @Override
    public ViewHolderTopic onCreateViewHolder(
        @NonNull ViewGroup parent,
        int viewType
    ) {
        if (viewType == VIEW_TYPE_TOPIC) {
            return super.onCreateViewHolder(
                parent,
                viewType
            );
        }

        ViewHolderTopic redTopic = new ViewHolderTopic(
            parent.getContext()
        );

        redTopic.itemView.setBackgroundColor(
            0xffff0000
        );

        redTopic.itemView.setOnClickListener(
            this
        );

        return redTopic;
    }

    @Override
    public void onBindViewHolder(
        @NonNull ViewHolderTopic holder,
        int position
    ) {
        if (position >= mIds.size()) {
            return;
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    @Override
    public void onClick(
        View v
    ) {
        final int updatePosition = getItemCount() - 1;
        final int id = mIds.get(
            mIds.size() - 1
        ) + 1;

        mIds.add(
            id
        );

        Log.d(TAG, "onClick: POSITION: " + updatePosition);

        notifyItemInserted(
            updatePosition
        );
    }
}
