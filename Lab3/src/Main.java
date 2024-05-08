import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Base64;
public class Main {
    public static void main(String[] args) {
        char[] polishAlphabet = { 'a', 'ą', 'b', 'c', 'ć', 'd', 'e', 'ę', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'ł',
                'm', 'n', 'ń', 'o', 'ó', 'p', 'r', 's', 'ś', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'ź', 'ż' };

        char[] base64Alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();

        String polishText = "";
        String base64Text = "";

        try {
            polishText = new String(Files.readAllBytes(Paths.get("src/polish.txt")));
            base64Text = InfoMetrics.toBase64(polishText);
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Энтропия польского алфавита (Шеннон):");
        double entropyPolish = InfoMetrics.computeEntropy(polishText, polishAlphabet);
        System.out.println("Энтропия base64 (Шеннон):");
        double entropyBase64 = InfoMetrics.computeEntropy(base64Text, base64Alphabet);

        double entropyPolishHartley = InfoMetrics.computeEntropyHartley(polishAlphabet.length);
        System.out.println("Энтропия польского алфавита (Хартли): " + entropyPolishHartley );
        double entropyBase64Hartley = InfoMetrics.computeEntropyHartley(base64Alphabet.length);
        System.out.println("Энтропия base64 (Хартли): " + entropyBase64Hartley);


        double redPolish = InfoMetrics.computeRedundancy(entropyPolish,entropyPolishHartley);
        System.out.println("Избыточность польского алфавита: " + redPolish );
        double redBase = InfoMetrics.computeRedundancy(entropyBase64, entropyBase64Hartley);
        System.out.println("Избыточность base64: " + redBase);

        String a = "Leshuk";
        String b = "Dmitry";

        String a_bin = InfoMetrics.toBinary(a);
        String b_bin = InfoMetrics.toBinary(b);

        String a_base = InfoMetrics.toBase64(a);
        String b_base = InfoMetrics.toBase64(b);

        String xor1_bin = InfoMetrics.XOROperation(a_bin,b_bin);
        String xor2_bin = InfoMetrics.XOROperation(xor1_bin, b_bin);
        System.out.println("a XOR b XOR b (bin):" + xor2_bin);

        String xor1_base = InfoMetrics.XOROperation(a_base, b_base);
        String xor2_base = InfoMetrics.XOROperation(xor1_base,b_base);
        byte[] xorResultBytes = new BigInteger(xor2_base, 2).toByteArray();
        String xorResultBase64 = Base64.getEncoder().encodeToString(xorResultBytes);
        System.out.println("a XOR b XOR b (base64):" + a_base);
    }
}
