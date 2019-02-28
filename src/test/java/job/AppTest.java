package job;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private static final String inputFolder = "../input/";
    public static final String OUTPUT_FOLDER = "../output";


    @Test
    public void SAT() throws IOException {
        Slideshow b = processTask("b_lovely_landscapes.txt");
        Slideshow c = processTask("c_memorable_moments.txt");
        Slideshow d = processTask("d_pet_pictures.txt");
        Slideshow e = processTask("e_shiny_selfies.txt");
        long b_result = resultScore(b);
        long c_result = resultScore(c);
        long d_result = resultScore(d);
        long e_result = resultScore(e);
        System.out.println("b_result: " + b_result);
        System.out.println("c_result: " + c_result);
        System.out.println("d_result: " + d_result);
        System.out.println("e_result: " + e_result);
        Assert.assertTrue(b_result >= 12);
        Assert.assertTrue(c_result >= 152);
        Assert.assertTrue(d_result >= 192962);
        Assert.assertTrue(e_result >= 112468);
    }

    @Test
    public void c_task() throws IOException {
        Slideshow c = processTask("c_memorable_moments.txt");
        long c_result = resultScore(c);
        Assert.assertTrue(c_result >= 152);
    }
    private Slideshow processTask(String fileName) throws IOException {
        List<Photo> photos = parseFile(inputFolder + fileName);
        //get vertical photos
        // System.out.println(photos);
        List<Photo> verticalPhotos = new ArrayList<>();
        for (Photo photo : photos) {
            if (photo.orientation == 'V') {
                verticalPhotos.add(photo);
            }
        }
        //  System.out.println(verticalPhotos);
        Slideshow slideshow = new Slideshow();
        //if vertical -> get pair
        for (int i = 0; i < verticalPhotos.size() - 1; i ++) {
            Slide slide = new Slide();
            Photo photo1 = verticalPhotos.get(i);
            Photo photo2 = verticalPhotos.get(i + 1);
            slide.photos.add(photo1);
            slide.photos.add(photo2);
            slideshow.slides.add(slide);
        }
        //else  put single photo slides
        photos.removeAll(verticalPhotos);
        for (Photo photo : photos) {
            Slide slide = new Slide();
            slide.photos.add(photo);
            slideshow.slides.add(slide);
        }
        slideshow.numberOfSlides = slideshow.slides.size();
        // System.out.println(slideshow);
        Files.createDirectories(Paths.get(OUTPUT_FOLDER));
        Files.write(Paths.get(OUTPUT_FOLDER + "/" + fileName), slideshow.toOuput().getBytes());
        return slideshow;
    }

    @Test
    public void parsingTest() throws IOException {
        //read a file by lines
        List<Photo> photos = parseFile("../input/a_example.txt");
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
        Files.createDirectories(Paths.get(OUTPUT_FOLDER));
        Files.write(Paths.get(OUTPUT_FOLDER + "/a_output.txt"), slideshow.toOuput().getBytes());
    }


    @Test
    public void tagsProcessingTest() {
        Slideshow slideshow = new Slideshow();
        Photo photo1 = new Photo();
        photo1.orientation = 'H';
        photo1.tagsNumber = 2;
        photo1.tags.addAll(Arrays.asList("cat", "garden"));
        Photo photo2 = new Photo();
        photo2.orientation = 'H';
        photo2.tagsNumber = 3;
        photo2.tags.addAll(Arrays.asList("garden", "selfie", "smile"));
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();
        slide1.photos.add(photo1);
        slide2.photos.add(photo2);
        slideshow.slides.addAll(Arrays.asList(slide1, slide2));
        slideshow.numberOfSlides = 2;

        System.out.println(slideshow);

        Set<String> intersection = intersection(photo1.tags, photo2.tags);
        Set<String> leftOuter = findDifferance(photo1.tags, intersection);
        Set<String> rightOuter = findDifferance(photo2.tags, intersection);

        assertEquals(intersection.toString(), "[garden]");
        assertEquals(leftOuter.toString(), "[cat]");
        assertEquals(rightOuter.toString(), "[selfie, smile]");
        System.out.println(findMin(intersection.size(), leftOuter.size(), rightOuter.size()));
    }

    int findMin(int a, int b, int c) {
        return Stream.of(a, b, c).mapToInt(x -> x).min().getAsInt();
    }


    public Set<String> findDifferance(Set<String> a, Set<String> b) {
        Set<String> results = new HashSet<>(a);
        results.removeAll(b);
        return results;
    }

    long resultScore(Slideshow slideshow) {
        long score = 0;
        //iterate over all the slides and get 3 factors
        for (int i = 0; i < slideshow.numberOfSlides - 1; i++) {
            Slide s1 = slideshow.slides.get(i);
            Slide s2 = slideshow.slides.get(i + 1);
            //get all the tags for s1 and s2
            Set<String> tags1 = new HashSet<>();
            Set<String> tags2 = new HashSet<>();
            for (Photo photo : s1.photos) {
                tags1.addAll(photo.tags);
            }
            for (Photo photo : s2.photos) {
                tags2.addAll(photo.tags);
            }
            Set<String> intersection = intersection(tags1, tags2);
            Set<String> leftOuter = findDifferance(tags1, tags2);
            Set<String> rightOuter = findDifferance(tags1, tags2);
            long scorePerPair = findMin(intersection.size(), leftOuter.size(), rightOuter.size());
            score += scorePerPair;
        }
        return score;
    }


    public static Set<String> intersection(Set<String> a, Set<String> b) {
        // unnecessary; just an optimization to iterate over the smaller set
        if (a.size() > b.size()) {
            return intersection(b, a);
        }
        Set<String> results = new HashSet<>();
        for (String element : a) {
            if (b.contains(element)) {
                results.add(element);
            }
        }
        return results;
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

}
