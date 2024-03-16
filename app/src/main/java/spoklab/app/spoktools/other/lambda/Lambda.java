package spoklab.app.spoktools.other.lambda;

public interface Lambda<T> {
    void onExecute(T result);
}
