import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Vigenere {
    public static void main(String[] args) throws IOException {

        // Loading the input
        String inputFilePath = "./input.txt";
        FileReader input = new FileReader(inputFilePath);

        // extracting plaintext and key from input
        String plainText = "", key = "";
        Scanner scan = new Scanner(input);

        // extracting Plain Text
        plainText = scan.nextLine();
        plainText = plainText.substring(plainText.indexOf("=") + 1).trim();

        // extracting key
        key = scan.nextLine();
        key = key.substring(key.indexOf("=") + 1).trim();

        // encrypting the plaintext with the key
        String encrypted = vigenereEncrypt(plainText, key);

        // decrypting the encrypted ciphertext using key
        String decrypted = vigenereDecrypt(encrypted, key);

        // writing results to output file
        String outputFilePath = "./output.txt";
        FileWriter output = new FileWriter(outputFilePath);
        output.write("Encrypted Message = " + encrypted + "\n");
        output.write("Decrypted Message = " + decrypted);

        input.close();
        output.close();
        scan.close();

    }

    private static String keyRep(String plainText, String key) { // function to add key to itself till length becomes
                                                                 // equal to plaintext
        int plainTextLength = plainText.length();
        int keyLength = key.length();
        String newKey = "";
        int j = 0;
        for (int i = 0; i < plainTextLength; ++i) {
            char ch = plainText.charAt(i);

            if ((ch >= 'A' && ch <= 'Z')) {
                newKey += Character.toUpperCase(key.charAt((j++) % keyLength));
            } else if (ch >= 'a' && ch <= 'z') {
                newKey += Character.toLowerCase(key.charAt((j++) % keyLength));
            } else {
                newKey += ch;
            }
        }
        return newKey;
    }

    private static char vigenereFunction(char p, char k, boolean flag) { // flag checks if it is encryption or
                                                                         // decryption
        // handling case
        int asc = (p >= 'A' && p <= 'Z') ? 65 : 97;

        if (flag) { // encyption
            return (char) ((((int) p + (int) k - (2 * asc)) % 26) + asc);
        }
        return (char) ((((int) p - (int) k + 26) % 26) + asc); // decryption
    }

    private static String vigenereEncrypt(String plainText, String key) { // encryption of plaintext
        // appending key to itself till length becomes equal to plainText
        int plainTextLength = plainText.length();
        String newKey = keyRep(plainText, key);

        String encrypted = "";
        for (int i = 0; i < plainTextLength; ++i) {
            char ch = plainText.charAt(i);
            char k = newKey.charAt(i);

            if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
                ch = vigenereFunction(ch, k, true); // using experession (Pi + Ki) mod 26
            }
            encrypted += ch;
        }
        return encrypted;
    }

    private static String vigenereDecrypt(String cipherText, String key) { //decryption of ciphertext
        // appending key to itself till length becomes equal to cipherText
        int cipherTextLength = cipherText.length();
        String newKey = keyRep(cipherText, key);

        String encrypted = "";
        for (int i = 0; i < cipherTextLength; ++i) {
            char ch = cipherText.charAt(i);
            char k = newKey.charAt(i);

            if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
                ch = vigenereFunction(ch, k, false); // using experession (Ei - Ki + 26) mod 26
            }
            encrypted += ch;
        }
        return encrypted;
    }
}
