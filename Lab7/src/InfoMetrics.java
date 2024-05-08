import java.util.Random;
public class InfoMetrics {
    public static String generateSequence() {
        StringBuilder sequence = new StringBuilder();
        Random random = new Random(12345);
        for (int i = 0; i < 112; i++) {
            sequence.append(random.nextInt(2));
        }
        return sequence.toString();
    }

    public static String encodeMessage(String message, int k) {
        StringBuilder encodedMessage = new StringBuilder();
        for (int i = 0; i < message.length(); i += k) {
            String word = message.substring(i, i + k);
            int[][] checkMatrix = generateHammingCheckMatrix(word);
            String redundantBits = computeRedundantBits(checkMatrix, word);
            encodedMessage.append(word).append(redundantBits);
        }
        return encodedMessage.toString();
    }

    public static char[][] createInterleaverMatrix(String message, int columns) {
        int rows = (int) Math.ceil((double) message.length() / columns);
        char[][] interleaverMatrix = new char[rows][columns];
        for (int i = 0; i < message.length(); i++) {
            interleaverMatrix[i / columns][i % columns] = message.charAt(i);
        }
        return interleaverMatrix;
    }

    public static String matrixToString(char[][] matrix) {
        StringBuilder sequence = new StringBuilder();
        for (int j = 0; j < matrix[0].length; j++) {
            for (char[] row : matrix) {
                sequence.append(row[j]);
            }
        }
        return sequence.toString();
    }

    public static String matrixToStringAlternate(char[][] matrix) {
        StringBuilder sequence = new StringBuilder();
        for (char[] row : matrix) {
            for (char element : row) {
                sequence.append(element);
            }
        }
        return sequence.toString();
    }

    public static String generateErrors(String sequence, int errorCount) {
        Random random = new Random();
        int errorPosition = random.nextInt(sequence.length() - errorCount + 1);
        char[] sequenceArray = sequence.toCharArray();
        for (int i = errorPosition; i < errorPosition + errorCount; i++) {
            sequenceArray[i] = sequenceArray[i] == '0' ? '1' : '0';
        }
        return new String(sequenceArray);
    }

    public static void printMatrix(char[][] matrix) {
        for (char[] row : matrix) {
            for (char element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }

    public static char[][] createDeinterleaverMatrix(String message, int columns) {
        int rows = (int) Math.ceil((double) message.length() / columns);
        char[][] deinterleaverMatrix = new char[rows][columns];
        for (int i = 0; i < message.length(); i++) {
            deinterleaverMatrix[i % rows][i / rows] = message.charAt(i);
        }
        return deinterleaverMatrix;
    }

    public static String correctMessage(String message, int wordLength) {
        StringBuilder correctedMessage = new StringBuilder();
        for (int i = 0; i < message.length(); i += wordLength) {
            String word = message.substring(i, i + wordLength);
            int[][] checkMatrix = generateHammingCheckMatrix(word);
            String redundantBits = computeRedundantBits(checkMatrix, word);
            String correctedWord = word.substring(0, 4);  // Исправляем ошибку
            correctedMessage.append(correctedWord);
        }
        return correctedMessage.toString();
    }

    public static int[][] generateHammingCheckMatrix(String infoWord) {
        int k = infoWord.length();
        int r = 0;
        while (Math.pow(2, r) < k + r + 1) {
            r++;
        }
        int n = k + r;
        int[][] checkMatrix = new int[r][n];

        int count = 1;
        for (int i = 0; i < k; i++) {
            String binary = Integer.toBinaryString(count++);
            while (binary.length() < r) {
                binary = "0" + binary;
            }
            for (int j = 0; j < r; j++) {
                checkMatrix[j][i] = binary.charAt(r - j - 1) - '0';
            }
        }
        for (int i = k; i < n; i++) {
            checkMatrix[i - k][i] = 1;
        }
        return checkMatrix;
    }


    public static String computeRedundantBits(int[][] checkMatrix, String infoWord) {
        StringBuilder redundantBits = new StringBuilder();

        for (int i = 0; i < checkMatrix.length; i++) {
            int sum = 0;
            for (int j = 0; j < infoWord.length(); j++) {
                sum += checkMatrix[i][j] * Character.getNumericValue(infoWord.charAt(j));
            }
            redundantBits.append(sum % 2);
        }

        return redundantBits.toString();
    }
}