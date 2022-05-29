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
        System.out.println("4: Convert message to picture");
        System.out.println("5: Exit program");
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
            case 4:
                Gen64ToImage.main(args);
                break;
            case 5:
                System.out.println("--------");
                System.out.println("Goodbye");
                System.out.println("--------");
                System.exit(0);
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
