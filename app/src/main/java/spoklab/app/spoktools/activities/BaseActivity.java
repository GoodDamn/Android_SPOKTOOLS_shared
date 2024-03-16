package spoklab.app.spoktools.activities;

import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity
extends AppCompatActivity
implements ActivityResultCallback<Uri> {

    private ActivityResultLauncher<String> mContentBrowser;

    @Override
    public void onCreate(
        @Nullable Bundle savedInstanceState
    ) {
        super.onCreate(savedInstanceState);
        mContentBrowser = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            this
        );
    }

    @Override
    public final void onCreate(
        @Nullable Bundle savedInstanceState,
        @Nullable PersistableBundle persistentState
    ) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onActivityResult(
        Uri result
    ) {

    }

    public final void launchContentBrowser(
        @NonNull String mimeType
    ) {
        mContentBrowser.launch(
            mimeType
        );
    }
}
