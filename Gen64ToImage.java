import java.io.ByteArrayInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import javax.imageio.ImageIO;


public class Gen64ToImage {

    public static void main(String[] args) throws IOException {

        String result = "";
        System.out.println("====input file image====");
        Scanner sc = new Scanner(System.in);
        String imageText = sc.nextLine();
        String type = getFileExtension(imageText);

        // if (type.equals("txt")){
            
        // }
        try {
            File myObj = new File(imageText);
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

        try {

            Decoder.BASE64Decoder decoder = new Decoder.BASE64Decoder();
            byte[] imageByte = decoder.decodeBuffer(result);
            System.out.println(Arrays.toString(imageByte));

            BufferedImage bis = ImageIO.read(new ByteArrayInputStream(imageByte));
            File f = new File("Picture.png");
            ImageIO.write(bis, "png", f);

        } catch (Exception a) {
            a.printStackTrace();
        }

    }
    
    public static String getFileExtension(String fullName) {
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

}