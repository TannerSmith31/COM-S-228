package edu.iastate.cs228.hw1;

/**
 * @author Tanner Smith
 *Abstract class that holds everything that is in common between all TownCell Types
 *
 */
public abstract class TownCell {

	protected Town plain;
	protected int row;
	protected int col;
	
	
	// constants to be used as indices.
	protected static final int RESELLER = 0;
	protected static final int EMPTY = 1;
	protected static final int CASUAL = 2;
	protected static final int OUTAGE = 3;
	protected static final int STREAMER = 4;
	
	public static final int NUM_CELL_TYPE = 5;
	
	//Use this static array to take census.
	public static final int[] nCensus = new int[NUM_CELL_TYPE];

	public TownCell(Town p, int r, int c) {
		plain = p;
		row = r;
		col = c;
	}
	
	/**
	 * Checks all neigborhood cell types in the neighborhood.
	 * Refer to homework pdf for neighbor definitions (all adjacent
	 * neighbors excluding the center cell).
	 * Use who() method to get who is present in the neighborhood
	 *  
	 * @param counts of all customers
	 */
	public void census(int nCensus[]) {
		// zero the counts of all customers
		nCensus[RESELLER] = 0; 
		nCensus[EMPTY] = 0; 
		nCensus[CASUAL] = 0; 
		nCensus[OUTAGE] = 0; 
		nCensus[STREAMER] = 0; 
		if (row > 0) {
			nCensus[stateToNum(plain.grid[row-1][col].who())] += 1;  //check up
					
			if (col > 0) {
				nCensus[stateToNum(plain.grid[row-1][col-1].who())] += 1;  //check up-left
			}
			if (col < plain.getWidth() - 1) {
				nCensus[stateToNum(plain.grid[row-1][col+1].who())] += 1;  //check up-right
			}
		}
		if (row < plain.getLength() - 1) {
			nCensus[stateToNum(plain.grid[row+1][col].who())] += 1;  //check down
					
			if (col > 0) {
				nCensus[stateToNum(plain.grid[row+1][col-1].who())] += 1;  //check down-left
			}
			if (col < plain.getWidth() - 1) {
				nCensus[stateToNum(plain.grid[row+1][col+1].who())] += 1;  //check down-right
			}
		}
		if (col > 0) {
			nCensus[stateToNum(plain.grid[row][col-1].who())] += 1;	//check left
		}
		if (col < plain.getWidth() - 1) {
			nCensus[stateToNum(plain.grid[row][col+1].who())] += 1;	//check right
		}
	}

	/**
	 * a method that turns a state into a number 0 to 4 depending on their index in nCensus list.
	 * used for the census method because I can't seem to figure out how to turn an enum to an int otherwise
	 * Reseller = 0
	 * Empty = 1
	 * Casual = 2
	 * Outage = 3
	 * Streamer = 4
	 * @param s: a state you would like to turn into its integer index in the census list
	 * @return the nCensus list's index of the state type you used as a parameter
	 */
	public int stateToNum(State s) {
		if (s == State.RESELLER) {
			return RESELLER;
		} else if (s == State.EMPTY) {
			return EMPTY;
		} else if (s == State.CASUAL) {
			return CASUAL;
		} else if (s == State.OUTAGE) {
			return OUTAGE;
		} else {
			return STREAMER;
		}
	}
	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	public abstract State who();

	/**
	 * Determines the cell type in the next cycle.
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	public abstract TownCell next(Town tNew);
}
