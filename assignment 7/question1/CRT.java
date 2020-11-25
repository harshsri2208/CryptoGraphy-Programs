import java.io.*;
import java.util.*;

public class CRT {

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

    private static int CRTFunc(ArrayList<Integer> n, ArrayList<Integer> a) {
        int N = 1; // product of all elements in n
        for (Integer i: n) {
            N *= i;
        }

        int res = 0;
        for (int i = 0; i < n.size(); ++i) {
            int ai = a.get(i);
            int ni = n.get(i);
            int bi = N / ni;

            res += ai * bi * modInverse(bi, ni);
        }

        return res % N;
    }

    public static void main(String[] args) throws IOException, FileNotFoundException {
        String inputFilePath = "./input.txt";
        String outputFilePath = "./output.txt";

        // Reading the input
        FileReader inputRead = new FileReader(inputFilePath);
        Scanner scan = new Scanner(inputRead);
        ArrayList<Integer> n = new ArrayList<>();
        ArrayList<Integer> a = new ArrayList<>();
        while(scan.hasNextLine()) {
            String eq = scan.nextLine();
            int i1 = eq.indexOf('=');
            int i2 = eq.indexOf('(');
            int i3 = eq.indexOf("mod");
            int i4 = eq.indexOf(')');
            int i5 = eq.indexOf('x');

            int ni = Integer.parseInt(eq.substring(i3 + 3, i4).trim());
            int ai = Integer.parseInt(eq.substring(i1 + 1, i2).trim());
            int xi = 1;
            if (eq.substring(0, i5).trim().equals("") == false) {
                xi = Integer.parseInt(eq.substring(0, i5).trim());
            }
            if (eq.contains("+")) {
                int ci = Integer.parseInt(eq.substring(eq.indexOf('+') + 1, i1).trim());
                ai = ai - ci;
            }
            else if (eq.contains("-")) {
                int ci = Integer.parseInt(eq.substring(eq.indexOf('-') + 1, i1).trim());
                ai = ai + ci;
            }
            int modInvi = modInverse(xi, ni);
            ai = (ai * modInvi) % ni; 

            n.add(ni);
            a.add(ai);

            System.out.println(ai + " " + ni + " " + xi);
        }
        inputRead.close();
        scan.close();

        // chinese remainder theorem
        int x = CRTFunc(n, a);
        FileWriter outputWrite = new FileWriter(outputFilePath);
        outputWrite.write("solution for the system of linear congruences is\n");
        outputWrite.write("x = " + x);
        outputWrite.close();
    }
}