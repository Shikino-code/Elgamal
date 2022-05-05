import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors

import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileWriter; //import the FileWriter class to write text files
import java.io.IOException; // Import the IOException class


public class readFile {
  static Scanner sc = new Scanner(System.in);
  static String Filename;
  static long bits;
  static long SafePrime = 8L;

  public static void main(String[] args) throws IOException {
    System.out.println("====Input====");
    Input();
    System.out.println("====ReadFile====");
    String text = readfiles(Filename);
    System.out.println("====convert====");
    String num = convertStringtoBinary(text);
    System.out.println(num);
    String s = conditionInput(num);
    System.out.println("====Correct====");
    System.out.println(s);
    System.out.println("====cutZero====");
    String resultBinary = customizeBinary(s, bits);
    System.out.println("====BinaryToDecinal====");
    long decimal = BinaryToDecimal(resultBinary); // ตัวแปรเก็บเลขฐาน 10
    SafePrime = CheckPrime(decimal);
    long Generator = FineKeyGen(SafePrime);
    CreateKey(Generator, SafePrime);;
  }

  public static void Input() {
    System.out.println("input Filename");
    Filename = sc.nextLine();
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

  public static long CheckPrime(long decimal) {
    long n = decimal;
    for (int i = 0; i < n; i++) {
      if (prime(n)) {
        System.out.println(n + " " + "is prime");
        if (safePrime(n)) {
          System.out.println(n + " " + "is Safe prime");
          break;
        } else {
          System.out.println("Not safe prime");
          n += 1;
        }
      } else {
        n += 1;
      }
    }
    return n;
  }

  public static String convertStringtoBinary(String text) {
    StringBuilder result = new StringBuilder();
    char[] chars = text.toCharArray();
    for (char aChar : chars) {
      result.append(String.format("%8s", Integer.toBinaryString(aChar)).replaceAll(" ", "0"));
    }
    return result.toString();
  }

  public static String conditionInput(String num) {
    boolean check = true;
    System.out.println("====Check====");

    System.out.println("input bit less than " + (long) (Math.log(Long.MAX_VALUE) / Math.log(2) / 2));
    bits = sc.nextInt();
    if (bits < (long) (Math.log(Long.MAX_VALUE) / Math.log(2) / 2)) {

    } else {
      System.out.println("more " + (long) (Math.log(Long.MAX_VALUE) / Math.log(2) / 2));
      System.out.println("exit program");
      System.exit(0);
    }

    System.out.println("bits: " + bits);
    System.out.println("string length: " + num.length());

    while (check == true) {
      if (num.length() < bits) {
        System.out.println("====text less than bits====");
        conditionInput(num);
      }
      check = false;
    }
    return num;
  }

  public static String customizeBinary(String num, long bits) {
    int i = 1;
    int j = 0;
    boolean start = true;
    String result = "";
    char[] ch = num.toCharArray();
    for (char c : ch) {
      if (j < bits) {
        if (c == '0' && start == true) {
          System.out.println("remove zero");
        } else {
          start = false;
          result += String.valueOf(c);
          System.out.println("count: " + i);
          System.out.println("binary: " + result);
          i++;
          j++;
        }
      }
    }
    System.out.println("====RESULT====");
    System.out.println("result: " + result);
    return result;
  }

  public static long BinaryToDecimal(String resultBinary) {
    long decimal = Integer.parseInt(resultBinary, 2);
    System.out.println("Decimal: " + decimal);
    return decimal;
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

  public static boolean safePrime(long n) {
    long n2 = (n - 1) / 2;
    for (int i = 0; i < 100; i++) {
      long a = 1 + (long) (Math.random() * (n2 - 1));
      long g = gcdExtended(a, n2);
      if (g > 1) {
        return false;
      } else if (power(a, ((n2 - 1) / 2), n2) % n2 == 1 ||
          power(a, ((n2 - 1) / 2), n2) % n2 == n2 - 1) {
      } else {
        return false;
      }
    }
    return true;
  }

  public static boolean prime(long n) {
    for (int i = 0; i < 100; i++) {
      long a = 1 + (long) (Math.random() * (n - 1));
      long g = gcdExtended(a, n);
      if (g > 1) {
        System.out.println(n + " " + "is not prime ");
        return false;
      } else if (power(a, ((n - 1) / 2), n) % n == 1 ||
          power(a, ((n - 1) / 2), n) % n == n - 1) {

      } else {
        System.out.println(n + " " + "is not prime ");
        return false;
      }
    }
    return true;
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

  public static long FineKeyGen(long SafePrime) {
    long a = 1 + (long) (Math.random() * (SafePrime - 1));
    long pow = ((SafePrime - 1) / 2);
    System.out.println("====Find Key generator====");

    long result = power(a, pow, SafePrime);
    System.out.println(result + " Check result");

    if (result != 1 % SafePrime) {
      System.out.println("====It's Key generator====");
      System.out.println(a);
      return a;
    } else {
      System.out.println("====Not Key generator=====");
      long sum = ((a * -1) % SafePrime) + SafePrime;
      System.out.println(a + " is Key generator now");
      return sum;
    }
  }

  public static void CreateKey(long Generator, long SafePrime) throws IOException {
    long g = Generator;
    long u = 1 + (long) (Math.random() * (SafePrime - 1));
    System.out.println("====CreateKey====");
    long y = power(g, u, SafePrime);

    System.out.println("====pulice Key including====");
    // System.out.println("P is : "+SafePrime);
    // System.out.println("G is : "+g);
    System.out.println("Y is : " + y);
    System.out.println("====Secret Key is====");
    System.out.println("U is : " + u);
    FileWriter(SafePrime, g, y, u);
  }

  public static void FileWriter(long SafePrime, long g, long y, long u)
      throws IOException {
    String fw_p = Long.toString(SafePrime);
    String fw_g = Long.toString(g);
    String fw_y = Long.toString(y);
    String fw_u = Long.toString(u);
    File myObj2 = new File("PK.txt");
    File myObj3 = new File("SK.txt");
    myObj2.createNewFile();
    myObj3.createNewFile();
    FileWriter fw = new FileWriter("PK.txt", true);
    try (FileWriter fw2 = new FileWriter("SK.txt", true)) {
      fw.write(fw_p + " ");
      fw.write(fw_g + " ");
      fw.write(fw_y);
      fw2.write(fw_p + " ");
      fw2.write(fw_g + " ");
      fw2.write(fw_u);
    }
    System.out.println("Completed writing into text file");
    fw.close();
  }

}


