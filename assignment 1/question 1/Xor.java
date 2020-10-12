import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Xor {
    public static void main(String args[]) throws IOException {
        try {
            FileReader input = new FileReader("./Harsh.txt");
            FileWriter output = new FileWriter("./Srivastava.txt");
            String res = "";
            while(input.ready()) {
                int c = input.read();
                output.write("XOR of character " + (char)c + " with 127 = " + (c ^ 127) + "\n");
                res += (char)(c ^ 127);
            }
            output.write("\nEncrypted String = " + res);
            input.close();
            output.close();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}