import java.util.Random;
public class InfoMetrics {
    public static String binary(String text) {
        StringBuilder binaryStr = new StringBuilder();
        for (char c : text.toCharArray()) {
            binaryStr.append(String.format("%8s", Integer.toBinaryString(c)).replaceAll(" ", "0"));
        }
        return binaryStr.toString();
    }
    public static int[][] createInformationMatrix(String binaryWord) {
        if (binaryWord.length() != 40) {
            throw new IllegalArgumentException("Длина двоичного слова должна быть 40 символов.");
        }
        int[][] infoMatrix = new int[5][8];
        int index = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                if (index < binaryWord.length()) {
                    infoMatrix[i][j] = Character.getNumericValue(binaryWord.charAt(index));
                    index++;
                } else {
                    infoMatrix[i][j] = 0;
                }
            }
        }
        return infoMatrix;
    }
    public static int[][] addParityBits(int[][] infoMatrix) {
        int rows = infoMatrix.length;
        int cols = infoMatrix[0].length;
        int[][] parityMatrix = new int[rows + 1][cols + 1];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(infoMatrix[i], 0, parityMatrix[i], 0, cols);
        }
        for (int i = 0; i < rows; i++) {
            int parity = 0;
            for (int j = 0; j < cols; j++) {
                parity ^= parityMatrix[i][j];
            }
            parityMatrix[i][cols] = parity;
        }
        for (int j = 0; j < cols; j++) {
            int parity = 0;
            for (int i = 0; i < rows; i++) {
                parity ^= parityMatrix[i][j];
            }
            parityMatrix[rows][j] = parity;
        }
        int diagonalParity = 0;
        for (int i = 0; i < rows; i++) {
            diagonalParity ^= parityMatrix[i][cols];
        }
        for (int j = 0; j < cols; j++) {
            diagonalParity ^= parityMatrix[rows][j];
        }
        parityMatrix[rows][cols] = diagonalParity;

        return parityMatrix;
    }

    public static String matrixToCodeword(int[][] parityMatrix) {
        StringBuilder codeword = new StringBuilder();
        for (int i = 0; i < parityMatrix.length; i++) {
            for (int j = 0; j < parityMatrix[i].length; j++) {
                codeword.append(parityMatrix[i][j]);
            }
        }
        return codeword.toString();
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
    public static String binaryToText(String binary) {
        StringBuilder text = new StringBuilder();
        int charCode;
        for(int i = 0; i < binary.length(); i += 8) {
            charCode = Integer.parseInt(binary.substring(i, i+8), 2);
            text.append((char) charCode);
        }
        return text.toString();
    }
    public static String correctErrors(int[][] parityMatrix_no_errs, String encodedWord_with_errs) {
        int[][] infoMatrix_with_errs = InfoMetrics.createInformationMatrix(encodedWord_with_errs);
        int[][] parityMatrix_with_errs = InfoMetrics.addParityBits(infoMatrix_with_errs);
        StringBuilder errorVector = new StringBuilder();
        for (int i = 0; i < parityMatrix_with_errs.length; i++) {
            for (int j = 0; j < parityMatrix_with_errs[i].length; j++) {
                errorVector.append(parityMatrix_no_errs[i][j] ^ parityMatrix_with_errs[i][j]);
            }
        }
        System.out.println("Вектор ошибок: " + errorVector);
        StringBuilder correctedWord = new StringBuilder();
        for (int i = 0; i < encodedWord_with_errs.length(); i++) {
            correctedWord.append(encodedWord_with_errs.charAt(i) ^ errorVector.charAt(i));
        }
        return correctedWord.toString();
    }

    public static int[][][] createThreeDimensionalMatrix(String binaryWord) {
        if (binaryWord.length() != 40) {
            throw new IllegalArgumentException("Длина двоичного слова должна быть 40 символов.");
        }
        int[][][] matrix = new int[2][10][2];
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 2; k++) {
                    if (index < binaryWord.length()) {
                        matrix[i][j][k] = Character.getNumericValue(binaryWord.charAt(index));
                        index++;
                    } else {
                        matrix[i][j][k] = 0;
                    }
                }
            }
        }
        return matrix;
    }
    public static int[][][] addParityBits3Dim(int[][][] matrix) {
        int[][][] parityMatrix = new int[2][11][3];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 10; j++) {
                System.arraycopy(matrix[i][j], 0, parityMatrix[i][j], 0, 2);
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 10; j++) {
                int parity = 0;
                for (int k = 0; k < 2; k++) {
                    parity ^= parityMatrix[i][j][k];
                }
                parityMatrix[i][j][2] = parity;
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int k = 0; k < 2; k++) {
                int parity = 0;
                for (int j = 0; j < 10; j++) {
                    parity ^= parityMatrix[i][j][k];
                }
                parityMatrix[i][10][k] = parity;
            }
        }
        for (int i = 0; i < 2; i++) {
            int diagonalParity = 0;
            for (int j = 0; j < 10; j++) {
                diagonalParity ^= parityMatrix[i][j][j % 2];
            }
            parityMatrix[i][10][2] ^= diagonalParity;
        }
        for (int j = 0; j < 10; j++) {
            for (int k = 0; k < 2; k++) {
                int zParity = parityMatrix[0][j][k] ^ parityMatrix[1][j][k];
                parityMatrix[0][10][k] ^= zParity;
                parityMatrix[1][10][k] ^= zParity;
            }
        }
        return parityMatrix;
    }

    public static void printThreeDimensionalMatrix(int[][][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            System.out.println("Слой " + (i + 1) + ":");
            for (int j = 0; j < matrix[i].length; j++) {
                for (int k = 0; k < matrix[i][j].length; k++) {
                    System.out.print(matrix[i][j][k] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public static String matrixToCodeword3Dim(int[][][] parityMatrix) {
        StringBuilder codeword = new StringBuilder();
        for (int i = 0; i < parityMatrix.length; i++) {
            for (int j = 0; j < parityMatrix[i].length; j++) {
                for (int k = 0; k < parityMatrix[i][j].length; k++) {
                    codeword.append(parityMatrix[i][j][k]);
                }
            }
        }
        return codeword.toString();
    }
    
    public static String correctErrors3D(int[][][] parityMatrixNoErrs, String encodedWordWithErrs) {
        int[][][] infoMatrixWithErrs = createThreeDimensionalMatrix(encodedWordWithErrs);
        int[][][] parityMatrixWithErrs = addParityBits3Dim(infoMatrixWithErrs);

        StringBuilder errorVector = new StringBuilder();
        for (int i = 0; i < parityMatrixWithErrs.length; i++) {
            for (int j = 0; j < parityMatrixWithErrs[i].length; j++) {
                for (int k = 0; k < parityMatrixWithErrs[i][j].length; k++) {
                    errorVector.append(parityMatrixNoErrs[i][j][k] ^ parityMatrixWithErrs[i][j][k]);
                }
            }
        }
        System.out.println("Вектор ошибок: " + errorVector);

        StringBuilder correctedWord = new StringBuilder();
        for (int i = 0; i < encodedWordWithErrs.length(); i++) {
            correctedWord.append(encodedWordWithErrs.charAt(i) ^ errorVector.charAt(i));
        }
        return correctedWord.toString();
    }

}