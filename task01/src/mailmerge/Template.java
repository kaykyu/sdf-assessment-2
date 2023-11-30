package mailmerge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Template {
    
    public static List<String> readTemplate(String template) {

        List<String> list = new ArrayList<>();

        try (FileReader fr = new FileReader(template)) {
            BufferedReader br = new BufferedReader(fr);
            list = br.lines()
                    .filter(line -> line.length() > 0)
                    .map(words -> words.replaceAll("[^A-za-z0-9_ ]", ""))
                    .map(words -> words.trim().split(" "))
                    .flatMap(words -> (Stream.of(words)))
                    .filter(word -> word.startsWith("__"))
                    .toList();

        } catch (IOException e) {
            System.out.println("Error in reading template file");
        }

        return list;
    }
    
    public static List<String> template(String template) {

        List<String> list = new ArrayList<>();

        try (FileReader fr = new FileReader(template)) {
            BufferedReader br = new BufferedReader(fr);
            list = br.lines()
                    .toList();            

        } catch (IOException e) {
            System.out.println("Error in reading template");
        }

        return list;
    }
}
