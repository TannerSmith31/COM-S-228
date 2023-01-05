package edu.iastate.cs228.hw1;
/**
 * @author Tanner Smith
 * Streamer focused subclass of TownCell
 */
public class Streamer extends TownCell{

	/**
	 * Passes all arguments to the abstract TownCell constructor
	 * @param p: a town to hold this cell
	 * @param r: the row this cell will reside
	 * @param c: the column this cell will reside
	 */
	public Streamer(Town p, int r, int c) {
		super(p, r, c);
	}
	
	/**
	 * Gets the identity of this cell. (Streamer)
	 * @return State
	 */
	@Override
	public State who() {
		State state = State.STREAMER;
		return state;
	}

	/**
	 * Determines the cell type in the next cycle.
	 * If there is less than 1 Empty and/or Outage in the area it becomes a reseller
	 * Else if there is any reseller in the neighborhood it becomes an outage
	 * Else if there is an outage in the area it becomes an empty
	 * Else it stays a streamer
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	@Override
	public TownCell next(Town tNew){
		this.census(nCensus);
		if (nCensus[1] + nCensus[3] <= 1) {
			return new Reseller(tNew, row, col);
		}else if(nCensus[0] > 0) {
			return new Outage(tNew, row, col);
		} else if (nCensus[3] > 0) {
			return new Empty(tNew, row, col);
		} else {
			return new Streamer(tNew, row, col);
		}
	}

}
