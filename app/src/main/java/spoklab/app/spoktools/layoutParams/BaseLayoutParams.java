package spoklab.app.spoktools.layoutParams;

import android.view.ViewGroup;

public final class BaseLayoutParams
extends ViewGroup.LayoutParams {

    public BaseLayoutParams(
            int width,
            int height
    ) {
        super(width, height);
    }


    public BaseLayoutParams copy() {
        return new BaseLayoutParams(
                width,
                height
        );
    }

}
