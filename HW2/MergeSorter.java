package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;

/**
 *  
 * @author Tanner Smith
 * This class uses the MergeSort algorithm to sort an array of points
 */


public class MergeSorter extends AbstractSorter
{
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		super.algorithm = "Mergesort";
		
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		mergeSortRec(points);
		
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
		int length = pts.length;
		//base case
		if (length <= 1) {
			return;
		}
		
		//Recursion
		int midIndex = length / 2;
		Point[] leftHalf = new Point[midIndex];
		Point[] rightHalf = new Point[length - midIndex];
		int j = 0;
		for(int i = 0; i < length; i++) {
			if (i < midIndex) {
				leftHalf[i] = pts[i];
			} else {
				rightHalf[j] = pts[i];
				j++;
			}
		}
		mergeSortRec(leftHalf);
		mergeSortRec(rightHalf);
		merge(leftHalf, rightHalf, pts);
	}
	
	private void merge(Point[] left, Point[] right, Point[] pts) {
		int leftSide = pts.length / 2;
		int rightSide = pts.length - leftSide;
		int i = 0; //pts index
		int l = 0; //leftHalf index
		int r = 0; //rightHalf index
		
		while(l < leftSide && r < rightSide) {
			if(pointComparator.compare(left[l], right[r]) < 0) {
				pts[i] = left[l];
				i++;
				l++;
			}else {
				pts[i] = right[r];
				i++;
				r++;
			}
		}
		while(l < leftSide) {
			pts[i] = left[l];
			i++;
			l++;
		}
		while (r < rightSide) {
			pts[i] = right[r];
			i++;
			r++;
		}
	}
}
