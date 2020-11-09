import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LinearCongruence {

    private static int modInverse(int a, int b) {
        int bi = b;
        int x = 1, y = 0;

        if (b == 1) {
            return 0;
        }

        while (a > 1) {

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

    private static int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    public static void main(String[] args) throws IOException, FileNotFoundException {
        String inputFilePath = "./input.txt";
        String outputFilePath = "./output.txt";

        // Reading the input
        // input format is ax = b(mod n)
        FileReader inputRead = new FileReader(inputFilePath);
        Scanner scan = new Scanner(inputRead);

        String modExp = scan.nextLine().toString();
        Pattern p = Pattern.compile("(\\s*)(?<A>[0-9]+)(\\s*x\\s*=\\s*)(?<B>[0-9]+)(\\s*\\(\\s*mod\\s*)(?<N>[0-9]+)(\\s*\\)\\s*)");
        Matcher m = p.matcher(modExp);
        int a = 0, b = 0, n = 0;

        if (m.find()) {
            a = Integer.parseInt(m.group("A"));
            b = Integer.parseInt(m.group("B"));
            n = Integer.parseInt(m.group("N"));
        }

        // closing input
        inputRead.close();
        scan.close();

        // Finding and storing the output
        int g = gcd(a, n);
        int a1 = a / g;
        int b1 = b / g;
        int n1 = n / g;
        int inv = modInverse(a1, n1); // finding the modulo inverse and multiplying with b
        int ans = (b1 * inv) % n1;
        FileWriter outputWrite = new FileWriter(outputFilePath);
        outputWrite.write("General solution for " + a + "x = " + b + "(mod " + n + ")\n");
        outputWrite.write("x = " + (n / g) + "k + " + ans);
        outputWrite.close();
    }
}