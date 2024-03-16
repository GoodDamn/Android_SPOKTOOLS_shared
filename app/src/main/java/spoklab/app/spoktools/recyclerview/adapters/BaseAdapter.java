package spoklab.app.spoktools.recyclerview.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import spoklab.app.spoktools.listeners.recyclerview.topics.OnClickModelListener;
import spoklab.app.spoktools.listeners.recyclerview.topics.OnLongClickModelListener;
import spoklab.app.spoktools.models.fileExtensions.FileSCS;
import spoklab.app.spoktools.recyclerview.viewholders.ViewHolderIdentified;

public abstract class BaseAdapter<
    MODEL,
    HOLDER extends ViewHolderIdentified<MODEL>
> extends RecyclerView.Adapter<HOLDER> {

    @Nullable
    protected OnClickModelListener<MODEL>
        mOnClickModelListener;

    @Nullable
    protected OnLongClickModelListener<MODEL>
        mOnLongClickModelListener;

    @NonNull
    protected List<Integer> mIds;

    public BaseAdapter(
        @NonNull List<Integer> ids
    ) {
        mIds = ids;
    }


    @Override
    public void onBindViewHolder(
        @NonNull HOLDER holder,
        int position
    ) {
        holder.setOnClickListener(
            mOnClickModelListener
        );

        holder.setOnLongClickListener(
            mOnLongClickModelListener
        );

        holder.load(
            mIds.get(
                position
            )
        );
    }

    @Override
    public int getItemCount() {
        return mIds.size();
    }

    public final void setOnClickModelListener(
        @Nullable OnClickModelListener<MODEL> listener
    ) {
        mOnClickModelListener = listener;
    }

    public final void setOnLongClickModelListener(
        @Nullable OnLongClickModelListener<MODEL> l
    ) {
        mOnLongClickModelListener = l;
    }

    public final void deleteItemAt(
        int position
    ) {
        mIds.remove(
            position
        );

        notifyItemRemoved(
            position
        );
    }

    public final void updateWith(
        @NonNull List<Integer> collections
    ) {
        mIds = collections;
        notifyDataSetChanged();
    }
}
