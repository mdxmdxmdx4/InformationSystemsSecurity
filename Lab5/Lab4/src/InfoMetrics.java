import java.util.Random;

public class InfoMetrics {
    public static String binary(String text) {
        StringBuilder binaryStr = new StringBuilder();
        for (char c : text.toCharArray()) {
            binaryStr.append(String.format("%8s", Integer.toBinaryString(c)).replaceAll(" ", "0"));
        }
        return binaryStr.toString();
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

        checkMatrix[4][0] = 1;
        checkMatrix[4][1] = 1;
        checkMatrix[4][3] = 1;
        checkMatrix[4][7] = 1;
        checkMatrix[4][15] = 1;
        checkMatrix[3][15] = 1;

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
    public static String introduceErrors(String word, int numErrors) {
        Random rand = new Random();
        char[] wordArray = word.toCharArray();

        for (int i = 0; i < numErrors; i++) {
            int errorPos = rand.nextInt(word.length());
            wordArray[errorPos] = (wordArray[errorPos] == '0') ? '1' : '0';
        }

        return new String(wordArray);
    }
    public static String correctError(String encodedWord_no_errs, String encodedWord_with_errs) {
        String errorVector = "";
        for (int i = 0; i < encodedWord_no_errs.length(); i++) {
            errorVector += encodedWord_no_errs.charAt(i) ^ encodedWord_with_errs.charAt(i);
        }
        System.out.println("Вектор ошибок: " + errorVector);
        String correctedWord = "";
        for (int i = 0; i < encodedWord_with_errs.length(); i++) {
            correctedWord += encodedWord_with_errs.charAt(i) ^ errorVector.charAt(i);
        }
        return correctedWord;
    }
    public static String binaryToText(String binary) {
        StringBuilder text = new StringBuilder();
        int charCode;
        for(int i = 0; i < binary.length(); i += 8) {
            charCode = Integer.parseInt(binary.substring(i, i+8), 2);
            text.append((char) charCode);
        }
        return text.toString();
    }


}