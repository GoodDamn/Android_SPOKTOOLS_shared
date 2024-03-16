package spoklab.app.spoktools.models.fileExtensions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public final class FileSCS {

    public final byte cardType;

    @NonNull
    public final String title;

    @NonNull
    public final List<Integer> topics;

    public FileSCS(
            @NonNull String title,
            @NonNull List<Integer> topics,
            byte cardType
    ) {
        this.title = title;
        this.topics = topics;
        this.cardType = cardType;
    }

}
