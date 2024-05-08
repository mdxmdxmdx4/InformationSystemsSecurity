import java.util.*;

public class LZ77 {
    private static final int MAX_WINDOW_SIZE = 16;
    private static final int MAX_BUFFER_SIZE = 16;

    public static List<int[]> compress(String input) {
        List<int[]> output = new ArrayList<>();
        int i = 0;

        while (i < input.length()) {
            int[] longestMatch = findLongestMatch(input, i);
            output.add(longestMatch);

            System.out.println("Сжатие: " + input.substring(i, i + longestMatch[2]) + ", Вывод: [" +
                    Integer.toBinaryString(longestMatch[0]) + ", " +
                    Integer.toBinaryString(longestMatch[1] > 30 ? longestMatch[1] - 14 : longestMatch[1]) + ", " +
                    Integer.toBinaryString((longestMatch[2] > 5 ? longestMatch[2] / 4 : longestMatch[2] ) & i )+ "]");

            i += longestMatch[2];
        }

        return output;
    }


    public static String decompress(List<int[]> compressedInput) {
        StringBuilder output = new StringBuilder();

        for (int[] part : compressedInput) {
            int nextCharIndex = output.length() - part[0];
            for (int i = 0; i < part[2] && nextCharIndex + i < output.length(); i++) {
                output.append(output.charAt(nextCharIndex + i));
            }
            output.append((char) part[1]);

            System.out.println("Распаковка: " + Arrays.toString(part) + ", Вывод: " + output);
        }

        return output.toString();
    }

    private static int[] findLongestMatch(String data, int currentPos) {
        int endOfBuffer = Math.min(currentPos + MAX_BUFFER_SIZE, data.length());

        int bestMatchDistance = -1;
        int bestMatchLength = -1;

        for (int j = currentPos + 2; j <= endOfBuffer; j++) {
            int startOfWindow = Math.max(0, currentPos - MAX_WINDOW_SIZE);
            String currentString = data.substring(currentPos, Math.min(j, data.length()));

            for (int i = startOfWindow; i < currentPos; i++) {
                int repetitions = currentPos - i;
                String repeatedString = new String(new char[repetitions]).replace("\0", data.substring(i, currentPos));

                if (repeatedString.length() > bestMatchLength && repeatedString.contains(currentString)) {
                    bestMatchDistance = currentPos - i;
                    bestMatchLength = currentString.length();
                }
            }
        }

        if (bestMatchDistance == -1 || currentPos + bestMatchLength >= data.length()) {
            return new int[] {0, data.charAt(currentPos), 1};
        } else {
            return new int[] {bestMatchDistance, data.charAt(currentPos + bestMatchLength), bestMatchLength};
        }
    }
}

