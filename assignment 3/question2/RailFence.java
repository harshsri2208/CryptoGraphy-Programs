//package question2;

import java.util.*;
import java.io.*;

public class RailFence {

    private static String railFenceEncrypt(String plainText, int depth) {
        int r = depth, len = plainText.length();
        int c = (int)Math.ceil((double)len / (double)depth);
        char wordMat[][] = new char[r][c];
        int k = 0;

        String cipherText = "";
        for (int i = 0; i < c; i++) {
            for (int j = 0; j < r; j++) {
                if (k != len)
                    wordMat[j][i] = plainText.charAt(k++);
                else
                    wordMat[j][i] = ' '; // whitespace for empty and non required spaces
            }
        }
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                cipherText += wordMat[i][j];
            }
        }
        return cipherText;
    }

    private static String railFenceDecrypt(String cipherText, int depth) {
        int r = depth, len = cipherText.length();
        int c = (int)Math.ceil((double)len / (double)depth);
        char wordMat[][] = new char[r][c];
        int k = 0;

        String plainText = "";
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                wordMat[i][j] = cipherText.charAt(k++);
            }
        }
        for (int i = 0; i < c; i++) {
            for (int j = 0; j < r; j++) {
                plainText += wordMat[j][i];
            }
        }
        return plainText;
    }


    public static void main(String[] args) throws IOException {

        // Loading the input
        String inputFilePath = "./plaintext.txt";
        FileReader input = new FileReader(inputFilePath);

        // extracting plaintext from input
        String plaintextInput = "";
        Scanner scan = new Scanner(input);

        // extracting the plaintext
        plaintextInput = scan.nextLine().trim();

        // Loading key input file
        String railInputPath = "./rail_depth.txt";
        FileReader railFile = new FileReader(railInputPath);

        // extracting the key
        Scanner scanRail = new Scanner(railFile);
        int rails = scanRail.nextInt();

        String outputFilename = "./output.txt";
        FileWriter fstream = new FileWriter(outputFilename);
        BufferedWriter out = new BufferedWriter(fstream);

        String encrypted = railFenceEncrypt(plaintextInput, rails);
        String decrypted = railFenceDecrypt(encrypted, rails);

        out.write("encrypted string = " + encrypted.trim() + "\n");
        out.write("decrypted string = " + decrypted.trim());

        out.close();
    }
}