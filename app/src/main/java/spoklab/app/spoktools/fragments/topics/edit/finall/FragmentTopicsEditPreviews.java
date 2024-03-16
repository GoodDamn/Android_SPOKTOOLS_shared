package spoklab.app.spoktools.fragments.topics.edit.finall;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.CompoundButtonCompat;

import com.google.firebase.storage.UploadTask;

import spoklab.app.spoktools.Application;
import spoklab.app.spoktools.activities.BaseActivity;
import spoklab.app.spoktools.cache.CacheData;
import spoklab.app.spoktools.enums.CardType;
import spoklab.app.spoktools.fragments.topics.FragmentDetailsPagedController;
import spoklab.app.spoktools.fragments.topics.finall.FragmentTopicsEdit;
import spoklab.app.spoktools.listeners.cache.CacheFileListener;
import spoklab.app.spoktools.listeners.fragments.topic.ModelDetailsListener;
import spoklab.app.spoktools.models.fileExtensions.FileSPC;
import spoklab.app.spoktools.other.flexui.FlexUI;
import spoklab.app.spoktools.other.lambda.Lambda;
import spoklab.app.spoktools.other.uploaders.PreviewUploader;
import spoklab.app.spoktools.other.uploaders.Uploader;
import spoklab.app.spoktools.staticc.Keys;
import spoklab.app.spoktools.staticc.StorageApp;
import spoklab.app.spoktools.staticc.utils.BitmapUtils;
import spoklab.app.spoktools.staticc.utils.FileExtensionMaker;
import spoklab.app.spoktools.staticc.utils.StringUtils;
import spoklab.app.spoktools.staticc.utils.UtilsFileExtension;
import spoklab.app.spoktools.staticc.utils.UtilsMath;
import spoklab.app.spoktools.views.TopicView;

public final class FragmentTopicsEditPreviews
extends FragmentDetailsPagedController<
    BaseActivity,
    FileSPC,
    FragmentTopicsEdit
