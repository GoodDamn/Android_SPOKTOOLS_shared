package spoklab.app.spoktools.enums;

import androidx.annotation.NonNull;

import spoklab.app.spoktools.staticc.Keys;
import spoklab.app.spoktools.staticc.StorageApp;

public enum CardType {
    B("B"),
    M("M");

    private final String v;
    private final String mExtensionString;

    CardType(final String v) {
        this.v = v;
        mExtensionString = v + Keys.STORAGE_SPC_EXTENSION;
    }

    @NonNull
    public String toExtension() {
        return mExtensionString;
    }

    @NonNull
    @Override
    public String toString() {
        return v;
    }
}
