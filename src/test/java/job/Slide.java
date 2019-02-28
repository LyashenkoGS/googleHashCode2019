package job;

import java.util.ArrayList;
import java.util.List;

class Slide {
    List<Photo> photos = new ArrayList<>();


    @Override
    public String toString() {
        return "Slide{" +
                "photos=" + photos +
                '}' + "\n";
    }
}
