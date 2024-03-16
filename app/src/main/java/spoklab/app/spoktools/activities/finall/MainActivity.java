package spoklab.app.spoktools.activities.finall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import spoklab.app.spoktools.Application;
import spoklab.app.spoktools.activities.BaseActivity;
import spoklab.app.spoktools.activities.finall.editor.SleepActivity;
import spoklab.app.spoktools.activities.finall.editor.TopicsActivity;

public final class MainActivity
extends BaseActivity {

    private final View.OnClickListener mClickTopics = v -> {
        startActivity(
                new Intent(
                        MainActivity.this,
                        TopicsActivity.class
                )
        );
    };

    private final View.OnClickListener mClickSleep = v -> {
        startActivity(
                new Intent(
                        MainActivity.this,
                        SleepActivity.class
                )
        );
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(
                this
        );

        Button btnTopics = new Button(
                this
        );

        Button btnSleep = new Button(
                this
        );

        layout.setOrientation(
                LinearLayout.VERTICAL
        );

        btnTopics.setText("Topics");
        btnSleep.setText("Sleep collection");

        btnTopics.setOnClickListener(mClickTopics);
        btnSleep.setOnClickListener(mClickSleep);

        layout.addView(
                btnTopics,
                Application.WIDTH,
                (int) (Application.HEIGHT * 0.2f)
        );

        layout.addView(
                btnSleep,
                Application.WIDTH,
                (int) (Application.HEIGHT * 0.2f)
        );

        setContentView(layout);
    }
}
