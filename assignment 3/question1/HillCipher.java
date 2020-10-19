import java.util.*;
import java.io.*;

public class HillCipher {

    private static int N;

    static int modInv(int a, int b) { // utility function to calculate modular multiplicative inverse of a w.r.t b
        a = a % b;
        for (int x = 1; x < b; x++)
            if ((a * x) % b == 1)
                return x;
        return 1;
    }

    // function for cofactor of a matrix
    private static void getCofactor(int A[][], int temp[][], int p, int q, int n) {
        int i = 0, j = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                if (row != p && col != q) {
                    temp[i][j++] = A[row][col];

                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    // function for determinant of a matrix
    private static int determinant(int A[][], int n) {
        int D = 0;

        if (n == 1)
            return A[0][0];

        int[][] temp = new int[N][N];

        int sign = 1;

        for (int f = 0; f < n; f++) {

            getCofactor(A, temp, 0, f, n);
            D += sign * A[0][f] * determinant(temp, n - 1);

            sign = -sign;
        }

        return D;
    }

    // adjoint of matrix
    static void adjoint(int A[][], int[][] adj) {
        if (N == 1) {
            adj[0][0] = 1;
            return;
        }

        int sign = 1;
        int[][] temp = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                getCofactor(A, temp, i, j, N);

                sign = ((i + j) % 2 == 0) ? 1 : -1;

                adj[j][i] = (sign) * (determinant(temp, N - 1));
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                adj[i][j] = ((adj[i][j] % 26) + 26) % 26;
            }
        }
    }

    // inverse of matrix
    private static void inverse(int A[][], int[][] inverse) {

        int det = determinant(A, N);
        det = ((det % 26) + 26) % 26;
        if (det == 0) {
            System.out.print("Singular matrix, can't find its inverse");
            return;
        }

        int mulInv = modInv(det, 26);

        int[][] adj = new int[N][N];
        adjoint(A, adj);

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                inverse[i][j] = (adj[i][j] * mulInv) % 26;
    }

    public static void inverse2by2(int key[][], int invKey[][]) { // utility
        // function to calculate inverse of a 2 x 2 matrix
        try {

            // calculating determinant
            int det = ((key[0][0] * key[1][1]) - (key[1][0] * key[0][1])) % 26;
            det = (((det % 26) + 26) % 26);
            int mulInv = modInv(det, 26);
            System.out.println(mulInv);

            // finding adjoint
            int temp = invKey[0][0];
            invKey[0][0] = invKey[1][1];
            invKey[1][1] = temp;

            invKey[0][1] = (-1 * invKey[0][1]);
            invKey[1][0] = (-1 * invKey[1][0]);

            // finding modulo of negative values with 26 to make positive
            invKey[0][1] = (((invKey[0][1] % 26) + 26) % 26);
            invKey[1][0] = (((invKey[1][0] % 26) + 26) % 26);

            // multiplying each element with mulInv to get inverse
            for (int i = 0; i < 2; ++i) {
                for (int j = 0; j < 2; ++j) {
                    invKey[i][j] = (((invKey[i][j] * mulInv) % 26) + 26) % 26;
                    // System.out.print(invKey[i][j] + " ");
                }
                // System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Cannot find inverse of key matrix = " + e.getMessage());
        }

    }

    private static String hillEncrypt(String plaintextInput, int key[][], int keyRowsCols) { // function for encrypting
                                                                                             // using hill cipher

        int extra = (plaintextInput.length() + 1) % keyRowsCols;
        while (extra-- > 0) {
            plaintextInput += 'x';
        }

        char[] rawPlaintext = plaintextInput.toLowerCase().toCharArray();
        char[] plaintext = new char[rawPlaintext.length];

        int j = 0;
        for (int i = 0; i < rawPlaintext.length; i++) {
            if (rawPlaintext[i] >= 'a' && rawPlaintext[i] <= 'z') {// taking only lower case characters
                plaintext[j++] = rawPlaintext[i];
            }
        }
        char[] ciphertext = new char[plaintext.length];

        for (int l = 0; l < j; l += keyRowsCols) {
            for (int r = 0; r < keyRowsCols; r++) {
                for (int c = 0; c < keyRowsCols; c++) {
                    if ((l + c) < j) {
                        ciphertext[l + r] += (char) (key[r][c] * (plaintext[l + c] - 'a'));
                    } else {
                        ciphertext[l + r] += (char) (key[r][c] * ('x' - 'a'));
                    }
                }
                ciphertext[(l + r)] = (char) (ciphertext[(l + r)] % 26 + 'a');
            }
        }

        String res = "";
        j = 0;
        for (int i = 0; i < rawPlaintext.length; ++i) {
            if (rawPlaintext[i] < 'a' || rawPlaintext[i] > 'z') {
                res += rawPlaintext[i];
            } else {
                res += ciphertext[j++];
            }
        }
        return res;

    }

    public static void main(String[] args) throws IOException {

        // Loading the input
        String inputFilePath = "./plaintext.txt";
        FileReader input = new FileReader(inputFilePath);

        // output load
        String outputFilename = "./output.txt";
        FileWriter fstream = new FileWriter(outputFilename);
        BufferedWriter out = new BufferedWriter(fstream);

        // extracting plaintext from input
        String plainText = "";
        Scanner scan = new Scanner(input);

        // extracting the plaintext
        plainText = scan.nextLine();
        out.write("Plain Text = " + plainText + "\n");
        String plaintextInput = plainText.trim().toLowerCase();
        scan.close();

        // Loading key input file
        String keyInputPath = "./key.txt";
        FileReader keyFile = new FileReader(keyInputPath);

        // extracting the key
        out.write("Key = \n");
        Scanner scanKey = new Scanner(keyFile);
        int keyRowsCols = scanKey.nextInt();
        N = keyRowsCols;
        int key[][] = new int[keyRowsCols][keyRowsCols];
        for (int i = 0; i < keyRowsCols; ++i) {
            for (int j = 0; j < keyRowsCols; ++j) {
                key[i][j] = scanKey.nextInt();
                out.write(key[i][j] + " ");
            }
            out.write("\n");
        }
        scanKey.close();

        String encrypted = hillEncrypt(plaintextInput, key, keyRowsCols).trim().toLowerCase();

        // finding inverse of key
        int invKey[][] = new int[keyRowsCols][keyRowsCols];
        for (int i = 0; i < keyRowsCols; ++i) {
            for (int j = 0; j < keyRowsCols; ++j) {
                invKey[i][j] = key[i][j];
            }
        }

        if (keyRowsCols == 2) { // specialized function for 2 x 2 matrix
            inverse2by2(key, invKey);
        } else { // for matrices of higer dimensions
            inverse(key, invKey);
        }

        String decrypted = hillEncrypt(encrypted, invKey, keyRowsCols).trim().toLowerCase();

        out.write("encrypted string = " + encrypted + "\n");
        out.write("decrypted string = " + decrypted);

        input.close();
        out.close();
    }
}