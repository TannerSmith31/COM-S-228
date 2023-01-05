package edu.iastate.cs228.hw1;
/**
 * @author Tanner Smith
 * Casual focused subclass of TownCell
 */
public class Casual extends TownCell{

	/**
	 * Passes all arguments to the abstract TownCell constructor
	 * @param p: a town to hold this cell
	 * @param r: the row this cell will reside
	 * @param c: the column this cell will reside
	 */
	public Casual(Town p, int r, int c) {
		super(p, r, c);
	}
	
	/**
	 * Gets the identity of this cell.(Casual)
	 * @return State
	 */
	@Override
	public State who() {
		State state = State.CASUAL;
		return state;
	}

	/**
	 * Determines the cell type in the next cycle.
	 * If there is less than one empty and/or Outage in the area, it becomes a reseller
	 * else if there are any resellers in the area, it becomes an outage
	 * else if there are any streamers in the area or greater than 5 casuas, it becomes a streamer
	 * else it stays a casual
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	@Override
	public TownCell next(Town tNew) {
		this.census(nCensus);
		if (nCensus[1] + nCensus[3] <= 1) {					//if there is less than 1 empty and/or outage in area
			return new Reseller(tNew, row, col);				//becomes a reseller
		} else if(nCensus[0] > 0) {							//else if there is a reseller in the area
			return new Outage(tNew, row, col);					//becomes an outage
		} else if ((nCensus[4] > 0) || (nCensus[2] >= 5)) { //if there are any streamers in the area or greater than 5 casuals
			return new Streamer(tNew, row, col);				//becomes a streamer
		}else {												//else
			return new Casual(tNew, row, col);					//becomes a casual
		}
	}
}
