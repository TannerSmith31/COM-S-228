package edu.iastate.cs228.hw1;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * @author Tanner Smith
 *JUnit to test the Empty Class
 */
public class EmptyTest {
	@Test
	void whoTest() {
		Town T = new Town(10,10);
		TownCell E = new Empty(T, 2, 3);
		T.grid[4][5] = new Empty(T, 4, 5);
		assertEquals(State.EMPTY, E.who());
		assertEquals(State.EMPTY, T.grid[4][5].who());
	}
	
	@Test
	void nextTest() {
		Town tNew = new Town(4,4);
		tNew.randomInit(10);
		assertEquals(State.EMPTY, tNew.grid[1][1].who());
		assertEquals(State.CASUAL, tNew.grid[1][1].next(tNew).who());
		
		assertEquals(State.EMPTY, tNew.grid[3][0].who());
		assertEquals(State.CASUAL, tNew.grid[3][0].next(tNew).who());	
	}
}
