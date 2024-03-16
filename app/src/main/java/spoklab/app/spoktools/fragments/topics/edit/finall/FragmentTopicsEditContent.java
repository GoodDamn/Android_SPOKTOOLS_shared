package spoklab.app.spoktools.fragments.topics.edit.finall;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import spoklab.app.spoktools.activities.BaseActivity;
import spoklab.app.spoktools.fragments.BaseFragment;
import spoklab.app.spoktools.staticc.utils.StringUtils;

public final class FragmentTopicsEditContent
extends BaseFragment<BaseActivity>
implements ActivityResultCallback<Uri> {

    private static final String TAG = "FragmentTopicsEditConte";

    @NonNull
    private TextView mTextViewUri;

    @Nullable
    @Override
    protected View onCreateView(
        @NonNull Context context
    ) {
        final LinearLayout layout = new
            LinearLayout(context);

        final Button btnImportSkc = new
            Button(context);

        mTextViewUri = new
            TextView(context);

        layout.setOrientation(
            LinearLayout.VERTICAL
        );

        btnImportSkc.setTextColor(
            0xff000000
        );

        layout.setBackgroundColor(
            0xffffffff
        );

        mTextViewUri.setText(
            "-------"
        );

        btnImportSkc.setText(
            "Import .skc"
        );

        btnImportSkc.setOnClickListener(
            this::onClickBtnImport
        );

        layout.addView(
            btnImportSkc,
            -1,
            -2
        );

        layout.addView(
            mTextViewUri,
            -1,
            -2
        );

        return layout;
    }

    @Override
    public void onActivityResult(
        Uri result
    ) {
        final String fileName = StringUtils
            .fileNameFrom(result);

        Log.d(TAG, "onActivityResult: " + fileName);

        if (fileName.contains(".skc")) {
            mTextViewUri.setText(fileName);
            return;
        }

        mTextViewUri.setText(
            "Incorrect file format. It needs '.skc'"
        );
    }

    private void onClickBtnImport(
        View view
    ) {
        getInstanceActivity()
            .launchContentBrowser(
                "*/*"
            );
    }
}

