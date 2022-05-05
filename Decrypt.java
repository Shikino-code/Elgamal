import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import org.apache.commons.lang.StringUtils;
import java.util.stream.Collectors;

public class Decrypt extends Encrypt {
    static String CipherText, Key, Padding;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Input();
        System.out.println("====Read FileText=====");
        String cipherText = readfiles(CipherText);
        System.out.println("====Read FileKey=====");
        String SkText = readfiles(Key);
        System.out.println("====Read ETCFile=====");
        String PaddingText = readfiles(Padding);
        System.out.println("====sort====");
        String[] etc = sortETC(PaddingText);
        String[] cipherTextSpilt = sort(cipherText);
        String[] Sktext = sort(SkText);
        System.out.println("====Decrypt====");
        long[] result = Decrypts(cipherTextSpilt, Sktext);
        System.out.println("====ConvertToBinary====");
        String[] binary = converDataTypeToString(result, etc);
        System.out.println("====DeletePadding====");
        String[] BinaryFinish = DeletePadding(binary, etc);
        System.out.println("====mixBinary====");
        String Mix = mix(BinaryFinish);
        System.out.println("=====convertToText=====");
        StringToBinary(Mix);

    }

    public static void Input() {
        System.out.println("====CipherText====");
        CipherText = sc.nextLine();
        System.out.println("=====KeyFile=====");
        Key = sc.nextLine();
        System.out.println("=====ETCFile=====");
        Padding = sc.nextLine();
    }

    public static String[] sortETC(String PkText) {
        String[] splited = PkText.split("\\s+");
        System.out.println("spilt: " + Arrays.toString(splited));
        return splited;
    }

    public static String readfiles(String Filename) {
        String charac = "";
        try {
            File myObj = new File(Filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                charac = myReader.nextLine();
                System.out.println(charac);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return charac;
    }

    public static String[] sort(String CypherText) {
        String[] splited = CypherText.split("\\s+");
        System.out.println("sort: " + Arrays.toString(splited));
        return splited;
    }

    public static long power(long x, long y, long n) {
        long res = 1; // Initialize result

        while (y > 0) {
            // If y is odd, multiply x with result
            if ((y & 1) != 0)
                res = (res * x) % n;

            // y must be even now
            y = y >> 1; // y = y/2
            x = x * x % n; // Change x to x^2
        }
        return res;
    }

    public static long[] Decrypts(String[] cypherTextSpilt, String[] SkText) {
        long p = Long.parseLong(SkText[0]);
        long u = Long.parseLong(SkText[2]);
        System.out.println("p: " + p);
        System.out.println("u: " + u);
        long[] result = new long[cypherTextSpilt.length / 2];
        System.out.println("ciphertext: " + Arrays.toString(cypherTextSpilt));
        int r = 1;
        int r2 = 0;
        long power = p - 1 - u;
        for (int i = 0; i < result.length; i++) {
            result[i] = (Long.parseLong(cypherTextSpilt[r]) * power(Long.parseLong(cypherTextSpilt[r2]), power, p)) % p;
            r += 2;
            r2 += 2;

        }
        System.out.println("result: " + Arrays.toString(result));
        return result;
    }

    public static String[] converDataTypeToString(long[] result, String[] etc) {
        int boxsize = Integer.valueOf(etc[1]);
        String[] results = new String[result.length];
        for (int i = 0; i < results.length; i++) {
            results[i] = Long.toBinaryString(result[i]);
            if (results[i].length() != boxsize) {
                results[i] = StringUtils.leftPad("" + results[i], boxsize, "0");
            }
        }
        System.out.println("results: " + Arrays.toString(results));
        return results;
    }

    public static String[] DeletePadding(String[] binary, String[] etc) {
        String r = String.valueOf(binary[binary.length - 1]);
        int extra = Integer.valueOf(etc[0]);
        String[] temp = r.split("");
        int condition = temp.length - extra;
        String[] anotherArray = new String[condition];
        for (int i = 0, k = 0; i < temp.length; i++) {

            if (i == condition) {
                condition++;
                continue;
            }

            anotherArray[k++] = temp[i];
        }
        System.out.println("another: " + Arrays.toString(anotherArray));

        for (int i = 0; i < anotherArray.length; i++) {
            if (i == 0) {
                binary[binary.length - 1] = anotherArray[i];
            } else {
                binary[binary.length - 1] += anotherArray[i];
            }
        }
        System.out.println("binary: " + Arrays.toString(binary));
        return binary;
    }

    public static String mix(String[] binaryFinish) {
        String binary = "";
        String result = "";
        int k = 0;
        for (int i = 0; i < binaryFinish.length; i++) {
            binary += binaryFinish[i];
        }
        String[] temp = binary.split("");
        int condi = ((temp.length / 8) + (temp.length % 8));
        System.out.println("test: " + condi);
        System.out.println(temp.length);
        for (int i = 0; i < condi; i++) {
            for (int j = 0; j < 8; j++) {
                result += temp[k++];
            }
            result += " ";
            System.out.println("mixs: "+result);
        }
        System.out.println("mix: " + result);
        return result;
    }

    public static void StringToBinary(String Mix) {
        String raw = Arrays.stream(Mix.split(" "))
                .map(binary -> Integer.parseInt(binary, 2))
                .map(Character::toString)
                .collect(Collectors.joining()); // cut the space

        System.out.println(raw);
    }

}
