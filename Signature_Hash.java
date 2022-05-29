import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileWriter; //import the FileWriter class to write text files
import java.io.IOException;
import java.security.MessageDigest;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class Signature_Hash {
  static Scanner sc = new Scanner(System.in);
  static String Filename,InputKey;
  static int extraZero = 0;
  static int SPresult = 0;

  public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
    Input();
    System.out.println("====ReadFile====");
    String text = readfiles(Filename);

    System.out.println("====Hash====");
    String Hash = toHexString(getSHA(text));
    System.out.println("Hash = " + Hash);

    FileWriter(Hash);
  }

  public static void Input() {
    System.out.println("=====input Filename=====");
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

  public static byte[] getSHA(String text) throws NoSuchAlgorithmException {
    // Static getInstance method is called with hashing SHA
    MessageDigest md = MessageDigest.getInstance("SHA-256");

    // digest() method called
    // to calculate message digest of an input
    // and return array of byte
    return md.digest(text.getBytes(StandardCharsets.UTF_8));
  }

  public static String toHexString(byte[] hash) {
    // Convert byte array into signum representation
    BigInteger number = new BigInteger(1, hash);

    // Convert message digest into hex value
    StringBuilder hexString = new StringBuilder(number.toString(10));

    // Pad with leading zeros
    while (hexString.length() < 64) {
      hexString.insert(0, '0');
    }

    return hexString.toString();
  }

  public static void FileWriter(String Hash)
      throws IOException {
    String fwsh = Hash;
    File myObj2 = new File("Hash.txt");
    myObj2.createNewFile();
    FileWriter fw = new FileWriter("Hash.txt", true);
    fw.write(fwsh + " ");
    System.out.println("Completed writing into text file");
    fw.close();
  }
}
