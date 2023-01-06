package edu.iastate.cs228.hw4;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This is the main class for the deciphering process. It asks the user for a file to decode
 * and then it prints out the letters and their codes as well as the decoded message
 * @author Tanner Smith
 */
public class cipherMain {
	public static void main(String []args) {
		String filename;
		File cipherFile;
		String decoder;
		String cipherText;
		Scanner usrInput = new Scanner(System.in);
		
		System.out.print("Please enter a filename to decode: ");
		filename = usrInput.next();
		cipherFile = new File(filename);
		
		try {
			CipherInfo cipherInfo = new CipherInfo(cipherFile);
			decoder = cipherInfo.getDecoder();
			cipherText = cipherInfo.getCipherText();
			CipherTree cipher = new CipherTree(decoder);
			CipherTree.printCodes(cipher, "");
			System.out.println();
			System.out.println("MESSAGE:");
			cipher.decode(cipher, cipherText);
		}catch(FileNotFoundException e) {
			System.out.print("ERROR: File not Found");
		}
		
		
		
		usrInput.close();
	}
}
