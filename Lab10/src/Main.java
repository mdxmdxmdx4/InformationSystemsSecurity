import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String testString = "0101010101010101010101010101010101010101010101010101010101010101";
        List<int[]> compressed = LZ77.compress(testString);
        System.out.println("После сжатия: ");
        for (int[] array : compressed) {
            System.out.print(Arrays.toString(array));
        }
        System.out.println();
        String decompressed = LZ77.decompress(compressed);
        System.out.println("После распаковки: " + decompressed);
        System.out.println(decompressed != testString);
    }
}