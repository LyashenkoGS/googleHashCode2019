package job;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws IOException {
        //read a file by lines
        List<Photo> photos = parseFile("input/a_example.txt");
        System.out.println(photos);
        //output
        Slideshow slideshow = new Slideshow();
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();
        Slide slide3 = new Slide();
        slide1.photos.add(photos.get(0));
        slide2.photos.add(photos.get(3));
        slide3.photos.add(photos.get(1));
        slide3.photos.add(photos.get(2));
        slideshow.numberOfSlides = 3;
        List<Slide> slides = Arrays.asList(slide1, slide2, slide3);
        slideshow.slides.addAll(slides);
        System.out.println(slideshow);
        System.out.println(slideshow.toOuput());
        Files.write(Paths.get("output/a_output.txt"), slideshow.toOuput().getBytes());
    }

    class Slideshow {
        long numberOfSlides;
        List<Slide> slides = new ArrayList<>();

        @Override
        public String toString() {
            return "Slideshow{" +
                    "numberOfSlides=" + numberOfSlides +
                    ", slides=" + slides +
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

    class Slide {
        List<Photo> photos = new ArrayList<>();

        @Override
        public String toString() {
            return "Slide{" +
                    "photos=" + photos +
                    '}';
        }
    }

    private List<Photo> parseFile(String fileName) throws IOException {
        Scanner scanner = new Scanner(Paths.get(fileName));
        int numberOfPhotos = scanner.nextInt();
        List<Photo> photos = new ArrayList<>(numberOfPhotos);
        scanner.nextLine();
        long counter = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] substring = line.split(" ");
            Photo photo = new Photo();
            photo.id = counter;
            counter++;
            photo.orientation = substring[0].toCharArray()[0];
            photo.tagsNumber = Integer.valueOf((substring[1]));
            photo.tags = new HashSet<>(photo.tagsNumber);
            photo.tags.addAll(Arrays.asList(substring).subList(2, 2 + photo.tagsNumber));
            photos.add(photo);
        }
        return photos;
    }

    class Photo {
        long id;
        char orientation;
        int tagsNumber;
        Set<String> tags;

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
}
