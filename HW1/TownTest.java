package edu.iastate.cs228.hw1;
import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;
/**
 * @author Tanner Smith
 * JUnit to test Town Class
 */
public class TownTest {
	
	@Test
	void testTownConstructor1() {	//This also tests getLength() and getWidth() functions
		Town T = new Town(69,420);
		assertEquals(69, T.getLength());
		assertEquals(420, T.getWidth());
	}
	
	@Test
	void testTownConstructor2() {
		Town T = new Town(1,1);
		try{
			T = new Town("ISP4x4.txt");
		} catch(FileNotFoundException e) {
			
		}
		assertEquals(4, T.getLength());
		assertEquals(4, T.getWidth());
		assertEquals(State.EMPTY,T.grid[1][1].who());
		assertEquals(State.STREAMER, T.grid[2][3].who());
		assertEquals(State.OUTAGE, T.grid[2][2].who());
	}
	
	@Test
	void testTownConstructor3() {
		Town T = new Town(4,4);
		T.randomInit(10);
		assertEquals(4, T.getLength());
		assertEquals(4, T.getWidth());
		assertEquals(State.EMPTY,T.grid[1][1].who());
		assertEquals(State.STREAMER, T.grid[2][3].who());
		assertEquals(State.OUTAGE, T.grid[2][2].who());
	}
	
	@Test
	void testTownToString() {
		Town T = new Town(4,4);
		T.randomInit(10);
		assertEquals("O R O R\nE E C O\nE S O S\nE O R R", T.toString());
	}
	
}
