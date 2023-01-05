package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


/**
 * @author Tanner Smith
 * A class to deal with any Town related information
 */
public class Town {
	
	private int length, width;  //Row and col (first and second indices)
	public TownCell[][] grid;	//grid to store all of the town cells in order
	
	/**
	 * Constructor to be used when user wants to generate grid randomly, with the given seed.
	 * This constructor does not populate each cell of the grid (but should assign a 2D array to it).
	 * @param length assigns the length of the town
	 * @param width assigns the width of the town
	 */
	public Town(int length, int width) {
		this.length = length;
		this.width = width;
		grid = new TownCell[length][width];
	}
	
	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simple throws FileNotFoundException exception instead of catching it.
	 * Ensure that you close any resources (like file or scanner) which is opened in this function.
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException {
		File userFile = new File(inputFileName);
		Scanner fileScanner = new Scanner(userFile);	//a scanner that will read the file
		String currentLine = fileScanner.nextLine();	//assigning the first line in the file as the current line
		Scanner lineScanner = new Scanner(currentLine);	//a scanner that will be given a line in the file to read
		//Taking the number of rows and columns from the first line in the text document and creating grid
		length = lineScanner.nextInt();
		width = lineScanner.nextInt();
		grid = new TownCell[length][width];
		//Cycle through the rows and columns and put their contents into a grid
		for(int row = 0; row < length; row++) {
			currentLine = fileScanner.nextLine();		//updates the current line 
			lineScanner = new Scanner(currentLine);		//puts the updated current file line in to the line scanner
			for(int col = 0; col < width; col++) {
				String cellType = lineScanner.next();
				if (cellType.equals("R")) {
					grid[row][col] = new Reseller(this, row, col);
				}else if (cellType.equals("E")) {
					grid[row][col] = new Empty(this, row, col);
				}else if (cellType.equals("C")) {
					grid[row][col] = new Casual(this, row, col);
				}else if (cellType.equals("O")) {
					grid[row][col] = new Outage(this, row, col);
				}else {
					grid[row][col] = new Streamer(this, row, col);
				}
			}
		}
		lineScanner.close();
		fileScanner.close();
	}
	
	/**
	 * Returns width of the grid.
	 * @return grid width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns length of the grid.
	 * @return grid length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following class object:
	 * Casual, Empty, Outage, Reseller OR Streamer
	 * @param seed: the seed that will be used to instantiate a random object
	 */
	public void randomInit(int seed) {
		Random rand = new Random(seed);
		//TODO: DONE Write your code here.
		for (int row = 0; row < length; row++) {
			for (int col = 0; col < width; col++) {
				int randNum = rand.nextInt(5) + 1; //produces random number from 1 to 5
				if (randNum == 1) {
					grid[row][col] = new Reseller(this, row, col);
				}else if (randNum == 2) {
					grid[row][col] = new Empty(this, row, col);
				}else if (randNum == 3) {
					grid[row][col] = new Casual(this, row, col);
				}else if (randNum == 4) {
					grid[row][col] = new Outage(this, row, col);
				}else if (randNum == 5) {
					grid[row][col] = new Streamer(this, row, col);
				}
			}
		}
		
	}
	
	/**
	 * Output the town grid. For each square, output the first letter of the cell type.
	 * Each letter should be separated either by a single space or a tab.
	 * And each row should be in a new line. There should not be any extra line between 
	 * the rows.
	 */
	@Override
	public String toString() {
		String s = "";
		//TODO: Write your code here.
		for (int row = 0; row < length; row++) {
			for (int col = 0; col < width; col++) {
				if (grid[row][col].who() == State.RESELLER) {
					s += 'R';
				}else if (grid[row][col].who() == State.EMPTY) {
					s += 'E';
				}else if (grid[row][col].who() == State.CASUAL) {
					s+= 'C';
				}else if (grid[row][col].who() == State.OUTAGE) {
					s+= 'O';
				}else {
					s+= 'S';
				}
			if (col != width -1) { //makes sure there isn't a space after the last letter in a line
				s += ' ';
			}
			}
			if (row != length -1) { //makes sure there isn't a new line after the final row
				s += '\n';
			}
		}
		return s;
	}
}
