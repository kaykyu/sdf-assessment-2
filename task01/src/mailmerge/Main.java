package mailmerge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private static String csv;
    private static String template;

    public static String getCsv() {
        return csv;
    }

    public static String getTemplate() {
        return template;
    }

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Please input CSV and template file to run Mail Merge");
            System.exit(0);
        }
        
        csv = args[0];
        template = args[1];

        List<String> variables = Template.readTemplate(template);
        List<String[]> data = Csv.readCsv(csv);
        String[] headers = data.get(0);

        System.out.println(variables.toString());
        System.out.println(Arrays.toString(data.get(0)));

        Map<String, Integer> fields = new HashMap<>();

        for (int j = 0; j < variables.size(); j++) {
            for (int i = 0; i < headers.length; i++) {
                if (variables.get(j).replaceAll("\\__", "").equals(headers[i])) {
                    fields.put(variables.get(j), i);
                }
            }
        }

        WriteMail.writeMail(fields, data);
    }
}