package edu.iastate.cs228.hw1;
import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;

/**
 * @author Tanner Smith
 * JUnit to test the ISPBusiness class
 */
public class ISPBusinessTest {
	
	@Test
	void updatePlainTest() {
		Town T = new Town(4, 4);
		T.randomInit(10);
		T = ISPBusiness.updatePlain(T);
		Town tReal_1 = new Town(4,4);	//Constructs basic town here to avoid errors
		Town tReal_11 = new Town(4,4);	//Constructs basic town here to avoid errors
		try{
			tReal_1 = new Town("ISP4x4-1.txt");		//Constructs a town based on what T should be after 1 iteration
			tReal_11 = new Town("ISP4x4-11.txt");	//Constructs a town based on what T should be after 11 iterations
			
		} catch (FileNotFoundException e){
			//nothing. Never executes
		}
		//Comparing 1 use of UpdatePlain
		assertEquals(tReal_1.toString(), T.toString()); //I have to compare strings so it knows what "same towns" are.
		
		//Upating plain 10 more times
		for (int i = 1; i <11; i++) {
			T = ISPBusiness.updatePlain(T);
		}
		//Comparing 11 uses of Update Plain
		assertEquals(tReal_11.toString(), T.toString());
		
		
		
	}
	@Test
	void getProfitTest() {
		//Tests the profit from the first 4 iterations of provided Example
		Town T = new Town(4,4);
		T.randomInit(10);
		assertEquals(1, ISPBusiness.getProfit(T));
		T = ISPBusiness.updatePlain(T);
		assertEquals(4, ISPBusiness.getProfit(T));
		T = ISPBusiness.updatePlain(T);
		assertEquals(12, ISPBusiness.getProfit(T));
		
	}
}
