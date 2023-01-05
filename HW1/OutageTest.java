package edu.iastate.cs228.hw1;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
/**
 * @author Tanner Smith
 *JUnit for the Outage Class
 */
public class OutageTest {
	@Test
	void whoTest() {
		Town T = new Town(10,10);
		TownCell O = new Outage(T, 2, 3);
		T.grid[4][5] = new Outage(T, 4, 5);
		assertEquals(State.OUTAGE, O.who());
		assertEquals(State.OUTAGE, T.grid[4][5].who());
	}
	
	@Test
	void nextTest() {
		Town tNew = new Town(4,4);
		tNew.randomInit(10);
		assertEquals(State.OUTAGE, tNew.grid[0][0].who());
		assertEquals(State.EMPTY, tNew.grid[0][0].next(tNew).who());
		
		assertEquals(State.OUTAGE, tNew.grid[1][3].who());
		assertEquals(State.EMPTY, tNew.grid[1][3].next(tNew).who());
	}
}
