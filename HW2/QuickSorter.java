package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;


/**
 *  
 * @author Tanner Smith
 * This class uses the QuickSort algorithm to sort an array of points
 */

public class QuickSorter extends AbstractSorter
{
	
	// Other private instance variables if you need ... 
		
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *   
	 * @param pts   input array of integers
	 */
	public QuickSorter(Point[] pts)
	{
		super(pts);
		super.algorithm = "Quicksort";
	}
		

	/**
	 * Carry out quicksort on the array points[] of the AbstractSorter class.  
	 * 
	 */
	@Override 
	public void sort()
	{
		quickSortRec(0, points.length-1);
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last. 
	 * 
	 * @param first  starting index of the subarray
	 * @param last   ending index of the subarray
	 */
	private void quickSortRec(int first, int last)
	{
		//Base case
		if (first >= last) {
			return;
		}
		int partition = partition(first, last);
		quickSortRec(first, partition - 1);
		quickSortRec(partition + 1, last);
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 * @param last
	 * @return
	 */
	private int partition(int first, int last)
	{
		Point pivot = points[last];	//I could choose 3 random points from a list and use the median as the pivot (because that is a standard way of doing it), but the instructions didn't say to do that.
		int i = first -1;
		for (int j = first; j < last; j++) {
			if (pointComparator.compare(points[j], pivot) < 1) {
				i++;
				swap(i,j);
			}
		}
		swap(i+1, last);
		return i + 1; 
	}	
}
