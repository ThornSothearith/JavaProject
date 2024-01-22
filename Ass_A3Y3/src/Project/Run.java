package Project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Run { 
    private static final String Myfile = "D:\\RUPP\\JAVA\\Ass.A3Y3\\image.txt";
    private static final Pattern IMAGE_PATTERN = Pattern.compile("\\b\\w+\\.(jpg|png|gif|bmp|jpeg)\\b", Pattern.CASE_INSENSITIVE);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please select an option:");
            System.out.println("1. List all image names");
            System.out.println("2. Display total number of images");
            System.out.println("3. Display duplicate image names");
            System.out.println("4. Exit");
            System.out.print("Your choice: ");
            int option = scanner.nextInt();
            switch (option) {
                case 1 -> listImageNames();
                case 2 -> displayTotalImages();
                case 3 -> displayDuplicateImageNames();
                case 4 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void listImageNames() {
        String Myfile = "D:\\RUPP\\JAVA\\Ass.A3Y3\\image.txt/";
        try (BufferedReader br = new BufferedReader(new FileReader(Myfile))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher = IMAGE_PATTERN.matcher(line);
                while (matcher.find()) {
                    System.out.println(matcher.group());
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file:");
            e.printStackTrace();
        }
    }

    private static void displayTotalImages() {
        String Myfile = "D:\\RUPP\\JAVA\\Ass.A3Y3\\image.txt/";
        int count =0;
        try (BufferedReader br = new BufferedReader(new FileReader(Myfile))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher = IMAGE_PATTERN.matcher(line);
                while (matcher.find()) {
                    count++;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file:");
            e.printStackTrace();
        }
        System.out.println("Total number of images: " + count);
    }

    private static void displayDuplicateImageNames() {
        Map<String, Integer> imageCounts = new HashMap<>();
        String Myfile = "D:\\RUPP\\JAVA\\Ass.A3Y3\\image.txt/";
        try (BufferedReader br = new BufferedReader(new FileReader(Myfile))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher = IMAGE_PATTERN.matcher(line);
                while (matcher.find()) {
                    String imageName = matcher.group();
                    imageCounts.put(imageName, imageCounts.getOrDefault(imageName, 0) + 1);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file:");
            e.printStackTrace();
        }
        System.out.println("Duplicate image names:");
        for (Map.Entry<String, Integer> entry : imageCounts.entrySet()) {
            if (entry.getValue() > 1) {
                System.out.println(entry.getKey());
            }
        }
        
    }
    
}