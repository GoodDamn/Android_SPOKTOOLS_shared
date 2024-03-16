package spoklab.app.spoktools.enums;

import androidx.annotation.NonNull;

public enum CardTypeByte {
    B((byte)0),
    M((byte)1);

    private byte v;

    CardTypeByte(
        byte v
    ) {
        this.v = v;
    }

    public final byte value() {
        return v;
    }

    @NonNull
    @Override
    public final String toString() {
        return String.valueOf(
            v
        );
    }
}
