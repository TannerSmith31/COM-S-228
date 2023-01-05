package edu.iastate.cs228.hw1;
/**
 * @author Tanner Smith
 * Empty focused subclass of TownCell
 */
public class Empty extends TownCell{

	/**
	 * Passes all arguments to the abstract TownCell constructor
	 * @param p: a town to hold this cell
	 * @param r: the row this cell will reside
	 * @param c: the column this cell will reside
	 */
	public Empty(Town p, int r, int c) {
		super(p, r, c);
	}
	
	/**
	 * Gets the identity of this cell. (Empty)
	 * @return State
	 */
	@Override
	public State who() {
		State state = State.EMPTY;
		return state;
	}

	/**
	 * Determines the cell type in the next cycle.
	 * If there is less than one Empty and/or Outage in the area, it becomes a reseller
	 * Else it becomes a casual user
	 * (NOTE: the 5 or more casual neighbors causing a streamer rule apparently doesn't apply here)
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	@Override
	public TownCell next(Town tNew) {
		this.census(nCensus);
		if (nCensus[1] + nCensus[3] <= 1) {			//if there is less than one Empty and/or Outage
			return new Reseller(tNew, row, col);		//becomes a reseller
		} else {									//else
			return new Casual(tNew, row, col);			//becomes a casual
		}
	}

}
