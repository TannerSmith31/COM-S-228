package edu.iastate.cs228.hw1;

/**
 * @author Tanner Smith
 * Reseller focused subclass of TownCell
 */
public class Reseller extends TownCell{

	/**
	 * Passes all arguments to the abstract TownCell constructor
	 * @param p: a town to hold this cell
	 * @param r: the row this cell will reside
	 * @param c: the column this cell will reside
	 */
	public Reseller(Town p, int r, int c) {
		super(p, r, c);
	}
	
	/**
	 * Gets the identity of this cell. (Reseller)
	 * @return State
	 */
	@Override
	public State who() {
		State state = State.RESELLER;
		return state;
	}

	/**
	 * Determines the cell type in the next cycle.
	 * if 3 or fewer casual users OR 3 or more empty cells in the area, changes to empty
	 * else if there are 5 or more casuals in the neighborhood it becomes a streamer
	 * Otherwise it stays a reseller
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	@Override
	public TownCell next(Town tNew) {//what is tNew? should I replace the "this" in the next line with tNew?
		this.census(nCensus);
		if (nCensus[2] <= 3 || nCensus[1] >= 3) {	//if 3 or fewer casuals OR 3 or more empties
			return new Empty(tNew, row, col);			//becomes Empty
		} else if (nCensus[2] >= 5) {				//else if there are 5 or more casuals in the neighborhood
			return new Streamer(tNew, row, col);		//becomes Streamer
		}else {										//Otherwise
			return new Reseller(tNew, row, col);		//stays a reseller
		}
	}

}
