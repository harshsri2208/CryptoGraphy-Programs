import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Caesar {

    public static void encrypt(FileReader input, int shift, FileWriter output) throws IOException {

        if (shift < 0) {
            shift = ((shift % 26) + 26) % 26;
        }

        while(input.ready()) {
            int asc = input.read();
            char c1;
            if (asc >= 65 && asc <= 90) { // uppercase english characters
                c1 = (char)((asc - 65 + shift) % 26 + 65);
            }
            else if (asc >= 97 && asc <= 122) { // lowercase english alphabets
                c1 = (char)((asc - 97 + shift) % 26 + 97);
            }
            else { // numbers and special characters
                c1 = (char)asc;
            }
            output.write(c1);
        }
        output.write("\n");
    }

    public static void main(String args[]) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String inputFilePath = "./Harsh.txt", outputFilePathEnc = "./Srivastava_enc.txt";
        String outputFilePathDec = "./Srivastava_dec.txt";
        FileReader input = new FileReader(inputFilePath);
        FileWriter output = new FileWriter(outputFilePathEnc);
        
        // encryption
        System.out.println("Enter shift value");
        int shift = Integer.parseInt(in.readLine());
        encrypt(input, shift, output);
        output.close();

        //decryption for encrypted file
        input = new FileReader(outputFilePathEnc);
        output = new FileWriter(outputFilePathDec);
        encrypt(input, -1 * shift, output);

        input.close();
        output.close();
    }
}
