public class Main {
    public static void main(String[] args) {
        // Генерация входного сообщения
        String sequence = InfoMetrics.generateSequence();
        System.out.println("Входное сообщение: \n" + sequence);

        // Кодирование сообщения
        String encodedMessage = InfoMetrics.encodeMessage(sequence, 4);
        System.out.println("Закодированное сообщение: \n" + encodedMessage);

        // Создание матрицы перемежения
        char[][] interleaverMatrix = InfoMetrics.createInterleaverMatrix(encodedMessage, 7);

        // Вывод матрицы перемежения
        InfoMetrics.printMatrix(interleaverMatrix);

        // Преобразование матрицы перемежения обратно в строку
        String interleavedSequence = InfoMetrics.matrixToString(interleaverMatrix);
        System.out.println("Последовательность после перемежения: \n" + interleavedSequence);

        // Генерация группы ошибок в строке
        String sequenceWithErrors = InfoMetrics.generateErrors(interleavedSequence, 8);
        System.out.println("Последовательность с ошибками: \n" + sequenceWithErrors);

        // Создание матрицы деперемежения
        char[][] deinterleaverMatrix = InfoMetrics.createDeinterleaverMatrix(sequenceWithErrors, 7);

        // Вывод матрицы деперемежения
        InfoMetrics.printMatrix(deinterleaverMatrix);

        // Преобразование матрицы деперемежения обратно в строку
        String deinterleavedSequence = InfoMetrics.matrixToStringAlternate(deinterleaverMatrix);
        System.out.println("Последовательность после деперемежения: \n" + deinterleavedSequence);

        // Исправление ошибок в сообщении
        String correctedMessage = InfoMetrics.correctMessage(deinterleavedSequence, 7);
        System.out.println("Исправленное сообщение: \n" + correctedMessage);
        if (sequence.equals(correctedMessage)) {
            System.out.println("Исходное сообщение и исправленное сообщение полностью совпадают.");
        } else {
            System.out.println("Исходное сообщение и исправленное сообщение не совпадают.");
        }
    }
}