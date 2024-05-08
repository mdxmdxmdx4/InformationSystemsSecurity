import java.util.*;

public class InfoMetrics {

    public static String[] bwt(String input) {
        int length = input.length();
        ArrayList<String> table = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            table.add(input.substring(length - i) + input.substring(0, length - i));
        }

        System.out.println("Таблица циклических сдвигов:");
        for (String row : table) {
            System.out.println(row);
        }

        Collections.sort(table);

        System.out.println("\nОтсортированная таблица:");
        for (String row : table) {
            System.out.println(row);
        }

        StringBuilder lastColumn = new StringBuilder(length);
        for (String row : table) {
            lastColumn.append(row.charAt(length - 1));
        }
        int originalRow = table.indexOf(input);

        return new String[]{lastColumn.toString(), Integer.toString(originalRow)};
    }

    public static String invBwt(String lastColumn, int originalRow) {
        int length = lastColumn.length();
        ArrayList<String> table = new ArrayList<>(Collections.nCopies(length, ""));

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                table.set(j, lastColumn.charAt(j) + table.get(j));
            }
            Collections.sort(table);

            System.out.println("Шаг " + (i + 1) + ":");
            for (String row : table) {
                System.out.println(row);
            }
            System.out.println();
        }

        return table.get(originalRow);
    }

}
