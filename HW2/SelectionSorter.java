package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;


/**
 *  
 * @author Tanner Smith
 * This class uses the selection sort method to sort points. 
 * 
 */

public class SelectionSorter extends AbstractSorter
{
	
	/**
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts  
	 */
	public SelectionSorter(Point[] pts)  
	{
		super(pts);
		super.algorithm = "Selection sort"; //Algorithm.SelectionSort;
	}	

	
	/** 
	 * Apply selection sort on the array points[] of the parent class AbstractSorter.  
	 * 
	 */
	@Override 
	public void sort()
	{
		for (int i = 0; i < points.length - 1; i++)  
        {  
            int minValIndex = i;  
            for (int j = i + 1; j < points.length; j++){  
                if (pointComparator.compare(points[j], points[minValIndex]) < 0){  
                    minValIndex = j;
                }  
            }  
            swap(i, minValIndex);
        }  
		
	}	
}
