package edu.iastate.cs228.hw1;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
/**
 * @author Tanner Smith
 *JUnit to test the Reseller Class
 */
public class ResellerTest {
	
	@Test
	void whoTest() {
		Town T = new Town(10,10);
		TownCell R = new Reseller(T, 2, 3);
		T.grid[4][5] = new Reseller(T, 4, 5);
		assertEquals(State.RESELLER, R.who());
		assertEquals(State.RESELLER, T.grid[4][5].who());
	}
	
	@Test
	void nextTest() {
		Town tNew = new Town(4,4);
		tNew.randomInit(10);
		assertEquals(State.RESELLER, tNew.grid[0][1].who());
		assertEquals(State.EMPTY, tNew.grid[0][1].next(tNew).who());
		
		assertEquals(State.RESELLER, tNew.grid[3][3].who());
		assertEquals(State.EMPTY, tNew.grid[0][1].next(tNew).who());	
	}
}
