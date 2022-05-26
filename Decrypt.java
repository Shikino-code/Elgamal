import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import org.apache.commons.lang.StringUtils;
import java.util.stream.Collectors;

public class Decrypt extends Encrypt {
    static String CipherText, Key, Padding;
    static Scanner sc = new Scanner(System.in);

    public static void main() {
        Input();

        System.out.println("====Read FileText=====");
        String cipherText = readfiles(CipherText);

        System.out.println("====Read FileKey=====");
        String SkText = readfiles(Key);

        System.out.println("====Read ETCFile=====");
        String PaddingText = readfiles(Padding);

        System.out.println("====sort====");
        System.out.println("---etc---");
        String[] etc = splits(PaddingText); 

        System.out.println("---Cipher Text Spilt After split---");
        String[] cipherTextSpilt = splits(cipherText);

        System.out.println("--- SK File---");
        String[] Sktext = splits(SkText);

        System.out.println("====Decrypt====");
        long[] result = Decrypts(cipherTextSpilt, Sktext);

        System.out.println("====Convert to Binary====");
        String[] binary = converDataTypeToString(result, etc);

        System.out.println("====Delete Padding====");
        String[] BinaryFinish = DeletePadding(binary, etc);

        System.out.println("====Resemble Binart Text====");
        String reform = reformBinary(BinaryFinish);

        System.out.println("=====Convert to Text=====");
        StringToBinary(reform);

    }

    public static void Input() {
        System.out.println("====CipherText====");
        CipherText = sc.nextLine();

        System.out.println("=====KeyFile=====");
        Key = sc.nextLine();

        System.out.println("=====ETCFile=====");
        Padding = sc.nextLine();
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

    //spilt a b
    public static String[] splits(String CypherText) {
        String[] splited = CypherText.split("\\s+");
        System.out.println("spilt: " + Arrays.toString(splited));
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

    //decrypt with p,u,cyphertext
    public static long[] Decrypts(String[] CipherTextSplits, String[] SkText) {
        long p = Long.parseLong(SkText[0]);
        long u = Long.parseLong(SkText[2]);

        System.out.println("p: " + p);
        System.out.println("u: " + u);

        long[] result = new long[CipherTextSplits.length / 2];
        System.out.println("Ciphertext: " + Arrays.toString(CipherTextSplits));

        int r = 1;
        int r2 = 0;
        long power = p - 1 - u;
        
        for (int i = 0; i < result.length; i++) {

            result[i] = (Long.parseLong(CipherTextSplits[r]) * power(Long.parseLong(CipherTextSplits[r2]), power, p)) % p;
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

    //delete 0 that we add in encrypt
    public static String[] DeletePadding(String[] binary, String[] etc) {
        String lastBlock = String.valueOf(binary[binary.length - 1]);
        int extra = Integer.valueOf(etc[0]);

        String[] splitDataInBlock = lastBlock.split("");
        int condition = splitDataInBlock.length - extra;
        String[] deletePaddingArr = new String[condition];

        for (int i = 0, k = 0; i < splitDataInBlock.length; i++) {

            if (i == condition) {
                condition++;
                continue;
            }
            deletePaddingArr[k++] = splitDataInBlock[i];
        }
        System.out.println("Delete Padding: " + Arrays.toString(deletePaddingArr));

        //Add data after delete padding to last block
        for (int i = 0; i < deletePaddingArr.length; i++) {
            if (i == 0) {
                binary[binary.length - 1] = deletePaddingArr[i];
            } else {
                binary[binary.length - 1] += deletePaddingArr[i];
            }
        }
        System.out.println("Binary Text: " + Arrays.toString(binary));
        return binary;
    }

    // ressemble to binary
    public static String reformBinary(String[] binaryText) {
        String binary = "";
        String result = "";

        int k = 0;

        for (int i = 0; i < binaryText.length; i++) {
            binary += binaryText[i];
        }
        
        String[] temp = binary.split("");
        int condi = ((temp.length / 8) + (temp.length % 8));
        
        for (int i = 0; i < condi; i++) {
            for (int j = 0; j < 8; j++) {
                result += temp[k++];
            }
            result += " ";
            //System.out.println("reform binary text: "+result);
        }
        System.out.println("Result ressemble: " + result);
        return result;
    }

    public static void StringToBinary(String reform) {
        String plainText = Arrays.stream(reform.split(" "))
                .map(binary -> Integer.parseInt(binary, 2))
                .map(Character::toString)
                .collect(Collectors.joining()); // cut the space

        System.out.println("Messaage = "+plainText);
    }

}
