import java.util.Random;

public class InfoMetrics {
    public static int[][] generateMatrix(String poly, int n, int k) {
        int[][] matrix = new int[k][n];
        int poly_length = poly.length();
        for (int i = 0; i < poly_length; i++) {
            matrix[0][i] = Character.getNumericValue(poly.charAt(i));
        }
        for (int i = 1; i < k; i++) {
            matrix[i][0] = 0;
            for (int j = 1; j < poly_length; j++) {
                matrix[i][j] = matrix[i - 1][j - 1];
            }
            for (int j = poly_length; j < n; j++) {
                matrix[i][j] = matrix[i - 1][j - 1];
            }
        }
        return matrix;
    }

    public static int[][] toCanonicalForm(int[][] matrix, int n, int k) {
        for (int i = 0; i < k; i++) {
            for (int j = i + 1; j < k; j++) {
                if (matrix[i][j] == 1) {
                    for (int l = 0; l < n; l++) {
                        matrix[i][l] = (matrix[i][l] + matrix[j][l]) % 2;
                    }
                }
            }
        }
        return matrix;
    }

    public static int[][] generateCheckMatrix(int[][] generatorMatrix, int n, int k) {
        int r = n - k;
        int[][] checkMatrix = new int[n][r];
        for (int i = 0; i < k; i++) {
            System.arraycopy(generatorMatrix[i], k, checkMatrix[i], 0, r);
        }
        for (int i = 0; i < r; i++) {
            checkMatrix[k + i][i] = 1;
        }
        return checkMatrix;
    }

    public static int[][] transposeMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] transposedMatrix = new int[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposedMatrix[j][i] = matrix[i][j];
            }
        }
        return transposedMatrix;
    }

    public static String calculateRedundantBits(String infoWord, int[][] checkMatrix) {
        int r = checkMatrix.length;
        String redundantBits = "";

        for (int i = 0; i < r; i++) {
            int sum = 0;
            for (int j = 0; j < infoWord.length(); j++) {
                sum += Character.getNumericValue(infoWord.charAt(j)) * checkMatrix[i][j];
            }
            redundantBits += sum % 2;
        }
        return redundantBits;
    }

    public static String correctError(String codeWord, String correctRedundantBits, int[][] checkMatrix) {
        int n = checkMatrix[0].length;
        int r = checkMatrix.length;
        String errorRedBits = calculateRedundantBits(codeWord, checkMatrix);
        String errorVector = "";
        for (int i = 0; i < r; i++) {
            errorVector += (Character.getNumericValue(errorRedBits.charAt(i)) +
                    Character.getNumericValue(correctRedundantBits.charAt(i))) % 2;
        }
        for (int i = 0; i < n; i++) {
            String column = "";
            for (int j = 0; j < r; j++) {
                column += checkMatrix[j][i];
            }
            if (column.equals(errorVector)) {
                char[] codeWordArray = codeWord.toCharArray();
                codeWordArray[i] = (codeWordArray[i] == '0') ? '1' : '0';
                codeWord = new String(codeWordArray);
                break;
            }
        }
        return codeWord;
    }

    public static String introduceErrors(String word, int numErrors) {
        Random rand = new Random();
        char[] wordArray = word.toCharArray();
        for (int i = 0; i < numErrors; i++) {
            int errorPos = rand.nextInt(word.length());
            wordArray[errorPos] = (wordArray[errorPos] == '0') ? '1' : '0';
        }
        return new String(wordArray);
    }
}
