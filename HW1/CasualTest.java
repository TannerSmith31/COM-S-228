package edu.iastate.cs228.hw1;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * @author Tanner Smith
 *JUnit to test the Casual Class
 */
public class CasualTest {
	@Test
	void whoTest() {
		Town T = new Town(10,10);
		TownCell C = new Casual(T, 2, 3);
		T.grid[4][5] = new Casual(T, 4, 5);
		assertEquals(State.CASUAL, C.who());
		assertEquals(State.CASUAL, T.grid[4][5].who());
	}
	
	@Test
	void nextTest() {
		Town tNew = new Town(4,4);
		tNew.randomInit(10);
		assertEquals(State.CASUAL, tNew.grid[1][2].who());
		assertEquals(State.OUTAGE, tNew.grid[1][2].next(tNew).who());
		
		tNew.grid[2][0] = new Casual(tNew, 2, 0);
		assertEquals(State.CASUAL, tNew.grid[2][0].who());
		assertEquals(State.STREAMER, tNew.grid[2][0].next(tNew).who());
	}
}
