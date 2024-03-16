package spoklab.app.spoktools.recyclerview.adapters.topics;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import spoklab.app.spoktools.Application;
import spoklab.app.spoktools.cache.CacheData;
import spoklab.app.spoktools.enums.CardType;
import spoklab.app.spoktools.listeners.recyclerview.topics.OnClickModelListener;
import spoklab.app.spoktools.listeners.recyclerview.topics.OnLongClickModelListener;
import spoklab.app.spoktools.models.fileExtensions.FileSCS;
import spoklab.app.spoktools.models.fileExtensions.FileSPC;
import spoklab.app.spoktools.other.lambda.Lambda;
import spoklab.app.spoktools.recyclerview.adapters.BaseAdapter;
import spoklab.app.spoktools.recyclerview.viewholders.ViewHolderTopic;
import spoklab.app.spoktools.staticc.Keys;

public class TopicsAdapter
extends BaseAdapter<
    FileSPC,
    ViewHolderTopic
> {
    private static final String TAG = "TopicsAdapter";

    public TopicsAdapter() {
        super(new ArrayList<>());
    }

    public TopicsAdapter(
        @NonNull List<Integer> ids
    ) {
        super(ids);
    }

    @NonNull
    @Override
    public ViewHolderTopic onCreateViewHolder(
        @NonNull ViewGroup parent,
        int viewType
    ) {
        return new ViewHolderTopic(
            parent.getContext()
        );
    }

    public final void changeItemAt(
        int position,
        @NonNull final FileSPC item
    ) {
        final Integer id = mIds.get(
            position
        );

        Log.d(TAG, "changeItem: POSITION:" + position + " " + id);

        CacheData<FileSPC> cache = Application
            .CACHE_PREVIEWS
                .get(id);

        if (cache == null) {
            cache = ViewHolderTopic
                .createCache(id);
            Application
                .CACHE_PREVIEWS
                .put(id, cache);
        }

        cache.setCacheObject(
            item
        );

        notifyItemChanged(
            position
        );
    }

    public static void allTopicsList(
        @NonNull final Lambda<ArrayList<Integer>> lambda
    ) {
        final Task<ListResult> task = FirebaseStorage
            .getInstance()
            .getReference(Keys.STORAGE_TOPICS)
            .listAll();

        task.addOnSuccessListener(listResult -> {

            final List<StorageReference> prefixes = listResult
                .getPrefixes();

            if (prefixes.isEmpty()) {
                Log.d(TAG, "onSuccessListTopics: PREFIXES_NOT_FOUND");
                return;
            }

            final ArrayList<Integer> topics = new ArrayList<>();
            for (StorageReference ref: prefixes) {
                topics.add(Integer.valueOf(
                    ref.getName()
                ));
            }

            lambda.onExecute(topics);
        });
    }

    public static void allTopics(
        @NonNull final Lambda<TopicsAdapter> lambda
    ) {
        allTopicsList(list -> {
            lambda.onExecute(new TopicsAdapter(
                list
            ));
        });
    }
}
