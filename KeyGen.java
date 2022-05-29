import java.io.ByteArrayOutputStream;
import java.io.File; // Import the File class
// import java.io.FileInputStream;
import java.io.FileNotFoundException; // Import this class to handle errors
import java.awt.image.BufferedImage;
// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files
import org.apache.xerces.impl.dv.util.Base64;

import javax.imageio.ImageIO;

// import org.apache.tools.ant.filters.ReplaceTokens.Token;

import java.io.FileWriter; //import the FileWriter class to write text files
import java.io.IOException; // Import the IOException class

public class KeyGen {
  static Scanner sc = new Scanner(System.in);
  static long bits;
  static String Filename;

  public static void main(String[] args) throws IOException {

    System.out.println("====Input file name or Text====");
    String file = Input();
    String typeInput = getFileExtension(file);

    System.out.println("====Read File====");
    String text = readInput(file, typeInput);

    System.out.println("====Convert====");
    String num = convertStringtoBinary(text);

    System.out.println(num);

    String s = conditionInput(num);

    System.out.println("====Correct====");
    System.out.println(s);

    System.out.println("====Cut Zero====");
    String resultBinary = customizeBinary(s, bits);

    System.out.println("====Binary to Decimal====");
    long decimal = BinaryToDecimal(resultBinary);

    System.out.println("====Check SafePrime====");
    long SafePrime = CheckPrime(decimal);
    long Generator = FineKeyGen(SafePrime);

    CreateKey(Generator, SafePrime);
  }

  // Input
  public static String Input() {

    System.out.println("input");
    Filename = sc.nextLine();
    return Filename;
  }

  // ReadFile from .txt
  public static String readInput(String filename, String type) throws IOException {
    String result = "";
    if (type.equals("txt")) {
      try {
        File myObj = new File(filename);
        Scanner myReader = new Scanner(myObj);

        // we use While Loop because if textfile have nextLine while loop can fix it
        while (myReader.hasNextLine()) {
          result += myReader.nextLine() + "\n";
        }

        myReader.close();

      } catch (FileNotFoundException e) {
        System.out.println("An error occurred, File is not found");
        e.printStackTrace();
      }
      System.out.print(result);
    } else if (type.equals("png") || type.equals("jpg")) {
      try {
        BufferedImage sourceimage = ImageIO.read(new File(filename));
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ImageIO.write(sourceimage, type, bytes);
        String resultantimage = Base64.encode(bytes.toByteArray());
        result = resultantimage;
        System.out.println(resultantimage);
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error??");
      }
    } else {
      Scanner myReader = new Scanner(filename);

      // we use While Loop because if textfile have nextLine while loop can fix it
      while (myReader.hasNextLine()) {
        result += myReader.nextLine() + "\n";
      }
      System.out.print(result);
      myReader.close();
    }

    return result;
  }

  // Check Safe Prime
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

  // ConvertStringToBinary
  public static String convertStringtoBinary(String text) {
    StringBuilder result = new StringBuilder();
    char[] chars = text.toCharArray();

    for (char aChar : chars) {
      result.append(String.format("%8s", Integer.toBinaryString(aChar)).replaceAll(" ", "0"));
    }

    return result.toString();
  }

  // Check Input is match with Condition
  public static String conditionInput(String num) {
    boolean check = true;
    System.out.println("====Check====");

    // How many numbers does each block have and must be less long datatype
    System.out
        .println("input bit less than " + (long) (Math.log(Long.MAX_VALUE) / Math.log(2) / 2) + " for find SafePrime");
    bits = sc.nextInt();

    if (bits < (long) (Math.log(Long.MAX_VALUE) / Math.log(2) / 2)) {

    } else {
      // out of range long exit program
      System.out.println("more " + (long) (Math.log(Long.MAX_VALUE) / Math.log(2) / 2));
      System.out.println("exit program");
      System.exit(0);
    }

    // print Select number each block
    System.out.println("bits: " + bits);
    // Input after convert to binary,How much it have range?
    System.out.println("string length: " + num.length());

    // check if select number each block have more than input?
    while (check == true) {
      if (num.length() < bits) {
        System.out.println("====text less than bits====");
        conditionInput(num);
      }
      check = false;
    }
    return num;
  }

  // cut zero in front of number in binary
  public static String customizeBinary(String num, long bits) {
    int i = 1;
    int j = 0;

    boolean start = true;

    String result = "";

    // spilt binary to char
    char[] ch = num.toCharArray();
    for (char c : ch) {
      if (j < bits) {
        if (c == '0' && start == true) {
          System.out.println("remove zero");
        } else {
          start = false;
          result += String.valueOf(c);
          // System.out.println("count: " + i);
          // System.out.println("binary: " + result);
          i++;
          j++;
        }
      }
    }

    System.out.println("====RESULT====");
    System.out.println("result: " + result);

    return result;
  }

  // convert binary to decimal
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
      // mod coz handle overflow
      y = y >> 1; // y = y/2
      x = x * x % n; // Change x to x^2
    }
    return res;
  }

  // Find Generator by lehman test
  public static long FineKeyGen(long SafePrime) {
    long a = 1 + (long) (Math.random() * (SafePrime - 1));
    long pow = ((SafePrime - 1) / 2);
    System.out.println("====Find Key generator====");

    long result = power(a, pow, SafePrime);
    // System.out.println(result + " Check result");

    if (result != 1 % SafePrime) {
      System.out.println("---It's Key generator---");
      System.out.println(a);

      return a;
    } else {
      System.out.println("---Not Key generator---");
      long sum = ((a * -1) % SafePrime) + SafePrime;

      System.out.println("---Key generator---");
      System.out.println(a);

      return sum;
    }
  }

  public static void CreateKey(long Generator, long SafePrime) throws IOException {
    long g = Generator;
    long u = 1 + (long) (Math.random() * (SafePrime - 1));
    long y = power(g, u, SafePrime);

    System.out.println("====CreateKey====");
    System.out.println("---pulice Key including---");
    System.out.println("P is : " + SafePrime);
    System.out.println("G is : " + g);
    System.out.println("Y is : " + y);

    System.out.println("---Secret Key is---");
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
    fw.close();
  }

  public static String getFileExtension(String fullName) {
    String fileName = new File(fullName).getName();
    int dotIndex = fileName.lastIndexOf('.');
    return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
  }

}
