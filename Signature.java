import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Signature {
    static String TextFile, TextFile2, TextFile3;
    static Scanner sc = new Scanner(System.in);
    static int extraZero = 0;
    static int SPresult = 0;

    public static void main(String[] args) {
        System.out.println("====HashTextFile====");
        TextFile = sc.nextLine();

        System.out.println("====SKTextFile====");
        TextFile2 = sc.nextLine();

        System.out.println("====PKTextFile====");
        TextFile3 = sc.nextLine();

        System.out.println("====Read FileText=====");
        String textFile = readfiles(TextFile);

        System.out.println("====Read FileKey=====");
        String textFile2 = readfiles(TextFile2);

        System.out.println("====Read FileKey=====");
        String textFile3 = readfiles(TextFile3);
        
        // String[] arrOfStr = textFile.split("", 8);
        String textBinary = convertStringtoBinary(textFile);
        System.out.println("textBinary = " + textBinary);
        String[] pgk = sortPK(textFile3);
        String[] Arr = sortBinary(textBinary, textFile, pgk);
        long[] ArrLong = BinaryToDecimal(Arr);
        String[] arrOfStr2 = textFile2.split(" ", 0);
        String[] Sign = SignVerify(ArrLong, pgk, arrOfStr2);
        //
        // String[] arrOfStr2 = textFile2.split(" ", 0);
        // String S_p_text = arrOfStr2[0];
        // String S_g_text = arrOfStr2[1];
        // String S_y_text = arrOfStr2[2];
        // String S_x_text = arrOfStr3[2];
        // System.out.println("Hash = " + Arrays.toString(arrOfStr));
        // System.out.println("p = " + S_p_text);
        // System.out.println("g = " + S_g_text);
        // System.out.println("y = " + S_y_text);
        // System.out.println("x = " + S_x_text);
        // Sign
        // long p = 11;
        // long g = 2;
        // long k = 7;
        // long x = 3;
        // long y = 8;
        // long X = 9;
        // // long k = 1 + (long) (Math.random() * (p - 1));
        // System.out.println("p = " + p);
        // System.out.println("k = " + k);
        // // long k2 = 1 + (long) (Math.random() * (p - 1));
        // // System.out.println("k2 = " + k2);
        // long gcd = gcdExtended(k, p - 1);
        // System.out.println("gcd = " + gcd);
        // long i = inverse(k, p - 1);
        // // long i2 = inverse(p - 1, k);
        // System.out.println("inverse(k , p-1); = " + i);
        // // System.out.println("inverse(p-1 , k); = " + i2);
        // long r = FastExponential(g, k, p);
        // System.out.println("r = " + r);
        // long s = i * (X - (x * r)) % (p - 1);
        // System.out.println("s = " + s);
        // long sum = 0;
        // if (s < 0) {
        // // long sum = ((s *-1) % (p-1)) +(p-1);
        // sum = (s % (p - 1)) + (p - 1);
        // System.out.println("sum = " + sum);
        // }
        // // (X,r,s)
        // System.out.println("(X,r,s) = " + X + r + sum);
        // long gx = FastExponential(g, X, p);
        // // verrify
        // System.out.println("gx = " + gx);
        // long yr = (FastExponential(y, r, p));
        // long rs = (FastExponential(r, sum, p));
        // long yr_rs = ((FastExponential(y, r, p)) * (FastExponential(r, sum, p)) % p);
        // System.out.println("yr = " + yr);
        // System.out.println("rs = " + rs);
        // System.out.println("yr_rs = " + yr_rs);
        // if (gx == yr_rs) {
        // System.out.println("Comparison is valid");
        // } else {
        // System.out.println("Comparison is not valid");
        // }
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

    public static long FastExponential(long x, long y, long n) {
        // x =number y =^number n =mod
        long res = 1; // Initialize result

        while (y > 0) {
            // If y is odd, multiply x with result
            if ((y & 1) != 0)
                res = (res * x) % n;

            // y must be even now
            y = y >> 1; // y = y/2
            x = x * x % n; // Change x to x^2
        }
        // System.out.println("res = " + res);
        return res;
    }

    public static long gcdExtended(long a, long n) {
        //
        // a = number first
        // n = mod second
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

    public static long inverse(long n, long a) {
        // n = number first
        // a = mod second
        long inv = 0;
        long r1 = a;
        long r2 = n;
        long t1 = 0;
        long t2 = 1;
        while (r2 > 0) {
            long q = r1 / r2;
            long r = r1 - q * r2;
            r1 = r2;
            r2 = r;
            long t = t1 - q * t2;
            t1 = t2;
            t2 = t;
        }
        if (r1 == 1) {
            // b^-1 = answer.
            inv = t1;
        }
        System.out.println("inv = " + inv);
        return inv;
    }

    public static String[] SignVerify(long[] arrLong, String[] pgk, String[] arrOfStr2) {
        //long X = arrLong[0];
        long p = Long.parseLong(pgk[0]);
        long g = Long.parseLong(pgk[1]);
        long y = Long.parseLong(pgk[2]);
        long x = Long.parseLong(arrOfStr2[2]);
        int count;
        //// long k = 1 + (long) (Math.random() * (p - 1));
        for (int j = 0; j < arrLong.length; j++) {
            long X = arrLong[j];
            boolean check = true;
            long gcd = 0;
            while (check == true) {
                long k = 1 + (long) (Math.random() * (p - 1));
                if (gcd != 1) {
                    gcd = gcdExtended(k, p - 1);
                } else {
                    // gcd = gcdExtended(k, p - 1);
                    System.out.println("gcd = " + gcd);
                    System.out.println("k = " +k);
                    System.out.println("p = " +(p - 1));
                    long i = inverse(k, p - 1);
                    while (i <= 0) {
                        k = 1 + (long) (Math.random() * (p - 1));
                        i = inverse(k, p - 1);
                        // System.out.println("k i<=0");
                    }
                    System.out.println("inverse(k , p-1); = " + i);
                    long r = FastExponential(g, k, p);
                    System.out.println("r = " + r);
                    long s = i * (X - (x * r)) % (p - 1);
                    long sum = 0;
                    if (s < 0) {

                        sum = (s % (p - 1)) + (p - 1);
                    } else {
                        sum = ((s * -1) % (p - 1)) + (p - 1);
                    }
                    // (X,r,s)
                    System.out.println("(X,r,s) = " + "(" + X + "," + r + "," + sum + ")");
                    long gx = FastExponential(g, X, p);
                    System.out.println("g^x = " + gx);
                    long yr = (FastExponential(y, r, p));
                    long rs = (FastExponential(r, sum, p));
                    System.out.println("yr = " + yr);
                    System.out.println("rs = " + rs);
                    long yr_rs = (yr) * (rs) % p;
                    System.out.println("yr_rs = " + yr_rs);
                    if (gx == yr_rs) {
                        System.out.println("Comparison is valid");
                        count = j;
                        System.out.println("count = "+count);
                        count++;
                        if (count == arrLong.length) {
                            System.out.println("All Comparison is valid");
                        }
                    } else {
                        System.out.println("Comparison is not valid");
                    }

                    check = false;
                }
            }
        }
        return pgk;
    }


    public static String convertStringtoBinary(String text) {
        StringBuilder result = new StringBuilder();
        char[] chars = text.toCharArray();
        for (char aChar : chars) {
            result.append(String.format("%8s", Integer.toBinaryString(aChar)).replaceAll(" ", "0"));
        }
        return result.toString();
    }

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
        String[] temp = textBinary.split("");

        System.out.println("templength: " + temp.length);

        String[] result = new String[(textLength.length() * 8) / SPresult];
        // String truetemp[] = checkTemp(temp);

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

    public static String[] sortPK(String PkText) {
        String[] splited = PkText.split("\\s+");
        System.out.println("spilt: " + Arrays.toString(splited));
        return splited;
    }

    public static long[] BinaryToDecimal(String[] Arr) {
        long[] decimal = new long[Arr.length];
        for (int i = 0; i < Arr.length; i++) {
            decimal[i] = Integer.parseInt(Arr[i], 2);
        }

        System.out.println("decimal: " + Arrays.toString(decimal));
        return decimal;
    }
}
