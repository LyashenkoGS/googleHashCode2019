package job;

import java.util.ArrayList;
import java.util.List;

class Slideshow {
    long numberOfSlides;
    List<Slide> slides = new ArrayList<>();

    @Override
    public String toString() {
        return "Slideshow{" +
                "numberOfSlides=" + numberOfSlides +
                ", slides=  \n" + slides +
                '}';
    }

    public String toOuput() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(numberOfSlides).append("\n");
        for (Slide slide : slides) {
            for (Photo photo : slide.photos) {
                stringBuilder.append(photo.id).append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
