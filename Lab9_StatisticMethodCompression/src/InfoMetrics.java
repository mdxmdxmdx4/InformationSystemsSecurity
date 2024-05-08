import java.util.*;

public class InfoMetrics {
    private static Map<Character, String> charPrefixHashMap = new HashMap<>();
    private static Map<Character, Double> frequency = new HashMap<>();

    public static void setFrequencyAndCompute(Map<Character, Double> probabilities) {
        frequency.clear();
        for (Map.Entry<Character, Double> entry : probabilities.entrySet()) {
            frequency.put(entry.getKey(), entry.getValue());
        }
        int totalSymbols = 86;
        List<Map.Entry<Character, Double>> list = new LinkedList<>(frequency.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        System.out.println("Вероятности встречи каждого символа в тексте:");
        for (Map.Entry<Character, Double> entry : list) {
            double probability = entry.getValue() / totalSymbols;
            System.out.println("Символ: " + entry.getKey() + ", вероятность: " + probability);
        }

        buildTree(list, 29, "");

        for (Map.Entry<Character, String> entry : charPrefixHashMap.entrySet()) {
            charPrefixHashMap.put(entry.getKey(), invertBits(entry.getValue()));
        }

        System.out.println("Символы и их коды в порядке убывания вероятности:");
        for (Map.Entry<Character, Double> entry : list) {
            System.out.println("Символ: " + entry.getKey() + ", код: " + charPrefixHashMap.get(entry.getKey()));
        }
    }

    public static void compress(String text) {
        int totalSymbols = text.length();
        for (char c : text.toCharArray()) {
            if (!frequency.containsKey(c)) {
                frequency.put(c, 0.0);
            }
            frequency.put(c, frequency.get(c) + 1.0);
        }

        List<Map.Entry<Character, Double>> list = new LinkedList<>(frequency.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        System.out.println("Вероятности встречи каждого символа в тексте:");
        for (Map.Entry<Character, Double> entry : list) {
            double probability = entry.getValue() / totalSymbols;
            System.out.println("Символ: " + entry.getKey() + ", вероятность: " + probability);
        }

        buildTree(list, totalSymbols, "");

        for (Map.Entry<Character, String> entry : charPrefixHashMap.entrySet()) {
            charPrefixHashMap.put(entry.getKey(), invertBits(entry.getValue()));
        }

        System.out.println("Символы и их коды в порядке убывания вероятности:");
        for (Map.Entry<Character, Double> entry : list) {
            System.out.println("Символ: " + entry.getKey() + ", код: " + charPrefixHashMap.get(entry.getKey()));
        }

    }
    private static void buildTree(List<Map.Entry<Character, Double>> list, int total, String prefix) {
        if (list.size() == 1) {
            charPrefixHashMap.put(list.get(0).getKey(), prefix);
        } else if (list.size() == 2) {
            charPrefixHashMap.put(list.get(0).getKey(), prefix + "0");
            charPrefixHashMap.put(list.get(1).getKey(), prefix + "1");
        } else {
            int i = 0;
            int totalWeight = 0;
            while (totalWeight < total / 2) {
                totalWeight += list.get(i++).getValue();
            }
            buildTree(list.subList(0, i), totalWeight, prefix + "0");
            buildTree(list.subList(i, list.size()), total - totalWeight, prefix + "1");
        }
    }

    private static String invertBits(String binaryString) {
        StringBuilder inverted = new StringBuilder();
        for (char c : binaryString.toCharArray()) {
            inverted.append(c == '0' ? '1' : '0');
        }
        return inverted.toString();
    }

    public static String encodeMessage(String message) {
        StringBuilder encoded = new StringBuilder();
        for (char c : message.toCharArray()) {
            encoded.append(charPrefixHashMap.get(c));
        }
        return encoded.toString();
    }

    public static String decodeMessage(String encodedMessage) {
        StringBuilder decoded = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        for (char c : encodedMessage.toCharArray()) {
            temp.append(c);
            for (Map.Entry<Character, String> entry : charPrefixHashMap.entrySet()) {
                if (entry.getValue().equals(temp.toString())) {
                    decoded.append(entry.getKey());
                    temp = new StringBuilder();
                    break;
                }
            }
        }
        return decoded.toString();
    }
}