import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Gen64ToImage {

    public static void main(String[] args) throws IOException{
        
        System.out.println("====input file image====");
        Scanner sc = new Scanner(System.in);
        String image = sc.nextLine();

        File myObj = new File("MessagePicture.png");
        myObj.createNewFile();
        FileWriter fw = new FileWriter("MessagePicture.png", true);
        System.out.println("--------");
        fw.write(image);
        fw.close();
    }
}