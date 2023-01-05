package edu.iastate.cs228.hw1;
/**
 * @author Tanner Smith
 * Outage focused subclass of TownCell
 */
public class Outage extends TownCell{

	/**
	 * Passes all arguments to the abstract TownCell constructor
	 * @param p: a town to hold this cell
	 * @param r: the row this cell will reside
	 * @param c: the column this cell will reside
	 */
	public Outage(Town p, int r, int c) {
		super(p, r, c);
	}
	
	/**
	 * Gets the identity of this cell (Outage).
	 * @return State
	 */
	@Override
	public State who() {
		State state = State.OUTAGE;
		return state;
	}

	/**
	 * Determines the cell type in the next cycle.
	 * Outage cells always become empty upon the next cycle
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	@Override
	public TownCell next(Town tNew) {
		return new Empty(tNew, row, col);
	}
}
