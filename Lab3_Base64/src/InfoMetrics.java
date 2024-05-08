import java.util.Base64;
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
    public static String toBinary(String text) {
        StringBuilder binaryStr = new StringBuilder();
        for (char c : text.toCharArray()) {
            binaryStr.append(String.format("%8s", Integer.toBinaryString(c)).replaceAll(" ", "0"));
        }
        return binaryStr.toString();
    }
    public static String XOROperation(String a, String b) {
        int length = Math.max(a.length(), b.length());
        StringBuilder result = new StringBuilder(length);
        for(int i = 0; i < length; i++) {
            char aChar = i < a.length() ? a.charAt(i) : 0;
            char bChar = i < b.length() ? b.charAt(i) : 0;
            result.append(Integer.toBinaryString(aChar ^ bChar));
        }
        return result.toString();
    }
    public static String toBase64(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }
    public static double computeEntropyHartley(int aLength) {
        double hartleyEntropy = Math.log(aLength) / Math.log(2);
        return hartleyEntropy;
    }
    public static double computeRedundancy(double HsA, double HchA) {
        double R = ((HchA - HsA) / HchA) * 100;
        return R;
    }
}