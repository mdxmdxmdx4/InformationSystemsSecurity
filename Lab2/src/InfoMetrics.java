import java.util.HashMap;
import java.util.Map;

public class InfoMetrics {
    public static double computeEntropy(String inputData, char[] symbolSet) {
        Map<Character, Integer> symbolCount = new HashMap<>();
        int dataLength = inputData.length();

        for (char symbol : symbolSet) {
            symbolCount.put(symbol, 0);
        }

        for (int i = 0; i < dataLength; i++) {
            char symbol = inputData.charAt(i);
            if (symbolCount.containsKey(symbol)) {
                symbolCount.put(symbol, symbolCount.get(symbol) + 1);
            }
        }

        double entropy = 0;
        for (char symbol : symbolSet) {
            int count = symbolCount.get(symbol);
            double probability = (double) count / dataLength;
            if (probability > 0) {
                entropy += probability * (Math.log(probability) / Math.log(2));
                System.out.println(-probability * (Math.log(probability) / Math.log(2)));
            } else {
                System.out.println(0);
            }
        }
        entropy = -entropy;
        System.out.println("Энтропия: " + entropy);
        return entropy;
    }

    public static String binary(String text) {
        StringBuilder binaryStr = new StringBuilder();
        for (char c : text.toCharArray()) {
            binaryStr.append(String.format("%8s", Integer.toBinaryString(c)).replaceAll(" ", "0"));
        }
        return binaryStr.toString();
    }

    public static double countInformation(String text, double entropy) {
        text = text.replace(" ", "");
        return text.length() * entropy;
    }

    public static double withError(double error) {
        double errEntropy = (1 - (-error * Math.log(error) / Math.log(2) - (1 - error) * Math.log((1 - error) / Math.log(2))));
        return errEntropy;
    }
}
