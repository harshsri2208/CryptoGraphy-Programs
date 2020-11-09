import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class ModInv {

    private static int modInverse(int a, int b) {
        int bi = b;
        int x = 1, y = 0;

        if (b == 1) {
            return 0;
        }

        while(a > 1) {

            int temp = y;
            y = x - ((a / b) * y);
            x = temp;

            temp = b;
            b = a % b;
            a = temp;
        }

        if (x < 0) {
            x += bi;
        }

        return x;

    }

    public static void main(String[] args) throws IOException, FileNotFoundException{
        String inputFilePath = "./input.txt";
        String outputFilePath = "./output.txt";

        // Reading the input
        FileReader inputRead = new FileReader(inputFilePath);
        Scanner scan = new Scanner(inputRead);
        int a = Integer.parseInt(scan.next());
        int n = Integer.parseInt(scan.next());
        inputRead.close();
        scan.close();

        // Finding and storing the output
        int b = modInverse(a, n);
        FileWriter outputWrite = new FileWriter(outputFilePath);
        outputWrite.write("Multiplicative inverse of " + a + " in the set z" + n + " = " + b);

        outputWrite.close();
    }
}