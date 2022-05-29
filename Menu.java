import java.io.IOException;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("");
        System.out.println("Select 1, 2, 3, 4, 5 or 6");
        System.out.println("1: Generate Key");
        System.out.println("2: Encryption");
        System.out.println("3: Decryption");
        System.out.println("4: Hash");
        System.out.println("5: Signature");
        System.out.println("6: Verify");
        System.out.println("7: Exit program");
        System.out.println("--------");
        System.out.print("input number: ");
        int i = sc.nextInt();
        
        switch(i){
            case 1:
                KeyGen.main(args);
                break;
            case 2:
                Encrypt.main(args);
                break;
            case 3:
                Decrypt.main(args);
                break;
            case 7:
                System.out.println("--------");
                System.out.println("Goodbye");
                System.exit(0);
                System.out.println("--------");
                break;
            default:
                System.out.println("--------");
                System.out.println("Wrong input try again");
                System.out.println("--------");
                Menu.main(args);
                break;
        }
        Menu.main(args);

    }
}
