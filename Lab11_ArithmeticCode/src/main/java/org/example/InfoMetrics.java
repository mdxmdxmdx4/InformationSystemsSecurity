package org.example;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashMap;
import java.util.Map;

public class InfoMetrics {
    private static Map<Character, Double[]> ranges = new LinkedHashMap<>();

    public static void main(String[] args) {
        String word = "достопримечательность";
        calculateInitialRanges(word);
        double encodedValue = performArithmeticEncoding(word);
        System.out.println("Закодированное значение: " + encodedValue);
        String decodedWord = performArithmeticDecoding(encodedValue, word.length());
        System.out.println("Декодированное слово: " + decodedWord);
    }

    private static void calculateInitialRanges(String word) {
        Map<Character, Double> probabilities = new LinkedHashMap<>();
        double totalLength = word.length();

        for (char ch : word.toCharArray()) {
            probabilities.put(ch, probabilities.getOrDefault(ch, 0.0) + 1);
        }
        probabilities.forEach((k, v) -> probabilities.put(k, v / totalLength));

        double lower = 0.0;
        double upper;

        for (Map.Entry<Character, Double> entry : probabilities.entrySet()) {
            upper = lower + entry.getValue();
            ranges.put(entry.getKey(), new Double[]{lower, upper});
            lower = upper;
        }
        System.out.println("Шаг 0");
        ranges.forEach((k, v) -> System.out.println("Для символа '" + k + "' диапазон: [" + v[0] + ", " + v[1] + "]"));
    }

    private static double performArithmeticEncoding(String word) {
        double lower = 0.0;
        double upper = 1.0;

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            Double[] currentRange = ranges.get(ch);
            double range = upper - lower;
            double newLower = lower + range * currentRange[0];
            double newUpper = lower + range * currentRange[1];
            lower = newLower;
            upper = newUpper;
            System.out.println("Шаг " + (i + 1) + ":");
            System.out.println(ch + "' диапазон: [" + newLower + ", " + newUpper + "]");
        }

        return (lower + upper) / 2.0;
    }

    private static String performArithmeticDecoding(double code, int length) {
        StringBuilder word = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            double finalCode = code;
            char ch = ranges.entrySet().stream()
                    .filter(entry -> entry.getValue()[0] <= finalCode && finalCode < entry.getValue()[1])
                    .findFirst()
                    .map(Map.Entry::getKey)
                    .orElseThrow(() -> new RuntimeException("Не удалось декодировать символ"));
            word.append(ch);
            Double[] currentRange = ranges.get(ch);
            code = (code - currentRange[0]) / (currentRange[1] - currentRange[0]);
        }
        return word.toString();
    }
}

