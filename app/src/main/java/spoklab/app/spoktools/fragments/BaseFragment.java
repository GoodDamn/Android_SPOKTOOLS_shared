package spoklab.app.spoktools.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import spoklab.app.spoktools.activities.BaseActivity;

public abstract class BaseFragment<ACTIVITY extends BaseActivity>
extends Fragment {

    private ACTIVITY mActivity;

    @Nullable
    @Override
    public final View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        Context context = getContext();
        if (context == null) {
            return null;
        }

        mActivity = (ACTIVITY) getActivity();

        return onCreateView(context);
    }

    protected void toast(
        @NonNull String msg
    ) {
        Toast.makeText(
            getContext(),
            msg,
            Toast.LENGTH_SHORT
        ).show();
    }

    protected ACTIVITY getInstanceActivity() {
        return mActivity;
    }

    @Nullable
    protected abstract View onCreateView(
        @NonNull Context context
    );

}
