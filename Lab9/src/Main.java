import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("На основе сжатия:");
        String text = "лешукдмитрий";
        InfoMetrics.compress(text);

        String encodedMessage = InfoMetrics.encodeMessage(text);
        System.out.println("Закодированное сообщение(а): " + encodedMessage);

        String decodedMessage = InfoMetrics.decodeMessage(encodedMessage);
        System.out.println("Декодированное сообщение(а): " + decodedMessage);

        System.out.println("\nНа основе результатов лабораторной №2");
        Map<Character, Double> probabilities = new HashMap<>();
        probabilities.put('л', 2.0);
        probabilities.put('е', 8.0);
        probabilities.put('ш', 1.0);
        probabilities.put('у', 2.0);
        probabilities.put('к', 1.0);
        probabilities.put('д', 3.0);
        probabilities.put('м', 1.0);
        probabilities.put('и', 3.0);
        probabilities.put('т', 2.0);
        probabilities.put('р', 5.0);
        probabilities.put('й', 1.0);
        InfoMetrics.setFrequencyAndCompute(probabilities);

        encodedMessage = InfoMetrics.encodeMessage(text);
        System.out.println("Закодированное сообщение(а): " + encodedMessage);

        decodedMessage = InfoMetrics.decodeMessage(encodedMessage);
        System.out.println("Декодированное сообщение(а): " + decodedMessage);
    }
}