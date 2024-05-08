import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
public class Main {
    public static void main(String[] args) {
        String givenText = "";
        try {
            givenText = new String(Files.readAllBytes(Paths.get("src/source.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        givenText = InfoMetrics.binary(givenText);
        System.out.println("Исходное сообщение в двоичной форме");
        System.out.println(givenText);

        System.out.println("\n Матрица Хемминга");
        int[][] checkMatrix = InfoMetrics.generateHammingCheckMatrix(givenText);
        for (int i = 0; i < checkMatrix.length; i++) {
            for (int j = 0; j < checkMatrix[i].length; j++) {
                System.out.print(checkMatrix[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Избыточные биты, слово без ошибок:");
        String redBits_no_errs = InfoMetrics.computeRedundantBits(checkMatrix, givenText);
        System.out.println(redBits_no_errs);

        String encodedWord = givenText + redBits_no_errs;
        System.out.println("Закодированное слово: " + encodedWord);


        System.out.println("\nИсходное слово с 1 ошибкой:");
        String givenText_1_err = InfoMetrics.introduceErrors(givenText,1);
        System.out.println(givenText_1_err);
        System.out.println("Избыточные биты с одной ошибкой в слове:");
        String redBits_1_err = InfoMetrics.computeRedundantBits(checkMatrix, givenText_1_err);
        System.out.println(redBits_1_err);

        System.out.println("\nИсходное слово с 2 ошибками:");
        String givenText_2_err = InfoMetrics.introduceErrors(givenText,2);
        System.out.println(givenText_2_err);
        System.out.println("Избыточные биты с двумя ошибками:");
        String redBits_2_err = InfoMetrics.computeRedundantBits(checkMatrix, givenText_2_err);
        System.out.println(redBits_2_err);
        System.out.println();

        String corrected_w_1_err = InfoMetrics.correctError(givenText, givenText_1_err);
        System.out.println("Скорректированный при единичной ошибке");
        System.out.println(corrected_w_1_err + " = " +  InfoMetrics.binaryToText(corrected_w_1_err) );
        System.out.println("Итоговое слово при 1й ошибке: " + (corrected_w_1_err + redBits_1_err));

        String corrected_w_2_errs = InfoMetrics.correctError(givenText,givenText_2_err);
        System.out.println("Скорректированный при двойной ошибке");
        System.out.println(corrected_w_2_errs + " = " +  InfoMetrics.binaryToText(corrected_w_2_errs) );
        System.out.println("Итоговое слово при 2ух ошибках: " + (corrected_w_1_err + redBits_2_err));
    }

}
