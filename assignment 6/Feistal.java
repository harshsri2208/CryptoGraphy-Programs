import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Feistal {

    private static String K1, K2;

    private static String randomKey(int n) {
        String rKey = "";
        for (int i = 0; i < n; ++i) {
            rKey += Integer.toString((int)Math.round(Math.random()));
        }
        return rKey;
    }

    private static String exor(String a, String b) {
        String ans = "";
        int n = a.length();
        for (int i = 0; i < n; ++i) {
            if (a.charAt(i) == b.charAt(i)) {
                ans += "0";
            }
            else {
                ans += "1";
            }
        }
        return ans;
    }

    private static String decToBinary(int a) {
        String temp = Integer.toBinaryString(a);
        while(temp.length() !=8){
            temp = "0"+temp;
        }
        return temp;
    }

    private static String feistEncryption(String plainText) {
        // Array of Ascii for characters
        int len = plainText.length();
        int plainTextAscii[] = new int[len];
        for (int i = 0; i < len; ++i) {
            plainTextAscii[i] = (int)plainText.charAt(i);
        }

        String plainTextBinary = "";
        for (int i = 0; i < len; ++i) {
            plainTextBinary += decToBinary(plainTextAscii[i]);
        }

        int n = plainTextBinary.length() / 2;
        String L1 = plainTextBinary.substring(0, n);
        String R1 = plainTextBinary.substring(n);
        int m = R1.length();

        K1 = randomKey(m);
        K2 = randomKey(m);

        String f1 = exor(R1, K1);
        String R2 = exor(f1, L1);
        String L2 = R1;

        String f2 = exor(R2, K2);
        String R3 = exor(f2, L2);
        String L3 = R2;

        String binRes = L3 + R3;
        String encrypted = "";
        for (int i = 0; i < binRes.length(); i += 8) {
            String temp = binRes.substring(i, i + 8);
            encrypted += (char)Integer.parseInt(temp, 2);
        }
        return encrypted;
    }

    private static String feistDecryption(String plainText) {
        // Array of Ascii for characters
        int len = plainText.length();
        int plainTextAscii[] = new int[len];
        for (int i = 0; i < len; ++i) {
            plainTextAscii[i] = (int)plainText.charAt(i);
        }

        String plainTextBinary = "";
        for (int i = 0; i < len; ++i) {
            plainTextBinary += decToBinary(plainTextAscii[i]);
        }

        int n = plainTextBinary.length() / 2;
        String L4 = plainTextBinary.substring(0, n);
        String R4 = plainTextBinary.substring(n);

        String f3 = exor(L4, K2);
        String L5 = exor(R4, f3);
        String R5 = L4;

        String f4 = exor(L5, K1);
        String L6 = exor(R5, f4);
        String R6 = L5;

        String binRes = L6 + R6;
        String encrypted = "";
        for (int i = 0; i < binRes.length(); i += 8) {
            String temp = binRes.substring(i, i + 8);
            encrypted += (char)Integer.parseInt(temp, 2);
        }
        return encrypted;
    }

    public static void main(String[] args) throws IOException, FileNotFoundException{
        String inputFilePath = "./input.txt";
        String outputFilePath = "./output.txt";

        // Reading the input
        FileReader inputRead = new FileReader(inputFilePath);
        Scanner scan = new Scanner(inputRead);
        String plainText = scan.nextLine();
        inputRead.close();
        scan.close();

        //storing the output
        String encrypted = feistEncryption(plainText);
        String decrypted = feistDecryption(encrypted);
        FileWriter outputWrite = new FileWriter(outputFilePath);
        outputWrite.write("Encrypted Text = " + encrypted + "\n");
        outputWrite.write("Decrypted Text = " + decrypted);

        outputWrite.close();

        //System.out.println(K1 + " " + K2);
    }
}