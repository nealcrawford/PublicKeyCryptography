/**
 * Created by Neal on 11/28/2014.
 */
import java.util.*;
public class Tester {
    static long p = 13; //43853; Large prime example
    static long q = 97; //68209;
    static long max = p * q;
    static long z = (p - 1) * (q - 1);

    public static void main(String args[]) {
        checkIfRunnable();

        System.out.println("Creating public and private keys...");
        MakeK kVal = new MakeK();                       //Make object with public key generation method
        long k = kVal.k(z);                             //Generate public key
        System.out.println("Public: " + k);
        System.out.println("Private: " + kVal.privateKey);

        System.out.print("Enter your message: ");       //Prompt user for message to be encrypted
        Scanner input = new Scanner(System.in);
        long[] codedMsg = encrypt(k, input.nextLine()); //Create array of encrypted rawMsg

        System.out.print("Decrypt using private key, or factor max? "); //Prompt user to decrypt with private key or prime factorization
        while (decryptProcess(input.nextLine().toLowerCase(), kVal.privateKey, codedMsg));
    }

    public static long[] encrypt(long pub, String msg) { //Encrypts the ascii code of each character in input
        long[] rawMsg = new long[msg.length()];          //Array rawMsg is the user inputted message
        char x;
        for (int i = 0; i < (msg.length()); i++) {       //Set array rawMsg to ascii codes of the input
            x = msg.charAt(i);
            rawMsg[i] = x;
        }
        System.out.println();
        System.out.println("Encrypting...");
        long ascii;
        System.out.print("[");
        for (int i = 0; i < (rawMsg.length); i++) {     //For every character in the message array
            ascii = rawMsg[i];
            for (int j = 0; j < pub-1; j++) {           //Multiply ascii by itself (public key) times
                rawMsg[i] *= ascii;
                if (rawMsg[i] > max) {                  //If ascii is larger than max, take remainder using max
                    rawMsg[i] %= max;
                }
            }
            System.out.print("=");
        }
        System.out.println(">] ...done");
        System.out.println();
        return rawMsg;    //return the encrypted array
    }

    public static boolean decryptProcess(String decryptMethod, long priv, long[] codedMsg) {
        System.out.println();
        if (decryptMethod.equals("key")) {
            System.out.println(decrypt(priv, codedMsg));
            return false;
        }
        else if (decryptMethod.equals("factor")) {
            FactorPrimes factor = new FactorPrimes();
            long primeFactor = factor.factorMax();
            long q = max / primeFactor;
            long solvedZ = (primeFactor - 1) * (q - 1);
            MakeK newK = new MakeK();
            long solvedK = newK.k(solvedZ);
            System.out.println("The following numbers were solved using the public maximum: ");
            System.out.println("The two primes are " + primeFactor + " and " + q);
            System.out.println("Public: " + solvedK);
            System.out.println("Private: " + newK.privateKey);
            System.out.println("Intercepted Message: " + decrypt(newK.privateKey, codedMsg));
            return false;
        }
        else {
            System.out.println("I didn't catch that, please try again.");
            return true;
        }
    }

    public static String decrypt(long priv, long[] codedMsg) { //Decrypts the encrypted array using private key
        char letter;
        String decryptedString = "";
        System.out.println();
        System.out.println("Decrypting...");
        long ascii;
        System.out.print("[");
        for (int i = 0; i < codedMsg.length; i++) {            //For every element in the codedMsg array
            ascii = codedMsg[i];
            for (int j = 0; j < priv-1; j++) {                 //Multiply (coded ascii) by itself (private key) times
                codedMsg[i] *= ascii;
                if (codedMsg[i] > max) {                       //If (coded ascii) is larger than max, take remainder using max
                    codedMsg[i] %= max;
                }
            }
            System.out.print("=");
        }
        System.out.println(">] ...done");
        System.out.println();
        for (int i = 0; i < (codedMsg.length); i++) {          //Get character from ascii code
            letter = (char)codedMsg[i];
            decryptedString += letter;
        }
        return decryptedString;                                //Return the decrypted string
    }

    public static void checkIfRunnable() {                     //Checks if p and q are prime, max within width of Long and above 127
        PrimeGen PrimesChecker = new PrimeGen();
        if (!PrimesChecker.isPrime(p)) {
            System.out.println("p must be prime");
            System.exit(0);
        }

        else if (!PrimesChecker.isPrime(q)) {
            System.out.println("q must be prime");
            System.exit(0);
        }

        else if (max < 127) {
            System.out.println("max too small");
            System.exit(0);
        }

        else if (max >= Long.MAX_VALUE) {
            System.out.println("max too large");
            System.exit(0);
        }
    }
}
