package job;

import java.util.HashSet;
import java.util.Set;

class Photo {
    long id;
    char orientation;
    int tagsNumber;
    Set<String> tags = new HashSet<>();

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", orientation=" + orientation +
                ", tagsNumber=" + tagsNumber +
                ", tags=" + tags +
                '}' + "\n";
    }
}
