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
    static int dataForEachBlocks;

    public static void main(String args[]) throws IOException {
        Input();
        System.out.println("====Read FileText=====");
        String textFile = readfiles(TextFile);

        System.out.println("====Read FileKey=====");
        String PkText = readfiles(InputKey);
        String[] pgy = spilts(PkText); // PK file

        System.out.println("====Convert FileText to Binary====");
        String textBinary = convertStringtoBinary(textFile);
        System.out.println(textBinary);

        System.out.println("====Add padding in file====");
        String[] Padded = addPadding(textBinary, textFile, pgy);
        long[] PaddedDecimmal = BinaryToDecimal(Padded);

        System.out.println("====Encrypt=====");
        String[] encrypt = Encrypts(PaddedDecimmal, pgy);

        System.out.println("====Export Encrypt====");
        System.out.println("Cipher Text: "+Arrays.toString(encrypt));
        
        System.out.println("====etc file====");
        FileWriter(encrypt,extraZero,dataForEachBlocks);
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
    public static String[] addPadding(String textBinary, String textLength, String[] p) {
        String[] addPaddingArr;
        boolean check = true;

        int sp = Integer.valueOf(p[0]); 
        dataForEachBlocks = (int) (Math.log(sp - 1) / Math.log(2));

       
        System.out.println("Textbinary before add zero: " + textBinary.length() + " bits");
        //Check The last block already Full?
        while (check == true) {
            if (textBinary.length() % dataForEachBlocks != 0) {
                textBinary += "0";
                extraZero++;
            } else
                check = false;
        }

        System.out.println("Add zero padding : " + extraZero + " bits");

        System.out.println("Data for each Block: "+dataForEachBlocks+ " bits");
        System.out.println("Total Blocks: "+(textBinary.length() / dataForEachBlocks)+" blocks");

        System.out.println("Textbinary after add zero: " + textBinary.length() + " bits");
        
        //split textBinry after add 0 
        String[] spiltTextBinary = textBinary.split("");

        //init block size
        String[] result = new String[(textLength.length() * 8) / dataForEachBlocks];

        
        //check block size that fit binaryText after add padding
        if ((textLength.length() * 8) % dataForEachBlocks != 0) {
            addPaddingArr = new String[result.length + 1];
            for (int i = 0; i < result.length; i++) {
                addPaddingArr[i] = result[i];
            }
        } else {
            addPaddingArr = new String[result.length];
            for (int i = 0; i < result.length; i++) {
                addPaddingArr[i] = result[i];  
            }
        }

             
        int k = 0; //point data in arr spiltTextBinary
        //add data in block
        for (int i = 0; i < addPaddingArr.length; i++) {
            for (int j = 0; j < dataForEachBlocks; j++) {
                if (addPaddingArr[i] == null) {
                    addPaddingArr[i] = spiltTextBinary[k++];
                    
                } else {
                    addPaddingArr[i] += spiltTextBinary[k++];
                }
            }
        }

        System.out.println("Data divided into blocks: " + Arrays.toString(addPaddingArr));
        return addPaddingArr;
    }

    //spilt PK file to p,g,y
    public static String[] spilts(String PkText) {
        String[] splited = PkText.split("\\s+");
        System.out.println("Spilt: " + Arrays.toString(splited));
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
    public static String[] Encrypts(long[] PaddedDecimmal, String[] pgk) {
        String[] AB = new String[PaddedDecimmal.length];
        long sp = Long.parseLong(pgk[0]);
        long g = Long.parseLong(pgk[1]);
        long y = Long.parseLong(pgk[2]);
        for (int i = 0; i < PaddedDecimmal.length; i++) {
            boolean check = true;
            long gcd = 0L;
            while (check == true) {
                long k = 1 + (long) (Math.random() * (sp - 1));
                if (gcd != 1L) {
                    gcd = gcdExtended(k, sp - 1);
                } else {
                    long a = power(g, k, sp);
                    long b = power(y, k, sp); // y^k
                    b = (b * PaddedDecimmal[i]) % sp; //b = (y^k) * Plaintext mod sp

                    AB[i] = a + " " + b;
                    check = false;
                }
            }         
        }
        System.out.println("AB[a(0) b(0),a(0) b(0),...,a(arrLong.length),b(arrLong.length)] = " + Arrays.toString(AB));
        return AB;
    }

    public static void FileWriter(String[] encrypt,int extraZero,int dataForEachBlocks)
            throws IOException {
        
        int dataInBlock = dataForEachBlocks;
        File myObj2 = new File("Ciphertext.txt");
        File myObj = new File("etc.txt");

        myObj.createNewFile();
        myObj2.createNewFile();

        System.out.println("Extra Zero: "+extraZero);
        System.out.println("Data For Each Blocks: "+dataForEachBlocks);

        FileWriter fw = new FileWriter("etc.txt", true);
        try (FileWriter fw2 = new FileWriter("Ciphertext.txt", true)) {
            for(int i = 0; i < encrypt.length;i++){        
                fw2.write(encrypt[i]+" ");
            }
        }

        fw.write(extraZero+" ");
        fw.write(new Integer(dataInBlock).toString());
        fw.close();
    }

}
