import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
public class Main {
    public static void main(String[] args) {
        char[] polishAlphabet = { 'a', 'ą', 'b', 'c', 'ć', 'd', 'e', 'ę', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'ł',
                'm', 'n', 'ń', 'o', 'ó', 'p', 'r', 's', 'ś', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'ź', 'ż' };
        char[] belarusianAlphabet = { 'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'і', 'й', 'к', 'л', 'м', 'н',
                'о', 'п', 'р', 'с', 'т', 'у', 'ў', 'ф', 'х', 'ц', 'ч', 'ш', 'ы', 'ь', 'э', 'ю', 'я' };
        char[] binaryAlphabet = { '1', '0' };

        String name = "Leshuk Dmitry Ihorevich";

        String polishText = "";
        String belarusianText = "";

        try {
            polishText = new String(Files.readAllBytes(Paths.get("src/polish.txt")));
            belarusianText = new String(Files.readAllBytes(Paths.get("src/bel.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Энтропия польского алфавита:");
        double entropyPolish = InfoMetrics.computeEntropy(polishText, polishAlphabet);

        System.out.println("\nЭнтропия белорусского алфавита:");
        double entropyBelarusian = InfoMetrics.computeEntropy(belarusianText, belarusianAlphabet);

        System.out.println("\nЭнтропия бинарного алфавита (польский текст):");
        double entropyBinary = InfoMetrics.computeEntropy(InfoMetrics.binary(polishText), binaryAlphabet);

        System.out.println("\nЭнтропия бинарного алфавита (белорусский текст):");
        double entropyBinary1 = InfoMetrics.computeEntropy(InfoMetrics.binary(belarusianText), binaryAlphabet);

        System.out.println("\nКол-во информации в сообщении (ФИО):");
        System.out.println("На основе польского алфавита: " + InfoMetrics.countInformation(name, entropyPolish));
        System.out.println("На основе белорусского алфавита: " + InfoMetrics.countInformation(name, entropyBelarusian));
        System.out.println("В кодах ASCII: " + InfoMetrics.countInformation(InfoMetrics.binary(name), entropyBinary));

        double error0_1 = InfoMetrics.withError(0.1);
        double error0_5 = InfoMetrics.withError(0.5);
        double error1_0 = InfoMetrics.withError(0.99999);

        System.out.println("\nЭффективная энтропия бинарного алфавита при вероятности ошибочной передачи единичного бита сообщения:");
        String binaryName = InfoMetrics.binary(name);
        System.out.println("0.1: " + error0_1 + "\nКоличество информации: " + InfoMetrics.countInformation(binaryName, 1 - error0_1 ));
        System.out.println("0.5: " + error0_5 + "\nКоличество информации: " + InfoMetrics.countInformation(binaryName, 1 - error0_5 ));
        System.out.println("~1.0: " + error1_0 + "\nКоличество информации: " + InfoMetrics.countInformation(binaryName, 1 - error1_0 ));

        System.out.println("\nЭффективная энтропия польского алфавита при вероятности ошибочной передачи единичного бита сообщения:");
        System.out.println("0.1: " + error0_1 + "\nКоличество информации: " + InfoMetrics.countInformation(name, entropyPolish - error0_1 ));
        System.out.println("0.5: " + error0_5 + "\nКоличество информации: " + InfoMetrics.countInformation(name, entropyPolish - error0_5 ));
        System.out.println("~1.0: " + error1_0 + "\nКоличество информации: " + InfoMetrics.countInformation(name, entropyPolish - error1_0 ));

        System.out.println("\nЭффективная энтропия белорусского алфавита при вероятности ошибочной передачи единичного бита сообщения:");
        System.out.println("0.1: " + error0_1 + "\nКоличество информации: " + InfoMetrics.countInformation(name, entropyBelarusian - error0_1 ));
        System.out.println("0.5: " + error0_5 + "\nКоличество информации: " + InfoMetrics.countInformation(name, entropyBelarusian - error0_5 ));
        System.out.println("~1.0: " + error1_0 + "\nКоличество информации: " + InfoMetrics.countInformation(name, entropyBelarusian - error1_0 ));
    }
}
