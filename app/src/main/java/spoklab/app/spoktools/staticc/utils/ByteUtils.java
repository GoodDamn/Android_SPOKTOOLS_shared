package spoklab.app.spoktools.staticc.utils;

public final class ByteUtils {

    public static byte[] integer(
        int val
    ) {
        return new byte[]{
            (byte) (val >> 24 & 0xff),
            (byte) (val >> 16 & 0xff),
            (byte) (val >> 8 & 0xff),
            (byte) (val & 0xff)
        };
    }

    public static byte[] shortt(
        int val
    ) {
        return new byte[] {
            (byte) (val >> 8 & 0xff),
            (byte) (val & 0xff)
        };
    }

    public static int integer(
            byte[] inp,
            int offset
    ) {
        return (inp[offset] & 0xff)<< 24 |
            (inp[offset+1] & 0xff) << 16 |
            (inp[offset+2] & 0xff) << 8 |
            (inp[offset+3] & 0xff);
    }

    public static short shortt(
            byte[] inp,
            int offset
    ) {
        return (short) (
            (inp[offset] & 0xff) << 8 |
            (inp[offset+1] & 0xff)
        );
    }

}
