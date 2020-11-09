import java.io.*;
import java.util.*;

public class OTP {
    public static void main(String[] args) throws IOException, FileNotFoundException {
        // Loading the input
        String inputFilePath = "./input.txt";
        FileReader input = new FileReader(inputFilePath);

        // extracting plaintext from input
        String plaintextInput = "";
        Scanner scan = new Scanner(input);

        // extracting the plaintext
        plaintextInput = scan.nextLine().toUpperCase().trim();
        int len = plaintextInput.length();

        scan.close();

        // generating OTP
        String otp = "";
        for (int i = 0; i < len; ++i) {
            char ch = plaintextInput.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                otp += (char) ((int) (Math.random() * 26) + 65);
            }
            else {
                otp += ch;
            }
        }

        // encrypting plaintext
        String cipherText = otpEncrypt(plaintextInput, len, otp, true);

        // decrypting ciphertext
        String plainText = otpEncrypt(cipherText, len, otp, false);

        // writing outputs
        String outputFilename = "./output.txt";
        FileWriter fstream = new FileWriter(outputFilename);
        BufferedWriter out = new BufferedWriter(fstream);

        out.write("encrypted string = " + cipherText.trim() + "\n");
        out.write("decrypted string = " + plainText.trim());
        out.close();
    }

    private static String otpEncrypt(String plaintextInput, int len, String otp, boolean flag) {

        String cipherText = "";
        if (flag) { // encryption
            for (int i = 0; i < len; ++i) {
                char ch = plaintextInput.charAt(i);
                if (ch >= 'A' && ch <= 'Z') {
                    cipherText += (char)((((int)plaintextInput.charAt(i) + (int)otp.charAt(i) - 130) % 26) + 65);
                }
                else {
                    cipherText += ch;
                }
            }
        }
        else { // decryption
            for (int i = 0; i < len; ++i) {
                char ch = plaintextInput.charAt(i);
                if (ch >= 'A' && ch <= 'Z') {
                    cipherText += (char)((((((int)plaintextInput.charAt(i) - (int)otp.charAt(i)) % 26) + 26) % 26) + 65);
                }
                else {
                    cipherText += ch;
                }
            }
        }
        return cipherText;

    }
}
