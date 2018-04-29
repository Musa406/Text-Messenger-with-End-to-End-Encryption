package clientSide;



import java.math.BigInteger;

import java.util.Random;



public class RSAencryption {

private BigInteger p;

private BigInteger q;

private BigInteger n;

private BigInteger phi;

private BigInteger e;

private BigInteger d;

private int bitlength = 1024;

private Random     r;

public RSAencryption()

{
    r = new Random();

    p = BigInteger.probablePrime(bitlength, r); // p = big prime

    q = BigInteger.probablePrime(bitlength, r); // q = big prime

    n = p.multiply(q); // n = p*q

    phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)); //phi = (p-1)*(q-1)

    
    //key generation..........................................
    
    e = BigInteger.probablePrime(bitlength / 2, r); // e = low number
    
    while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) //finding  e

    {

        e.add(BigInteger.ONE); //e++

    }
 
    d = e.modInverse(phi); //d = e^-1(mod n)
 
    //..........................................................
}

public RSAencryption(BigInteger e, BigInteger d, BigInteger n)
{
    this.e = e;

    this.d = d;

    this.n = n;
}

	//encryption

	public String encrypt(String message, String strE, String strN)
	{
		byte [] messageByte = message.getBytes();
		
		BigInteger messageInt = new BigInteger(messageByte);
		
		BigInteger keyE = new BigInteger(strE);
		BigInteger keyN = new BigInteger(strN);
		
		BigInteger cipherText = messageInt.modPow(keyE ,  keyN); // (PlainText^e) mod n
		
		String cipherData = ""+cipherText;
		
		return cipherData;
	}
	


    //decryption
	
	public String decrypt(String message)
	
	{
		BigInteger messageInt = new BigInteger(message);
		
		BigInteger cipherText = messageInt.modPow(d ,  n); // (CipherText^e) mod n
		
		String plainText = new String(cipherText.toByteArray());
		
		return plainText;
	}
	
	
	
	public String getPublicKey()
	{
		String E = ""+e;
		String N = ""+n;
		
		String publicKey = E+"               "+N;
		
		return publicKey;
	}

}

