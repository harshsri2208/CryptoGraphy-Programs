import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PlayFair {
    public static void main(String[] args) throws IOException {

        // Loading the input
        String inputFilePath = "./input.txt";
        FileReader input = new FileReader(inputFilePath);

        // extracting plaintext and key from input
        String plainText = "", key = "";
        Scanner scan = new Scanner(input);

        // extracting the plaintext
        plainText = scan.nextLine();
        plainText = plainText.substring(plainText.indexOf("=") + 1).trim().toUpperCase();
        // removing whitespaces from plaintext and replacing J with I
        String plainTextW = plainText.replaceAll("\\s", "").replaceAll("J", "I");

        // extracting the key
        key = scan.nextLine();
        key = key.substring(key.indexOf("=") + 1).trim().toUpperCase();
        key = removeDuplicateChars(key); // removing duplicate characters because PlayFair key should have unique
                                         // characters

        // encrypting the plaintext with the key
        String encrypted = playFairEncrypt(plainTextW, key);

        // uncomment below line to restore whitespaces
        // encrypted = restoreString(encrypted, plainText);

        // decrypting the encrypted ciphertext using key
        String encryptedW = encrypted.replaceAll("\\s", "").replaceAll("J", "I");
        String decrypted = playFairDecrypt(encryptedW, key);

        // uncomment below line to restore whitespaces
        // decrypted = restoreString(decrypted, encrypted);

        // writing results to output file
        String outputFilePath = "./output.txt";
        FileWriter output = new FileWriter(outputFilePath);
        output.write("Encrypted Message = " + encrypted + "\n");
        output.write("Decrypted Message = " + decrypted);

        input.close();
        output.close();
        scan.close();

    }

    private static void createKeyMat(String plainText, String key, char keyMat[][]) { // creating a key matrix of size
                                                                                      // 5 x 5
        int plainTextLength = plainText.length();
        int keyLength = key.length();

        int flag[] = new int[26];
        flag['J' - 'A'] = 1; // excluding J

        int k = 0, l = 0;
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                if (k < keyLength) {
                    char ch = key.charAt(k);
                    keyMat[i][j] = ch;
                    flag[ch - 'A'] = 1;
                    ++k;
                } else {
                    while (flag[l] == 1) {
                        ++l;
                    }
                    keyMat[i][j] = (char) (l + 65);
                    ++l;
                }
            }
        }
    }

    public static void createDoubleMap(String mapDouble[], String plainText) { // creating an array storing string as
                                                                               // character pairs
        int plainTextLength = plainText.length();
        int mapDoubleLength = mapDouble.length;

        int k = 0;
        for (int i = 0; i < plainTextLength; i += 2) {
            if (i == (plainTextLength - 1)) {
                mapDouble[k++] = plainText.charAt(i) + "Z";
            } else {
                mapDouble[k++] = plainText.substring(i, i + 2);
            }
        }
    }

    private static int findIndex(char val, char keyMat[][]) { // find index of character in matrix. Return row and
                                                              // column as a single value
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                if (val == keyMat[i][j])
                    return (i * 5) + j;
            }
        }
        return -1;
    }

    private static String playFairEncrypt(String plainText, String key) { // function to encrypt plaintext
        // appending key to itself till length becomes equal to plainText
        int plainTextLength = plainText.length();
        int keyLength = key.length();
        char keyMat[][] = new char[5][5];
        createKeyMat(plainText, key, keyMat);

        // for (int i = 0; i < 5; ++i) {
        // for (int j = 0; j < 5; ++j) {
        // System.out.print(keyMat[i][j] + " ");
        // }
        // System.out.println();
        // }

        // length of array for storing 2 characters at a time
        int mapLength = (plainTextLength + 1) / 2;
        String mapDouble[] = new String[mapLength];
        createDoubleMap(mapDouble, plainText);

        String encrypted = "";

        for (int i = 0; i < mapLength; ++i) {
            int i1 = findIndex(mapDouble[i].charAt(0), keyMat);
            int i2 = findIndex(mapDouble[i].charAt(1), keyMat);

            int r1 = i1 / 5, c1 = i1 % 5; // row and column index of first character in pair
            int r2 = i2 / 5, c2 = i2 % 5; // row and column index of second character in pair

            if (c1 == c2) { // if they belong to same column
                encrypted += keyMat[(r1 + 1) % 5][c1];
                encrypted += keyMat[(r2 + 1) % 5][c2];
            } else if (r1 == r2) { // else if they belong to the same row
                encrypted += keyMat[r1][(c1 + 1) % 5];
                encrypted += keyMat[r2][(c2 + 1) % 5];
            } else { // neither same row nor column
                encrypted += keyMat[r1][c2];
                encrypted += keyMat[r2][c1];
            }
        }

        return encrypted;
    }

    private static String playFairDecrypt(String cipherText, String key) { // function to decrypt cipher text
        // appending key to itself till length becomes equal to cipherText
        int cipherTextLength = cipherText.length();
        int keyLength = key.length();
        char keyMat[][] = new char[5][5];
        createKeyMat(cipherText, key, keyMat);

        // for (int i = 0; i < 5; ++i) {
        // for (int j = 0; j < 5; ++j) {
        // System.out.print(keyMat[i][j] + " ");
        // }
        // System.out.println();
        // }

        // length of array for storing 2 characters at a time
        int mapLength = (cipherTextLength + 1) / 2;
        String mapDouble[] = new String[mapLength];
        createDoubleMap(mapDouble, cipherText);

        String decrypted = "";

        for (int i = 0; i < mapLength; ++i) {
            int i1 = findIndex(mapDouble[i].charAt(0), keyMat);
            int i2 = findIndex(mapDouble[i].charAt(1), keyMat);

            int r1 = i1 / 5, c1 = i1 % 5; // row and column index of first character in pair
            int r2 = i2 / 5, c2 = i2 % 5; // row and column index of second character in pair

            if (c1 == c2) { // if they belong to same column
                decrypted += keyMat[((r1 - 1) + 5) % 5][c1];
                decrypted += keyMat[((r2 - 1) + 5) % 5][c2];
            } else if (r1 == r2) { // else if they belong to the same row
                decrypted += keyMat[r1][((c1 - 1) + 5) % 5];
                decrypted += keyMat[r2][((c2 - 1) + 5) % 5];
            } else { // neither same row nor column
                decrypted += keyMat[r1][c2];
                decrypted += keyMat[r2][c1];
            }
        }

        return decrypted;
    }

    private static String restoreString(String val, String org) { // function to restore spaces to the encrypted string
        String encryptedRestore = "";
        int k = 0;
        for (int i = 0; i < org.length(); ++i) {
            if (org.charAt(i) == ' ') {
                encryptedRestore += ' ';
            } else {
                encryptedRestore += val.charAt(k++);
            }
        }
        if (k == (val.length() - 1))
            encryptedRestore += val.charAt(k);
        return encryptedRestore;
    }

    private static String removeDuplicateChars(String sourceStr) { // function to remove duplicate characters
        // Store encountered letters in this string.
        char[] chrArray = sourceStr.toCharArray();
        String targetStr = "";
        int hsh[] = new int[26];

        // Loop over each character.
        for (char value : chrArray) {
            // See if character is in the target
            if (hsh[value - 'A'] != 1) {
                targetStr += value;
                hsh[value - 'A'] = 1;
            }
        }
        return targetStr;
    }
}