> implements ModelDetailsListener<FileSPC>,
    CacheFileListener,
    ActivityResultCallback<Uri> {

    private static final String TAG = "FragmentTopicsEditPrevi";

    @NonNull
    private final PreviewUploader mUploaderM = new
        PreviewUploader();

    @NonNull
    private final PreviewUploader mUploaderB = new
        PreviewUploader();

    @NonNull
    private TopicView mTopicViewM;

    @NonNull
    private TopicView mTopicViewB;

    @NonNull
    private EditText mEditTextColor;

    @NonNull
    private EditText mEditTextTitleM;
    @NonNull
    private EditText mEditTextDescM;

    @NonNull
    private EditText mEditTextTitleB;
    @NonNull
    private EditText mEditTextDescB;

    @NonNull
    private TextView mTextViewProgressM;

    @NonNull
    private TextView mTextViewProgressB;

    @NonNull
    private CheckBox mCheckBoxPremium;

    private int mTopicId;

    private boolean mNeedBitmapB = false;

    @Override
    public void onCreate(
        @Nullable Bundle savedInstanceState
    ) {
        mUploaderM
            .setOnProgressListener(
                this::onProgressM
            );

        mUploaderM
            .setOnSuccessListener(
                this::onSuccessM
            );

        mUploaderB
            .setOnProgressListener(
                this::onProgressB
            );

        mUploaderB
            .setOnSuccessListener(
                this::onSuccessB
            );


        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    protected View onCreateView(
        @NonNull Context context
    ) {
        final FrameLayout layout = new
            FrameLayout(context);

        mTopicViewM = new
            TopicView(
                context,
                Application.TOPIC_CONFIG_M
            );

        mTopicViewB = new
            TopicView(
                context,
                Application.TOPIC_CONFIG_B
            );

        mTextViewProgressB = new
            TextView(context);

        mTextViewProgressM = new
            TextView(context);

        mEditTextTitleM = new
            EditText(context);

        mEditTextDescM = new
            EditText(context);

        mEditTextTitleB = new
            EditText(context);

        mEditTextDescB = new
            EditText(context);

        mCheckBoxPremium = new
            CheckBox(context);

        mEditTextColor = new
            EditText(context);

        final Button btnSave = new
            Button(context);

        final Button btnUpload = new
            Button(context);

        layout.setBackgroundColor(
            0xffffffff
        );

        mTextViewProgressM.setTextColor(
            0xffffffff
        );

        mTextViewProgressB.setTextColor(
            0xffffffff
        );

        mEditTextColor.setTextColor(
            0xff000000
        );

        mEditTextTitleM.setTextColor(
            0xff000000
        );

        mEditTextDescM.setTextColor(
            0xff000000
        );

        mEditTextTitleB.setTextColor(
            0xff000000
        );

        mEditTextDescB.setTextColor(
            0xff000000
        );

        mTopicViewB.setBackgroundColor(
            0xff00ff00
        );

        mTopicViewM.setBackgroundColor(
            0xff00ff00
        );

        CompoundButtonCompat.setButtonTintList(
            mCheckBoxPremium,
            ColorStateList.valueOf(
                0xff000000
            )
        );

        mTextViewProgressM.setGravity(
            Gravity.CENTER
        );

        mTextViewProgressB.setGravity(
            Gravity.CENTER
        );

        layout.setFitsSystemWindows(
            true
        );

        mEditTextColor.setFilters(new InputFilter[]{
            new InputFilter.LengthFilter(8)
        });

        mEditTextColor.setInputType(
            InputType.TYPE_CLASS_TEXT
        );

        mEditTextTitleM.setHint(
            "Title M"
        );

        mEditTextDescM.setHint(
            "Desc M"
        );

        mEditTextTitleB.setHint(
            "Title B"
        );

        mEditTextDescB.setHint(
            "Desc B"
        );

        mEditTextColor.setHint(
            "FFFFFFFF"
        );

        btnSave.setText(
            "Save preview config"
        );

        btnUpload.setText(
            "Upload"
        );

        mEditTextColor.setAllCaps(
            true
        );

        mCheckBoxPremium.setText(
            "Premium"
        );

        mTopicViewB.calculatePositions();

        mTopicViewM.setOnClickListener(
            this::onClickTopicM
        );

        mTopicViewB.setOnClickListener(
            this::onClickTopicB
        );

        btnSave.setOnClickListener(
            this::onClickBtnSave
        );

        btnUpload.setOnClickListener(
            this::onClickBtnUpload
        );

        editTextListener(mEditTextColor, str -> {
            if (!str.matches("^[0-9a-f]+$")
                || str.length() != 8) {
                return;
            }
            final int color = Color.parseColor(
                "#"+str
            );
            mTopicViewM.setTitleColor(color);
            mTopicViewM.setDescColor(color);

            mTopicViewB.setTitleColor(color);
            mTopicViewB.setDescColor(color);

            mTopicViewM.update();
            mTopicViewB.update();
        });

        editTextListener(mEditTextTitleM, str -> {
            mTopicViewM.setTitle(str);
            mTopicViewM.calculatePositions();
            mTopicViewM.update();
        });

        editTextListener(mEditTextDescM, str -> {
            mTopicViewM.setDescription(str);
            mTopicViewM.calculatePositions();
            mTopicViewM.update();
        });

        editTextListener(mEditTextTitleB, str -> {
            mTopicViewB.setTitle(str);
            mTopicViewB.calculatePositions();
            mTopicViewB.update();
        });

        editTextListener(mEditTextDescB, str -> {
            mTopicViewB.setDescription(str);
            mTopicViewB.calculatePositions();
            mTopicViewB.update();
        });

        FlexUI.size( // (X,Y)=(0,0)
            mEditTextColor,
            0.21f,
            0.06f
        );

        FlexUI.bounds(mCheckBoxPremium,
            0.5f,
            0.06f,
            0.23f,
            0f
        );

        FlexUI.bounds(btnUpload,
            0.2f,
            0.06f,
            0.8f,
            0.0f
        );

        FlexUI.bounds(mTopicViewM,
            Application.TOPIC_CONFIG_M
                .width,
            Application.TOPIC_CONFIG_M
                .height,
            0f,
            0.06f);

        FlexUI.bounds(mTextViewProgressM,
            Application.TOPIC_CONFIG_M
                .width,
            Application.TOPIC_CONFIG_M
                .height,
            0f,
            0.06f);

        FlexUI.bounds(mEditTextTitleM,
            0.55f,
            0.09f,
            0.45f,
            0.06f);

        FlexUI.bounds(mEditTextDescM,
            0.55f,
            0.09f,
            0.45f,
            0.16f);

        FlexUI.bounds(mTopicViewB,
            Application.TOPIC_CONFIG_B.width,
            Application.TOPIC_CONFIG_B.height,
            0f,
            0.32f);

        FlexUI.bounds(mTextViewProgressB,
            Application.TOPIC_CONFIG_B.width,
            Application.TOPIC_CONFIG_B.height,
            0f,
            0.32f);

        FlexUI.bounds(mEditTextTitleB,
            0.99f,
            0.08f,
            0f,
            0.57f
        );

        FlexUI.bounds(mEditTextDescB,
            0.99f,
            0.08f,
            0f,
            0.66f);

        FlexUI.bounds(btnSave,
            0.99f,
            0.06f,
            0f,
            0.75f);

        layout.addView(
            mEditTextColor
        );

        layout.addView(
            mTopicViewM
        );

        layout.addView(
            mTextViewProgressM
        );

        layout.addView(
            mEditTextTitleM
        );

        layout.addView(
            mEditTextDescM
        );

        layout.addView(
            mTopicViewB
        );

        layout.addView(
            mTextViewProgressB
        );

        layout.addView(
            mEditTextTitleB
        );

        layout.addView(
            mEditTextDescB
        );

        layout.addView(
            mCheckBoxPremium
        );

        layout.addView(
            btnSave
        );

        layout.addView(
            btnUpload
        );

        return layout;
    }

    private void setTopicViewModel(
        final TopicView topicView,
        final FileSPC model
    ) {
        topicView.setTitle(
            model.title
        );

        topicView.setDescription(
            model.description
        );

        topicView.setBitmapImage(
            model.image
        );

        topicView.setTitleColor(
            model.color
        );

        topicView.setDescColor(
            model.color
        );

        topicView.calculatePositions();
        topicView.update();
    }

    private void onClickTopicM(
        View view
    ) {
        mNeedBitmapB = false;
        getInstanceActivity()
            .launchContentBrowser(
                "image/png"
            );
    }

    private void onClickTopicB(
        View view
    ) {
        mNeedBitmapB = true;
        getInstanceActivity()
            .launchContentBrowser(
                "image/png"
            );
    }

    private void onClickBtnSave(
        View view
    ) {
        final FileSPC spcm = getSpcM();

        saveDataPreview(
            spcm,
            CardType.M
        );

        saveDataPreview(
            getSpcB(),
            CardType.B
        );

        notifyItemChanged(
            spcm
        );
    }

    private void onClickBtnUpload(
        View view
    ) {
        uploadSpc(
            getSpcM(),
            CardType.M,
            mUploaderM
        );

        uploadSpc(
            getSpcB(),
            CardType.B,
            mUploaderB
        );
    }

    private void onSuccessM(
        UploadTask.TaskSnapshot snapshot
    ) {
        mTextViewProgressM.setText(
            ""
        );
    }

    private void onProgressM(
        @NonNull UploadTask.TaskSnapshot snapshot
    ) {
        mTextViewProgressM.setText(
            snapshot.getBytesTransferred()
                + "/"
                + snapshot.getTotalByteCount()
        );
    }

    private void onSuccessB(
        UploadTask.TaskSnapshot snapshot
    ) {
        mTextViewProgressB.setText(
            ""
        );
    }

    private void onProgressB(
        @NonNull UploadTask.TaskSnapshot snapshot
    ) {
        mTextViewProgressB.setText(
            snapshot.getBytesTransferred()
                + "/"
                + snapshot.getTotalByteCount()
        );
    }

    @NonNull
    private FileSPC getSpcM() {
        return new FileSPC(
            mEditTextTitleM.getText().toString(),
            mEditTextDescM.getText().toString(),
            mTopicViewM.getBitmapImage(),
            mCheckBoxPremium.isChecked(),
            (byte)0,
            mTopicViewM.getTitleColor()
        );
    }

    @NonNull
    private FileSPC getSpcB() {
        return new FileSPC(
            mEditTextTitleB.getText().toString(),
            mEditTextDescB.getText().toString(),
            mTopicViewB.getBitmapImage(),
            mCheckBoxPremium.isChecked(),
            (byte)0,
            mTopicViewB.getTitleColor()
        );
    }

    private void uploadSpc(
        @NonNull final FileSPC spc,
        @NonNull final CardType cardType,
        @NonNull final PreviewUploader uploader
    ) {
        final byte[] spcBytes = FileExtensionMaker
            .mkSPC(
                spc
            );

        if (spcBytes == null) {
            toast("Some issue with uploading for " + cardType.toExtension());
            return;
        }

        uploader.upload(
            mTopicId,
            cardType,
            spcBytes
        );
    }

    private void saveDataPreview(
        final FileSPC spc,
        final CardType cardType
    ) {
        final byte[] dataM = FileExtensionMaker
            .mkSPC(spc);

        if (dataM == null) {
            toast("Some data is missed for saving " + cardType.toExtension());
            return;
        }

        StorageApp.preview(
            mTopicId,
            cardType,
            Application.LANGUAGE_CODE,
            dataM
        );
    }

    private void editTextListener(
        @NonNull final EditText editText,
        @NonNull final Lambda<String> lambda
    ) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(
                CharSequence s,
                int start,
                int before,
                int count
            ) {
                lambda.onExecute(s.toString());
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void setTopicBitmap(
        @NonNull Uri uri,
        TopicView dstTopicView,
        int targetWidth
    ) {
        final Context context = getContext();
        if (context == null) {
            return;
        }

        final Bitmap b = BitmapUtils
            .getBitmapUri(
                context,
                uri
            );

        if (b == null) {
            return;
        }

        if (UtilsMath.outRange(
            targetWidth-3,
            b.getWidth(),
            targetWidth+3)
        ) {
            toast("Incorrect width: " + b.getWidth() + " Need: " + targetWidth);
            return;
        }

        if (UtilsMath.outRange(
            621 - 3,
            b.getHeight(),
            621 + 3
        )) {
            toast("Incorrect height: " + b.getHeight() + " Need: " + 621);
            return;
        }

        dstTopicView.setBitmapImage(
            b
        );

        dstTopicView.update();
    }

    @Override
    public void onReceiveModelDetails(
        @NonNull FileSPC spcm,
        int topicId
    ) {
        mTopicId = topicId;

        mEditTextColor.setText(
            Integer.toHexString(
                spcm.color
            )
        );

        setTopicViewModel(
            mTopicViewM,
            spcm
        );

        mCheckBoxPremium.setChecked(
            spcm.isPremium
        );

        mEditTextTitleM.setText(
            spcm.title
        );

        mEditTextDescM.setText(
            spcm.description
        );

        final CacheData<FileSPC> mCacheSPCB = new CacheData<>(
            Keys.STORAGE_TOPICS + topicId + "/"+ CardType.B.toExtension(),
            StorageApp.previewUrl(
                topicId,
                CardType.B,
                Application.LANGUAGE_CODE
            )
        );

        mCacheSPCB.setCacheListener(this);
        mCacheSPCB.load();
    }

    @Override
    public void onActivityResult(
        @NonNull final Uri result
    ) {
        final String fileName = StringUtils
            .fileNameFrom(result);

        if (mNeedBitmapB) {
            setTopicBitmap(
                result,
                mTopicViewB,
                1053
            );
            return;
        }

        setTopicBitmap(
            result,
            mTopicViewM,
            498
        );

        Log.d(TAG, "onActivityResult: FILE_NAME: " + fileName);
    }

    @Override
    public void onFileCache(
        @Nullable byte[] data
    ) {
        if (data == null) {
            Log.d(TAG, "onFileCache: DATA=null");
            return;
        }

        final FileSPC fileB = UtilsFileExtension
            .spc(
                data,
                Application.TOPIC_CONFIG_B
                    .layoutParams
            );

        mEditTextTitleB.setText(
            fileB.title
        );

        mEditTextDescB.setText(
            fileB.description
        );

        setTopicViewModel(
            mTopicViewB,
            fileB
        );

    }

    @Override
    public void onNetCache(
        @Nullable byte[] data
    ) {
        if (data == null) {
            Log.d(TAG, "onNetCache: DATA=null");
            return;
        }

        StorageApp.preview(
            mTopicId,
            CardType.B,
            Application.LANGUAGE_CODE,
            data
        );

        onFileCache(data);
    }

    @Override
    public void onErrorCache() {}
}