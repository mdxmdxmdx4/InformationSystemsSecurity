import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String input = "110001001100111011100001";

        long startTime = System.nanoTime();
        String[] bwtResult = InfoMetrics.bwt(input);
        long endTime = System.nanoTime();
        System.out.println("Время выполнения прямого преобразования: " + (endTime - startTime) / 1000000 + " миллисекунд");

        System.out.println("Результат BWT: " + Arrays.toString(bwtResult));

        startTime = System.nanoTime();
        String invBwtResult = InfoMetrics.invBwt(bwtResult[0], Integer.parseInt(bwtResult[1]));
        endTime = System.nanoTime();
        System.out.println("Время выполнения обратного преобразования: " + (endTime - startTime) / 1000000 + " миллисекунд");

        System.out.println("Результат обратного преобразования: " + invBwtResult);
    }
}