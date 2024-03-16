package spoklab.app.spoktools.models.fileExtensions.mutable;

import androidx.annotation.NonNull;

import java.util.List;

import spoklab.app.spoktools.models.fileExtensions.FileSCS;

public final class MutableFileSCS {

    public byte cardType;

    @NonNull
    public String title;

    @NonNull
    public List<Integer> topics;

    public MutableFileSCS(
        @NonNull FileSCS fileSCS
    ) {
        this.title = fileSCS.title;
        this.topics = fileSCS.topics;
        this.cardType = fileSCS.cardType;
    }

    public MutableFileSCS(
        @NonNull String title,
        @NonNull List<Integer> topics,
        byte cardType
    ) {
        this.title = title;
        this.topics = topics;
        this.cardType = cardType;
    }
}
