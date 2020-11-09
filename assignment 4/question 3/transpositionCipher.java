import java.util.*;
import java.io.*;

public class transpositionCipher {
    public static void main(String[] args) throws IOException, FileNotFoundException {
        // Loading the input
        String inputFilePath = "./input.txt";
        FileReader input = new FileReader(inputFilePath);

        // extracting plaintext from input
        String plaintextInput = "";
        Scanner scan = new Scanner(input);

        // extracting the plaintext
        plaintextInput = scan.nextLine().trim();
        int len = plaintextInput.length();

        // extracting the permutation key
        String permKey = "";
        ArrayList <String> permKeyList = new ArrayList<>();
        while(scan.hasNext()) {
            String x = scan.nextLine();
            permKey += x;
            permKeyList.add(x);
        }
        scan.close();

        // System.out.println(plaintextInput);
        // System.out.println(permKey);

        String cipherText = transpositionEncrypt(permKey, plaintextInput, len, permKeyList);
        String plainText = transpositionDecrypt(cipherText, permKey, permKeyList);

        // writing outputs
        String outputFilename = "./output.txt";
        FileWriter fstream = new FileWriter(outputFilename);
        BufferedWriter out = new BufferedWriter(fstream);

        out.write("encrypted string = " + cipherText.trim() + "\n");
        out.write("decrypted string = " + plainText.trim());
        out.close();
    }

    private static String transpositionEncrypt (String permKey, String plaintextInput, int len, 
            ArrayList<String> permKeyList) {
        int cols = permKey.length();
        int rows = len % cols == 0 ? (len / cols) : ((len / cols) + 1);

        // matrix for ciphertext
        char cipherMat[][] = new char[rows][cols];
        int k = 0;
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (k >= len) {
                    cipherMat[i][j] = ' ';
                } else {
                    cipherMat[i][j] = plaintextInput.charAt(k++);
                }
            }
        }

        // final ciphertext
        String cipherText = "";
        for (int i = 0; i < permKey.length(); ++i) {
            int col = permKeyList.indexOf(Integer.toString(i + 1));
            for (int j = 0; j < rows; ++j) {
                cipherText += cipherMat[j][col];
            }
        }
        //System.out.println(cipherText);
        return cipherText;
    }

    private static String transpositionDecrypt (String cipherText, String permKey, ArrayList<String> permKeyList) {
        // matrix for plaintext
        int cols = permKey.length();
        int rows = cipherText.length() / cols;
        char plaintextMat[][] = new char[rows][cols]; int k = 0;
        for (int i = 0; i < cols; ++i) {
            for (int j = 0; j < rows; ++j) {
                if (k >= cipherText.length()) {
                    plaintextMat[j][i] = ' ';
                }
                else {
                    plaintextMat[j][i] = cipherText.charAt(k++);
                }
            }
        }

        // final plaintext
        String plainText = "";
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                int col = Integer.parseInt(permKeyList.get(j)) - 1;
                plainText += plaintextMat[i][col];
            }
        }
        //System.out.println(plainText);
        return plainText;
    }

}
