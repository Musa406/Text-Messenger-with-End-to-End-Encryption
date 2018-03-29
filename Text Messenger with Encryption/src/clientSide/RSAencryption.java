package clientSide;

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;



public class RSAencryption

{

  private BigInteger p ,q, N, phi, e, d;

  private Random random;
   int bitlength = 1024;

 

   public RSAencryption()

   {
       random = new Random();

       p = BigInteger.probablePrime(bitlength, random);

       q = BigInteger.probablePrime(bitlength, random);

       N = p.multiply(q);
       
       
       //key generation............................................................................

       phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

       e = BigInteger.probablePrime(bitlength / 2, random);

       while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0)

       {

           e.add(BigInteger.ONE);

       }

       d = e.modInverse(phi);
       
       //..................................................................................................

   }


   public RSAencryption(BigInteger e, BigInteger d, BigInteger N)
   {
       this.e = e;

       this.d = d;

       this.N = N;
   }

   public static void main(String[] args) throws IOException

   {

       RSAencryption rsa = new RSAencryption();

       DataInputStream in = new DataInputStream(System.in);

       String teststring;

       System.out.println("Enter the plain text:");

       teststring = in.readLine();
       System.out.println(teststring);

       //plaintex value in byte: 
       System.out.println("plainText in byte : "  + bytesToString(teststring.getBytes()));
     
       // encrypt.........
       byte[] encrypted = rsa.rsaEncryption(teststring.getBytes());
       System.out.println("Encrypted value in byte: "+ encrypted);

       // decrypt.........
       byte[] decrypted = rsa.rsaDecryption(encrypted);

       System.out.println("Decrypted String: " + new String(decrypted));

   }



   private static String bytesToString(byte[] encrypted)
   {
	   
       String test = "";

       for (byte b : encrypted)
       {

           test += Byte.toString(b);
       }

       return test;
   }



  

   public byte[] rsaEncryption(byte[] message)

   {
       byte []cipherText = (new BigInteger(message)).modPow(e, N).toByteArray();
       
       return cipherText;

   }



   // Decrypt message

   public byte[] rsaDecryption(byte[] message)

   {

       byte [] plainText =  (new BigInteger(message)).modPow(d, N).toByteArray();
       
       return plainText;

   }

}