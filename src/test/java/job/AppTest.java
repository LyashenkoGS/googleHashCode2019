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

    private static final String inputFolder = "../input/";
    public static final String OUTPUT_FOLDER = "../output";

    @Test
    public void commonTagsTest(){

        Slideshow slideshow = new Slideshow();
        Photo photo1 = new Photo();
        photo1.orientation='H';
        photo1.tagsNumber =2;
        photo1.tags.addAll(Arrays.asList("cat", "garden"));
        Photo photo2 = new Photo();
        photo2.orientation='H';
        photo2.tagsNumber =3;
        photo2.tags.addAll(Arrays.asList("garden", "selfie", "smile"));
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();
        slide1.photos.add(photo1);
        slide2.photos.add(photo2);
        slideshow.slides.addAll(Arrays.asList(slide1,slide2));
        slideshow.numberOfSlides = 2;

        System.out.println(slideshow);

        intersection()






    }

    public static Set<Integer> intersection(Set<Stir> a, Set<Integer> b) {
        // unnecessary; just an optimization to iterate over the smaller set
        if (a.size() > b.size()) {
            return intersection(b, a);
        }

        Set<Integer> results = new HashSet<>();

        for (Integer element : a) {
            if (b.contains(element)) {
                results.add(element);
            }
        }

        return results;
    }

//    long commonTags(Slideshow slideshow){
//
//
//        for (int i = 0; i < slideshow.slides.size()-1; i++) {
//            i+=2;
//
//
//
//        }
//
//
//
//    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void example() throws IOException {
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
    public void c_problem() throws IOException {
        processTask("a_example.txt");
        processTask("b_lovely_landscapes.txt");
        processTask("d_pet_pictures.txt");
        processTask("c_memorable_moments.txt");
        processTask("e_shiny_selfies.txt");
    }

    private void processTask(String fileName) throws IOException {
        List<Photo> photos = parseFile(inputFolder +fileName);
        //get vertical photos
        // System.out.println(photos);
        List<Photo> verticalPhotos = new ArrayList<>();
        for (Photo photo : photos) {
            if (photo.orientation == 'V') {
                verticalPhotos.add(photo);
            }
        }
        System.out.println(verticalPhotos);
        Slideshow slideshow = new Slideshow();
        //if vertical -> get pair
        for (int i = 0; i < verticalPhotos.size() - 1; i += 2) {
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
        System.out.println(slideshow);
        Files.createDirectories(Paths.get(OUTPUT_FOLDER));
        Files.write(Paths.get(OUTPUT_FOLDER + "/" + fileName), slideshow.toOuput().getBytes());
    }


    class App{
        Slideshow slideshow = new Slideshow();

        App() throws IOException {
        }

        void makeSlides(Photo photo){

            boolean isTwoPhotosInSlide = false;

            if (photo.orientation=='H'){
                Slide slide = new Slide();
                slide.photos.add(photo);
                slideshow.slides.add(slide);
            } else {
                if (!isTwoPhotosInSlide){
                    // 1 photo in slide
                    Slide slide = new Slide();
                    slide.photos.add(photo);
                    isTwoPhotosInSlide=true;
                } else {
                    Slide slide = new Slide();
                    slide.photos.add(photo);
                    slideshow.slides.add(slide);
                    isTwoPhotosInSlide=false;
                }
            }
        }


        List<Photo> photos = parseFile("../input/c_memorable_moments.txt");

        @Test
        void doStuff(){
            for (Photo photo : photos) {

            }
        }


    }








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

    class Slide {
        List<Photo> photos = new ArrayList<>();


        @Override
        public String toString() {
            return "Slide{" +
                    "photos=" + photos +
                    '}' + "\n";
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
}
