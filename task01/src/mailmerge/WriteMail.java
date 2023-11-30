package mailmerge;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WriteMail {

    public static void writeMail(Map<String, Integer> fields, List<String[]> data) {

        String path = "%s".formatted(Main.getTemplate().substring(0, Main.getTemplate().length() - 4));
        File save = new File(path);

        if (!save.exists()) {
            save.mkdirs();
            System.out.println("Directory created");
        } else {
            System.out.println("Directory exists");
        }

        for (int i = 1; i < data.size(); i++) {

            try (FileWriter fw = new FileWriter("%s/%d.txt".formatted(path, i))) {

                BufferedWriter bw = new BufferedWriter(fw);
                List<String> template = Template.template(Main.getTemplate());
                for (int j = 0; j < template.size(); j++) {

                    String line = template.get(j);
                    
                    while (line.contains("_")) {
                        for (String key : fields.keySet()) {
                            if (line.contains(key)) {
                                line = line.replace(key, data.get(i)[fields.get(key)]);
                            }
                        }
                    }

                    bw.write("%s\n".formatted(line));
                    bw.flush();
                }

            } catch (IOException e) {
                System.out.println("Error writing mail");
            }

        }

    }
}
