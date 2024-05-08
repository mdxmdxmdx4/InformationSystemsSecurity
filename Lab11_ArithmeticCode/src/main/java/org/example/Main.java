package org.example;

public class Main {
    public static void main(String[] args) {
        String word = "достопримечательность";
        calculateInitialRanges(word);
        double encodedValue = performArithmeticEncoding(word);
        System.out.println("Закодированное значение: " + encodedValue);
        String decodedWord = performArithmeticDecoding(encodedValue, word.length());
        System.out.println("Декодированное слово: " + decodedWord);
    }
}