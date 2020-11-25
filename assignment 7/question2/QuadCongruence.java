import java.io.*;
import java.util.*;
import java.util.regex.*;


public class Solution {
    
    // Function to calculate (x^y)%p in O(log y) 
    static int power(int x, int y, int p) 
    { 
        // Initialize result 
        int res = 1;      
         
        // Update x if it is more   
        // than or equal to p 
        x = x % p;  
  
       if (x == 0) return 0; // In case x is divisible by p;    
  
        while (y > 0) 
        { 
            // If y is odd, multiply x 
            // with result 
            if((y & 1)==1) 
                res = (res * x) % p; 
      
            // y must be even now 
            // y = y / 2 
            y = y >> 1;  
            x = (x * x) % p;  
        } 
        return res; 
    } 
	// Function to return modulo inverse of a with respect to m using extended Euclid Algorithm 
    static int modInverse(int a, int m) 
    { 
        int m0 = m; 
        int y = 0, x = 1; 
  
        if (m == 1) 
            return 0; 
  
        while (a > 1) 
        { 
            // q is quotient 
            int q = a / m; 
  
            int t = m; 
  
            // m is remainder now, process 
            // same as Euclid's algo 
            m = a % m; 
            a = t; 
            t = y; 
  
            // Update x and y 
            y = x - q * y; 
            x = t; 
        } 
  
        // Make x positive 
        if (x < 0) 
            x += m0; 
  
        return x; 
    } 
    //Function to calculate gcd of two numbers
    static int gcd(int a,int b){
        if(b==0)
            return a;
        else
            return gcd(b,a%b);
    }
    //Function to calculate get prime factors of number
    static List<Integer> getPrimeFactors(int n) {
        List <Integer> res = new ArrayList<>();
        for (int i = 2; i <= n; ++i) {
            if (n % i == 0) {
                res.add(i);
            }
            while(n % i == 0) {
                n /= i;
            }
        }
        return res;
    }

	public static void main(String[] args) throws IOException {
        File readfile = new File("Roshan.txt"); //Input file to read from
        if (readfile.exists()) {
            File writefile = new File("Shaw.txt"); //Output file to write on
            FileWriter myWriter= new FileWriter(writefile);
            Scanner myReader = new Scanner(readfile);
            while (myReader.hasNextLine()) {//Reading the file line by line
                String modExp = myReader.nextLine().toString();
                Pattern p = Pattern.compile("(x2\\s*=\\s*)(?<A>[0-9]+)(\\s*\\(\\s*mod\\s*)(?<N>[0-9]+)(\\s*\\))");
                Matcher m = p.matcher(modExp);//Extracting pattern from input
                int a = 0,  n = 0;
                if (m.find()) {
                    a = Integer.parseInt(m.group("A"));
                    n = Integer.parseInt(m.group("N"));
                }
                List <Integer> pFactors = getPrimeFactors(n); //Calculating prime factors
                List<List<Integer>> ans = new ArrayList<>();
                //Checking the prime factors based on euler's criterion
                for (int i: pFactors) {
                    int ni = i;
                    int a1 = power(a, (ni + 1) / 4, ni);
                    int a2 = -1 * a1;

                    List<Integer> ansi = new ArrayList<>();
                    
                    if (power(a, (ni - 1) / 2, ni) == 1) {
                        ansi.add(a1);
                        ansi.add(a2);
                    }
                    if(ansi.size()!=2){
                        ans.clear();
                        break;
                    } 
                    ansi.add(ni);
                    ans.add(ansi);
                }
                myWriter.write("Solution for the given equation x2 = " + a + "(mod " + n + ")\n");
                //Writing the solutions to the output file
                if(ans.isEmpty()){
                    myWriter.write("No Solution exists\n");
                }
                else if(ans.size()==1){
                    int a1=ans.get(0).get(0);
                    int a2=ans.get(0).get(1);
                    int an=ans.get(0).get(2);
                    myWriter.write("x = "+a1+" (mod "+an+") and "+"x = "+a2+" (mod "+an+")"+"\n"); 
                }
                else{
                    int solno=1;
                    for(int i=0;i<ans.size();i++){
                        for(int j=i+1;j<ans.size();j++){
                            int a1=ans.get(i).get(0);
                            int a2=ans.get(i).get(1);
                            int an=ans.get(i).get(2);
                            int b1=ans.get(j).get(0);
                            int b2=ans.get(j).get(1);
                            int bn=ans.get(j).get(2);
                            myWriter.write("Solution:"+solno+++"\n");
                            myWriter.write("x = "+a1+" (mod "+an+") and "+"x = "+b1+" (mod "+bn+")"+"\n"); 
                            myWriter.write("Solution:"+solno+++"\n");
                            myWriter.write("x = "+a1+" (mod "+an+") and "+"x = "+b2+" (mod "+bn+")"+"\n"); 
                            myWriter.write("Solution:"+solno+++"\n");
                            myWriter.write("x = "+a2+" (mod "+an+") and "+"x = "+b1+" (mod "+bn+")"+"\n");
                            myWriter.write("Solution:"+solno+++"\n");
                            myWriter.write("x = "+a2+" (mod "+an+") and "+"x = "+b2+" (mod "+bn+")"+"\n");
                        }
                    }
                }
            }
            myWriter.close();
            myReader.close();
        } else {
                System.out.println("The file does not exist.");
        }
    }
        
}
