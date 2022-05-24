import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner; // Import the Scanner class to read text files

public class Encrypt {
    static String TextFile, InputKey;
    static Scanner sc = new Scanner(System.in);
    static int extraZero = 0;
    static int SPresult = 0;

    public static void main(String[] args) throws IOException {
        Input();
        System.out.println("====Read FileText=====");
        String textFile = readfiles(TextFile);
        System.out.println("====Read FileKey=====");
        String PkText = readfiles(InputKey);
        String[] pgk = sortPK(PkText); // PK file
        System.out.println("====Convert FileText to Binary====");
        String textBinary = convertStringtoBinary(textFile);
        System.out.println(textBinary);
        System.out.println("====Sort Binary in File====");
        String[] Arr = sortBinary(textBinary, textFile, pgk);
        long[] ArrLong = BinaryToDecimal(Arr);
        System.out.println("====Encrypt=====");
        String[] encrypt = Encrypts(ArrLong, pgk);
        System.out.println("====Export Encrypt====");
        System.out.println("encrypt: "+Arrays.toString(encrypt));
        FileWriter(encrypt,extraZero,SPresult);
    }

    //Input
    public static void Input() {
        System.out.println("====TextFile====");
        TextFile = sc.nextLine();
        System.out.println("=====KeyFile=====");
        InputKey = sc.nextLine();
    }

    //Readfile from .txt
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

    //Convert String in file to binary
    public static String convertStringtoBinary(String text) {
        StringBuilder result = new StringBuilder();
        char[] chars = text.toCharArray();
        for (char aChar : chars) {
            result.append(String.format("%8s", Integer.toBinaryString(aChar)).replaceAll(" ", "0"));
        }
        return result.toString();
    }

    public static long[] BinaryToDecimal(String[] Arr) {
        long[] decimal = new long[Arr.length];
        for (int i = 0; i < Arr.length; i++) {
            decimal[i] = Integer.parseInt(Arr[i], 2);
        }

        System.out.println("decimal: " + Arrays.toString(decimal));
        return decimal;
    }
    // sort including textBinary,textLength , PK 
    public static String[] sortBinary(String textBinary, String textLength, String[] p) {
        String[] newArr;
        boolean check = true;
        int sp = Integer.valueOf(p[0]);
        sp -= 1;
        SPresult = (int) (Math.log(sp) / Math.log(2));
        while (check == true) {
            if (textBinary.length() % SPresult != 0) {
                textBinary += "0";
                extraZero++;
            } else
                check = false;
        }
        System.out.println("textBinaryLength: " + textBinary.length());
        System.out.println("extraZero: " + extraZero);

        //split textBinry after add 0 to fix block size
        String[] temp = textBinary.split("");
        System.out.println("templength: " + temp.length);

        String[] result = new String[(textLength.length() * 8) / SPresult];
        // String truetemp[] = checkTemp(temp);
        
        //check how many block should have from text in .txt
        if ((textLength.length() * 8) % SPresult != 0) {
            newArr = new String[result.length + 1];
            for (int i = 0; i < result.length; i++) {
                newArr[i] = result[i];
            }
        } else {
            newArr = new String[result.length];
            for (int i = 0; i < result.length; i++) {
                newArr[i] = result[i];
            }
        }

        System.out.println("newArr: " + newArr.length);
        int k = 0;
        for (int i = 0; i < newArr.length; i++) {
            for (int j = 0; j < SPresult; j++) {
                if (newArr[i] == null) {
                    newArr[i] = temp[k++];
                    
                } else {
                    newArr[i] += temp[k++];
                }

            }
        }
        System.out.println("result: " + Arrays.toString(newArr));
        return newArr;
    }

    //spilt PK file to p,g,y
    public static String[] sortPK(String PkText) {
        String[] splited = PkText.split("\\s+");
        System.out.println("spilt: " + Arrays.toString(splited));
        return splited;
    }

    public static long gcdExtended(long a, long n) {
        long x = 1, y = 1;
        // Base Case
        if (a == 0) {
            x = 0;
            y = 1;
            return n;
        }
        long x1 = 1, y1 = 1; // To store results of recursive call
        long gcd = gcdExtended(n % a, a);

        // Update x and y using results of recursive
        // call
        x = y1 - (n / a) * x1;
        y = x1;
        return gcd;
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
    //encrypts a b 
    public static String[] Encrypts(long[] arrLong, String[] pgk) {
        String[] AB = new String[arrLong.length];
        long p = Long.parseLong(pgk[0]);
        long g = Long.parseLong(pgk[1]);
        long y = Long.parseLong(pgk[2]);
        for (int i = 0; i < arrLong.length; i++) {
            boolean check = true;
            long gcd = 0L;
            while (check == true) {
                long k = 1 + (long) (Math.random() * (p - 1));
                if (gcd != 1L) {
                    gcd = gcdExtended(k, p - 1);
                } else {
                    long a = power(g, k, p);
                    long b = power(y, k, p);
                    b *= arrLong[i];
                    System.out.println("a: " + a);
                    System.out.println("b: " + b);
                    AB[i] = a + " " + b;
                    check = false;
                }
            }
            System.out.println("AB[]: " + Arrays.toString(AB));
        }
        return AB;
    }

    public static void FileWriter(String[] encrypt,int extraZero,int SPresult)
            throws IOException {
        String count = String.valueOf(extraZero);
        String BoxSize = String.valueOf(SPresult);
        File myObj2 = new File("Ciphertext.txt");
        File myObj = new File("etc.txt");
        myObj.createNewFile();
        myObj2.createNewFile();
        System.out.println("count: "+count);
        System.out.println("Boxsize: "+BoxSize);
        FileWriter fw = new FileWriter("etc.txt", true);
        try (FileWriter fw2 = new FileWriter("Ciphertext.txt", true)) {
            for(int i = 0; i < encrypt.length;i++){        
                fw2.write(encrypt[i]+" ");
            }
            fw.write(count+" ");
            fw.write(BoxSize);
        }
        System.out.println("Completed writing into text file");
        fw.close();
    }

}
