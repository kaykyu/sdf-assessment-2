package mailmerge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Csv {

    public static List<String[]> readCsv(String csv) {

        List<String[]> list = new ArrayList<>();

        try (FileReader fr = new FileReader(csv)) {
            BufferedReader br = new BufferedReader(fr);
            list = br.lines()
                    .map(words -> words.replace("\\n", "\r\n").split(","))
                    .toList();               

        } catch (IOException e) {
            System.out.println("Error in reading CSV file");
        }

        return list;
    }  
}
