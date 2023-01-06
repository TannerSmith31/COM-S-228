package edu.iastate.cs228.hw4;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A class that gets the decoder tree information and the ciphertext from a file
 * @author Tanner Smith
 */
public class CipherInfo {
	//instance variables
	private String decoder;		//the string pre-order version of the cipherTree
	private String cipherText;	//the string of 1's and 0's
	
	/**
	 * Constructs a CipherInfo object that takes the text from the given file and 
	 * stores a string of the decoder (the tree), and a string of the cipherText (the 0's and 1's)
	 * @param file : the file that you are getting the decoder and cipherText from
	 * @throws FileNotFoundException : if the input file doesn't exist
	 */
	public CipherInfo(File file) throws FileNotFoundException{
		decoder = "";
		cipherText = "";
		Scanner fileScanner = new Scanner(file);
		String curLine;
		char curChar;
		curLine = fileScanner.nextLine();
		//first line always has to be the decoder
		for (int i = 0; i<curLine.length();i++) {
			curChar = curLine.charAt(i);
			decoder += curChar;
		}
		while (fileScanner.hasNextLine()) {
			curLine = fileScanner.nextLine();
			if (curLine.charAt(0) != '1' && curLine.charAt(0) != '0') { //meaning the current line is a continuation of the decoder
				decoder += '\n';	//the reason the decoder would go to a second line is if there was a newLine character
				for (int i = 0; i<curLine.length();i++) {
					curChar = curLine.charAt(i);
					decoder += curChar;
				}
			}
			else {//the current line is part of the cipher text
				for (int i = 0; i<curLine.length();i++) {
					curChar = curLine.charAt(i);
					cipherText += curChar;
				}
			}
		}
		fileScanner.close();
	}
	
	/**
	 * returns the Decoder cipherTree string stored in this object
	 * @return decoder string
	 */
	public String getDecoder() {
		return decoder;
	}
	
	/**
	 * returns the ciphertext string (lots of 0's and 1's) stored in this object
	 * @return ciphertext
	 */
	public String getCipherText() {
		return cipherText;
	}
}
