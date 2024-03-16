package spoklab.app.spoktools.fragments.collection.finall;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;

import spoklab.app.spoktools.Application;
import spoklab.app.spoktools.enums.CardType;
import spoklab.app.spoktools.fragments.collection.DetailsFragment;
import spoklab.app.spoktools.models.fileExtensions.FileSCS;
import spoklab.app.spoktools.models.fileExtensions.mutable.MutableFileSCS;
import spoklab.app.spoktools.other.uploaders.CollectionUploader;
import spoklab.app.spoktools.recyclerview.adapters.topics.TopicsAdapter;
import spoklab.app.spoktools.staticc.utils.FileExtensionMaker;
import spoklab.app.spoktools.staticc.utils.UtilsFileExtension;
import spoklab.app.spoktools.staticc.utils.ViewUtils;
import spoklab.app.spoktools.views.bottomSheets.BottomSheetTopicsSelect;

public final class FragmentSleepDetails
extends DetailsFragment<FileSCS>
implements BottomSheetTopicsSelect.OnSelectListener,
    OnSuccessListener<UploadTask.TaskSnapshot>,
    OnProgressListener<UploadTask.TaskSnapshot> {

    @NonNull
    private EditText mEditTextTitle;

    @NonNull
    private TextView mTextViewProgress;

    @Nullable
    private TopicsAdapter mAdapter;

    @NonNull
    private MutableFileSCS mCollection;

    @NonNull
    private final Button[] mBtnCardTypes = new Button[2];

    @NonNull
    private final CollectionUploader mCollectionUploader = new
        CollectionUploader();

    @NonNull
    private static final CardType[] mCardTypes =
        CardType.values();

    private int mCollectionId;

    @NonNull
    @Override
    protected View onCreateView(
            @NonNull Context context
    ) {
        mCollectionUploader.setOnSuccessListener(
            this
        );

        mCollectionUploader.setOnProgressListener(
            this
        );

        final LinearLayout layout = new
            LinearLayout(context);

        final LinearLayout layoutLinearTab = new
            LinearLayout(context);

        mEditTextTitle = new
            EditText(context);

        mTextViewProgress = new
            TextView(context);

        mAdapter = new TopicsAdapter();

        final RecyclerView recyclerView = new
            RecyclerView(context);

        final Button btnSave = new
            Button(context);

        final Button btnSelectTopics = new
            Button(context);

        final Button btnUpload = new
            Button(context);

        final Button btnClose = ViewUtils
            .buttonClose(context);

        mEditTextTitle.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            Application.HEIGHT * 0.05f
        );

        btnUpload.setText(
            "Upload"
        );

        btnSelectTopics.setText(
            "Select topics"
        );

        btnSave.setText(
            "Save"
        );

        btnUpload.setOnClickListener(
            this::onClickBtnUpload
        );

        btnClose.setOnClickListener(
            this::onClickBtnClose
        );

        btnSave.setOnClickListener(
            this::onClickBtnSave
        );

        btnSelectTopics.setOnClickListener(
            this::onClickBtnSelectTopics
        );

        recyclerView.setHasFixedSize(
            true
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        ));

        recyclerView.setAdapter(
            mAdapter
        );

        layoutLinearTab.setOrientation(
            LinearLayout.HORIZONTAL
        );

        layout.setOrientation(
            LinearLayout.VERTICAL
        );

        layoutLinearTab.addView(
            btnClose
        );

        layoutLinearTab.addView(
            mTextViewProgress
        );

        layoutLinearTab.addView(
            btnUpload
        );

        layout.addView(
            layoutLinearTab,
            -1,
            -2
        );

        layout.addView(
            mEditTextTitle
        );

        layout.addView(
            btnSelectTopics,
            -1, // match parent
            -2
        );

        layout.addView(
            recyclerView,
            Application.WIDTH,
            (int) (Application.HEIGHT * 0.3f)
        );

        layout.addView(
            btnSave,
            -1,
            -2 // wrap content
        );

        for (byte i = 0; i < mBtnCardTypes.length; i++) {
            final Button btn = new Button(context);
            mBtnCardTypes[i] = btn;
            btn.setText("Card "+mCardTypes[i]);
            btn.setOnClickListener(
                this::onClickBtnCardType
            );

            layout.addView(
                btn,
                -1,
                -2
            );
        }

        return layout;
    }

    private void onClickBtnClose(
        View view
    ) {
        getInstanceActivity()
            .backFragment();
    }

    private void onClickBtnCardType(
        View view
    ) {
        for (byte i = 0; i < mBtnCardTypes.length; i++) {
            final Button btn = mBtnCardTypes[i];
            if (btn.equals(view)) {
                btn.setTextColor(
                    0xffff0000
                );
                mCollection.cardType = i;
                continue;
            }

            btn.setTextColor(
                0xff000000
            );
        }
    }

    private void onClickBtnSelectTopics(
        View view
    ) {
        final BottomSheetTopicsSelect sheet = new
            BottomSheetTopicsSelect();

        sheet.setOnSelectListener(
            this
        );

        sheet.show(
            getChildFragmentManager(),
            "sheet"
        );
    }

    private void onClickBtnSave(
        View view
    ) {
        final FileSCS scs = getScs();

        Application
            .CACHE_COLLECTIONS
            .put(
                mCollectionId,
                scs
            );

        notifyItemChanged(
            scs
        );
    }

    private void onClickBtnUpload(
        View view
    ) {
        final byte[] scs = FileExtensionMaker
            .mkSCS(getScs());

        if (scs == null) {
            toast("Something is missed for uploading process");
            return;
        }

        mCollectionUploader
            .upload(
                mCollectionId,
                scs
            );
    }

    @NonNull
    private FileSCS getScs() {
        return new FileSCS(
            mEditTextTitle.getText().toString(),
            mCollection.topics,
            mCollection.cardType
        );
    }

    @Override
    public void onReceiveModelDetails(
        @NonNull FileSCS collection,
        int collectionId
    ) {
        mCollection = new MutableFileSCS(
            collection
        );

        mCollectionId = collectionId;

        mEditTextTitle.setText(
            collection.title
        );

        mAdapter.updateWith(
            collection.topics
        );

        for (byte i = 0; i < mBtnCardTypes.length; i++) {
            mBtnCardTypes[i].setTextColor(
                i == mCollection.cardType ?
                    0xffff0000
                    : 0xff000000
            );
        }

    }

    @Override
    public void onSelectTopics(
        @NonNull ArrayList<Integer> topics
    ) {
        mCollection.topics = topics;
        mAdapter.updateWith(topics);
    }

    @Override
    public void onSuccess(
        UploadTask.TaskSnapshot taskSnapshot
    ) {
        mTextViewProgress.setText(
            ""
        );
    }

    @Override
    public void onProgress(
        @NonNull UploadTask.TaskSnapshot snapshot
    ) {
        mTextViewProgress.setText(
            snapshot.getBytesTransferred()
            + "/"
            + snapshot.getTotalByteCount()
        );
    }
}
