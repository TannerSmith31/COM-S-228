package edu.iastate.cs228.hw1;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
/**
 * @author Tanner Smith
 * JUnit for the TownCell Class
 */
class TownCellTest {
	//Test TownCell constructor
	@Test
	void testTownCellConstructor() {
		Town town = new Town(4,4);
		TownCell tCell = new Casual(town, 1,3);
		assertEquals(1, tCell.row);
		assertEquals(3, tCell.col);
		assertEquals(town, tCell.plain);
	}
	
	
	@Test
	void testCensus() {
		Town town = new Town(4,4);
		town.randomInit(10);
		int[] census = new int[5];
		town.grid[1][1].census(census);
		assertEquals(1, census[0]);	//Resellers
		assertEquals(2, census[1]);	//Emptys
		assertEquals(1, census[2]);	//Casuals
		assertEquals(3, census[3]);	//Outages
		assertEquals(1, census[4]);	//Streamers
	}

	
	@Test
	void testStateToNum() {
		Town town = new Town(4,4);
		TownCell tCell = new Casual(town, 3,3);
		TownCell tCell2 = new Streamer(town, 2,2);
		assertEquals(0, tCell.stateToNum(State.RESELLER));
		assertEquals(4, tCell2.stateToNum(State.STREAMER));
	}
}
