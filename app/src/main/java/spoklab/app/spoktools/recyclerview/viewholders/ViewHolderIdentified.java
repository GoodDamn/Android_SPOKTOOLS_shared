package spoklab.app.spoktools.recyclerview.viewholders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import spoklab.app.spoktools.listeners.recyclerview.topics.OnClickModelListener;
import spoklab.app.spoktools.listeners.recyclerview.topics.OnLongClickModelListener;

public abstract class ViewHolderIdentified<MODEL>
extends RecyclerView.ViewHolder
implements View.OnClickListener,
    View.OnLongClickListener {

    @Nullable
    private OnClickModelListener<MODEL> mOnClickModelListener;

    @Nullable
    private OnLongClickModelListener<MODEL> mOnLongClickModelListener;

    protected int mRecycledId;

    public ViewHolderIdentified(
        @NonNull View itemView
    ) {
        super(itemView);
        itemView.setOnClickListener(
            this
        );

        itemView.setOnLongClickListener(
            this
        );
    }

    public final void setOnLongClickListener(
        @Nullable OnLongClickModelListener<MODEL> l
    ) {
        mOnLongClickModelListener = l;
    }

    public final void setOnClickListener(
        @Nullable OnClickModelListener<MODEL> l
    ) {
        mOnClickModelListener = l;
    }

    public final void load(
        int id
    ) {
        onLoad(id);
    }

    @Override
    public final void onClick(
        View v
    ) {
        if (mOnClickModelListener == null) {
            return;
        }
        onClickModel(
            mOnClickModelListener
        );
    }

    @Override
    public final boolean onLongClick(
        View v
    ) {
        if (mOnLongClickModelListener == null) {
            return false;
        }

        onLongClickModel(
            mOnLongClickModelListener
        );
        return true;
    }

    protected abstract void onLoad(
        int modelId
    );

    protected abstract void onLongClickModel(
        @NonNull OnLongClickModelListener<MODEL> listener
    );

    protected abstract void onClickModel(
        @NonNull OnClickModelListener<MODEL> listener
    );
}
