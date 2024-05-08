import java.util.*;

public class InfoMetricsBWT {

    public static String[] bwt(String input) {
        int length = input.length();
        ArrayList<String> table = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            table.add(input.substring(length - i) + input.substring(0, length - i));
        }

        Collections.sort(table);

        StringBuilder lastColumn = new StringBuilder(length);
        for (String row : table) {
            lastColumn.append(row.charAt(length - 1));
        }
        int originalRow = table.indexOf(input);

        return new String[]{lastColumn.toString(), Integer.toString(originalRow)};
    }

}
