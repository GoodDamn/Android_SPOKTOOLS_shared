package spoklab.app.spoktools.staticc.utils;

public final class UtilsMath {

    public static boolean outRange(
        int left,
        int val,
        int right
    ) {
        return left >= val || val >= right;
    }

}
