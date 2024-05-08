public class Main {
    public static void main(String[] args) {
        final String message = "00001011101000010000101110";
        String poly = "1001001";  // Порождающий полином G(x) = x^6 + x^3+ 1 в бинарной форме
        int k = 26;  // Длина сообщения
        int r = 6;  // Количество избыточных битов
        int n = k + r;  // Длина кодового слова

        int[][] gMatrix = InfoMetrics.generateMatrix(poly, n, k);
        System.out.println("Порождающая матрица G:");
        for (int[] row : gMatrix) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }

        int[][] canonicalGMatrix = InfoMetrics.toCanonicalForm(gMatrix, n, k);
        System.out.println("\n\nКаноническая порождающая матрица Gk:");

        for (int[] row : canonicalGMatrix) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }

        int[][] checkMatrix = InfoMetrics.generateCheckMatrix(canonicalGMatrix, n, k);

        System.out.println("\n\nПроверочная матрица H:");

        for (int[] row : checkMatrix) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }

        int[][] canonicalCheckMatrix = InfoMetrics.transposeMatrix(checkMatrix);
        System.out.println("\n\nКаноническая проверочная матрица Hk:");

        for (int[] row : canonicalCheckMatrix) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
        System.out.println("Сообщение: " + message);
        String redBits = InfoMetrics.calculateRedundantBits(message, canonicalCheckMatrix);

        System.out.println("\nИзбыточные биты: " + redBits);

        String messageW1Err = InfoMetrics.introduceErrors(message, 1);
        String redBits1Err = InfoMetrics.calculateRedundantBits(messageW1Err, canonicalCheckMatrix);

        System.out.println("\nИзбыточные биты c 1й ошибкой: " + redBits1Err);
        String correctedWord = InfoMetrics.correctError(messageW1Err, redBits, canonicalCheckMatrix);
        System.out.println("Сообщение после исправления ошибки: " + correctedWord);


        String messageW2Err = InfoMetrics.introduceErrors(message, 2);
        String redBits2Err = InfoMetrics.calculateRedundantBits(messageW2Err, canonicalCheckMatrix);
        System.out.println("\nИзбыточные биты c 2й ошибками: " + redBits2Err);

        String correctedWord2 = InfoMetrics.correctError(messageW2Err, redBits, canonicalCheckMatrix);
        System.out.println("Сообщение после исправления ошибки: " + correctedWord2);
    }
}