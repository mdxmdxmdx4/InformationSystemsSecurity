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
        System.out.println("Исходное сообщение в двоичной форме (Xk)");
        System.out.println(givenText);

        int[][] dDimentionalMatrix = InfoMetrics.createInformationMatrix(givenText);
        System.out.println("\nДвумерная матрица (k1,k2)");
        for (int i = 0; i < dDimentionalMatrix.length; i++) {
            for (int j = 0; j < dDimentionalMatrix[i].length; j++) {
                System.out.print(dDimentionalMatrix[i][j] + " ");
            }
            System.out.println();
        }

        int[][] matrixWParitets = InfoMetrics.addParityBits(dDimentionalMatrix);
        System.out.println("\nМатрица с паритетами");
        for (int i = 0; i < matrixWParitets.length; i++) {
            for (int j = 0; j < matrixWParitets[i].length; j++) {
                System.out.print(matrixWParitets[i][j] + " ");
            }
            System.out.println();
        }
        String codeWordXn = InfoMetrics.matrixToCodeword(matrixWParitets);
        System.out.println("\nСобщение Xn: " + codeWordXn );

        String codeWordYn = InfoMetrics.introduceErrors(givenText,3);
        System.out.println("\nYn (исходное слово с 3 ошибками):\n" + codeWordYn);

        String corrWYnStr = InfoMetrics.correctErrors(matrixWParitets, codeWordYn);
        System.out.println("Yn': " + corrWYnStr);

        System.out.println("\n\nРабота с трёхмерной матрицей (2,10,2):");

        int[][][] threeDimMatrix = InfoMetrics.createThreeDimensionalMatrix(givenText);
         InfoMetrics.printThreeDimensionalMatrix(threeDimMatrix);

         int[][][] threeDimParitets = InfoMetrics.addParityBits3Dim(threeDimMatrix);
        System.out.println("3-ёхмерная матрица паритетов (разбита на слои)");
        InfoMetrics.printThreeDimensionalMatrix(threeDimParitets);
        String codewordEncoded3Dim = InfoMetrics.matrixToCodeword3Dim(threeDimParitets);
        System.out.println("\nКодовое слово " + codewordEncoded3Dim);

        System.out.println("\nYn (исходное слово с 3 ошибками):\n" + codeWordYn);
        String resultWord = InfoMetrics.correctErrors3D(threeDimParitets, InfoMetrics.introduceErrors(givenText,2));
        System.out.println("Исправленное слово: " + resultWord);

    }

}
