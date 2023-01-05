package edu.iastate.cs228.hw1;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
/**
 * @author Tanner Smith
 * JUnit to test the Streamer Class
 */
public class StreamerTest {
	@Test
	void whoTest() {
		Town T = new Town(10,10);
		TownCell S = new Streamer(T, 2, 3);
		T.grid[4][5] = new Streamer(T, 4, 5);
		assertEquals(State.STREAMER, S.who());
		assertEquals(State.STREAMER, T.grid[4][5].who());
	}
	
	@Test
	void nextTest() {
		Town tNew = new Town(4,4);
		tNew.randomInit(10);
		assertEquals(State.STREAMER, tNew.grid[2][3].who());
		assertEquals(State.OUTAGE, tNew.grid[2][3].next(tNew).who());
		
		tNew.grid[2][0] = new Streamer(tNew, 2, 0);
		assertEquals(State.STREAMER, tNew.grid[2][0].who());
		assertEquals(State.EMPTY, tNew.grid[2][0].next(tNew).who());
	}
}
