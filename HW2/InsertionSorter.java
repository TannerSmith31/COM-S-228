package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;


/**
 *  
 * @author Tanner Smith
 * This class utilizes insertion sort to sort an array of points
 */

public class InsertionSorter extends AbstractSorter 
{
	
	/**
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 * 
	 * @param pts  
	 */
	public InsertionSorter(Point[] pts) 
	{
		super(pts);
		super.algorithm = "Insertion sort";
	}	

	
	/** 
	 * Perform insertion sort on the array points[] of the parent class AbstractSorter.  
	 */
	@Override 
	public void sort()
	{
		int length = points.length;
		for (int i = 1; i< length; i++) {
			Point temp = points[i];
			int j = i - 1;
			while (j >= 0 && pointComparator.compare(points[j], temp) > 0) {
				points[j+1] = points[j];
				j--;
			}
			points[j+1] = temp;
		}
	}		
}
