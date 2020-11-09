import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AutoKey {
    public static void main(String[] args) throws IOException {

        // Loading the input
        String inputFilePath = "./input.txt";
        FileReader input = new FileReader(inputFilePath);

        // extracting plaintext and key from input
        String plainText = "", key = "", autoKey = "";
        Scanner scan = new Scanner(input);

        // extracting Plain Text
        plainText = scan.nextLine();
        plainText = plainText.substring(plainText.indexOf("=") + 1).trim();

        // extracting key
        autoKey = scan.nextLine();
        autoKey = autoKey.substring(autoKey.indexOf("=") + 1).trim();

        // generating key from autokey
        key = autoKey + plainText.substring(autoKey.length());
        System.out.println(key);

        // encrypting the plaintext with the key
        String encrypted = autoKeyEncrypt(plainText, key);

        // decrypting the encrypted ciphertext using key
        String decrypted = autoKeyDecrypt(encrypted, key);

        // writing results to output file
        String outputFilePath = "./output.txt";
        FileWriter output = new FileWriter(outputFilePath);
        output.write("Encrypted Message = " + encrypted.toUpperCase() + "\n");
        output.write("Decrypted Message = " + decrypted.toUpperCase());

        input.close();
        output.close();
        scan.close();

    }

    private static char autoKeyFunction(char p, char k, boolean flag) { // flag checks if it is encryption or
                                                                        // decryption
        // handling case
        int asc = (p >= 'A' && p <= 'Z') ? 65 : 97;

        if (flag) { // encyption
            return (char) ((((int) p + (int) k - (2 * asc)) % 26) + asc);
        }
        return (char) ((((int) p - (int) k + 26) % 26) + asc); // decryption
    }

    private static String autoKeyEncrypt(String plainText, String key) { // encryption of plaintext
        // appending key to itself till length becomes equal to plainText
        int plainTextLength = plainText.length();
        String newKey = key;// keyRep(plainText, key);

        String encrypted = "";
        for (int i = 0; i < plainTextLength; ++i) {
            char ch = plainText.charAt(i);
            char k = newKey.charAt(i);

            if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
                ch = autoKeyFunction(ch, k, true); // using experession (Pi + Ki) mod 26
            }
            encrypted += ch;
        }
        return encrypted;
    }

    private static String autoKeyDecrypt(String cipherText, String key) { // decryption of ciphertext
        // appending key to itself till length becomes equal to cipherText
        int cipherTextLength = cipherText.length();
        String newKey = key;// keyRep(cipherText, key);

        String encrypted = "";
        for (int i = 0; i < cipherTextLength; ++i) {
            char ch = cipherText.charAt(i);
            char k = newKey.charAt(i);

            if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
                ch = autoKeyFunction(ch, k, false); // using experession (Ei - Ki + 26) mod 26
            }
            encrypted += ch;
        }
        return encrypted;
    }
}
