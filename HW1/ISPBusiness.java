package edu.iastate.cs228.hw1;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * @author Tanner Smith
 *
 * The ISPBusiness class performs simulation over a grid 
 * plain with cells occupied by different TownCell types.
 *
 */
public class ISPBusiness {
	
	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * @param tOld: old/current Town object.
	 * @return: New town object.
	 */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth());
		for (int row = 0; row < tNew.getLength(); row++) {
			for (int col = 0; col < tNew.getWidth(); col++) {
				//call next cell function from townCell on old town to update the cell to the next thing
				tNew.grid[row][col] = tOld.grid[row][col].next(tNew);
			}
		}
		return tNew;
	}
	
	/**
	 * Returns the profit for the current state in the town grid.
	 * @param town that the simulation is currently on
	 * @return profit for one given month
	 */
	public static int getProfit(Town town) {
		int profit = 0;
		for (int row = 0; row < town.getLength(); row++) {
			for (int col =0; col < town.getWidth(); col++) {
				if (town.grid[row][col].who() == State.CASUAL){
					profit++;
				}
			}
		}
		return profit;
	}

	/**
	 *  Main method. Interact with the user and ask if user wants to specify elements of grid
	 *  via an input file (option: 1) or wants to generate it randomly (option: 2).
	 *  
	 *  Depending on the user choice, create the Town object using respective constructor and
	 *  if user choice is to populate it randomly, then populate the grid here.
	 *  
	 *  Finally: For 12 billing cycle calculate the profit and update town object (for each cycle).
	 *  Print the final profit in terms of %. You should print the profit percentage
	 *  with two digits after the decimal point:  Example if profit is 35.5600004, your output
	 *  should be:
	 *
	 *	35.56%
	 *  
	 * Note that this method does not throw any exception, so you need to handle all the exceptions
	 * in it.
	 * 
	 * @param args
	 */
	public static void main(String []args) {
		int townCreationType = 0;
		boolean hasWorked = false;
		Town simTown = new Town(0,0);	//the town that the simulation will be run on
		Scanner scnr = new Scanner(System.in);
		while (!hasWorked) {
			System.out.println("How to populate grid (type 1 or 2): 1- from a file. 2- randomly with seed");
			try {
				townCreationType = scnr.nextInt();
				//TOWN CREATION TYPE 1
				if (townCreationType == 1) {
					boolean fileNotFound = true;
					while (fileNotFound) {
						System.out.println("Please enter file path:");
						String fileName = scnr.next();
						try {
							simTown = new Town(fileName);	//Do I catch the file not found here?
							fileNotFound = false;
							hasWorked = true;
						} catch (FileNotFoundException e) {
							System.out.println("File not found. Please try again.\n");
						}
					}
				//TOWN CREATION TYPE 2
				} else if (townCreationType == 2) {
					int rows;
					int cols;
					int seed;
					System.out.println("Provide rows, cols and seed integer separated by spaces: ");
					rows = scnr.nextInt(); 	//length
					cols = scnr.nextInt();	//width
					seed = scnr.nextInt();	//seed
					simTown = new Town(rows, cols);	//creates a town with the given rows and columns
					simTown.randomInit(seed);	//uses the given seed to populate the town
					hasWorked = true;
				//INVALID TOWN CREATION TYPE
				} else {
					System.out.println("Invalid entry type!\n");
				}
			} catch (InputMismatchException e) {	//This covers any invalid user inputs
				System.out.println("Invalid Entry Type!\n");
				scnr.next();		//without this, there would be an infinite loop
			}
		}

		scnr.close();
		int totalProfit = 0;
		
		//The initial month of the simulation
		totalProfit += getProfit(simTown);
		
		//this loop will execute 11 times for the 11 months after the first month
		for (int month = 1; month <= 11; month++) {
			simTown = updatePlain(simTown);
			totalProfit += getProfit(simTown);
		}
		
		int totalPossibleProfit = simTown.getLength() * simTown.getWidth() * 12;
		float percentProfit = (totalProfit / (float)totalPossibleProfit) * 100;
		System.out.printf("profit percent = %.2f", percentProfit);
		System.out.print("%");
	}
}
