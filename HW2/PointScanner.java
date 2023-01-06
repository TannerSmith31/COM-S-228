package edu.iastate.cs228.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Tanner Smith
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 */


public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	
	private static final String outputFileName = "MCP.txt";	//A name for the output file
	
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException();
		}
		else {
			points = new Point[pts.length];
			for (int i = 0; i < pts.length; i++) {
				points[i] = pts[i];
			}
			sortingAlgorithm = algo;
		}
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		ArrayList<Integer> integerArr = new ArrayList<Integer>();
		sortingAlgorithm = algo;
		
		File userFile = new File(inputFileName);
		Scanner fileScanner = new Scanner(userFile);
		
		//check if there are an even number of integers
		int numInts = 0;
		while(fileScanner.hasNextInt()) {
			integerArr.add(fileScanner.nextInt());
			numInts++;
		}
		fileScanner.close();
		
		//Putting Integers into a list (throwing exception if odd number of points)
		if (numInts % 2 != 0) {
			throw new InputMismatchException();
		} else {
			points = new Point[numInts/2];	//Points are comprised of 2 ints so length numInts/2
			for (int i = 0; i < integerArr.size(); i = i+2) {
				points[i/2] = new Point(integerArr.get(i), integerArr.get(i+1));
			}
		}
	}

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param algo
	 * @return
	 */
	public void scan()
	{
		AbstractSorter aSorter;
		if (sortingAlgorithm == Algorithm.SelectionSort) {
			aSorter = new SelectionSorter(points);
			
		} else if (sortingAlgorithm == Algorithm.MergeSort) {
			aSorter = new MergeSorter(points);
			
		} else if (sortingAlgorithm == Algorithm.InsertionSort) {
			aSorter = new InsertionSorter(points);
			
		}else {
			aSorter = new QuickSorter(points);
		}
		
		//SORT BY X
		long startTime = System.nanoTime(); //Gets time before sorting
		aSorter.setComparator(0); //0 is sorting by x coordinate
		aSorter.sort();
		int XMedian = aSorter.getMedian().getX();
		
		//SORT BY Y
		aSorter.setComparator(1); //1 is sorting by y coordinate
		aSorter.sort();
		int YMedian = aSorter.getMedian().getY();
		long endTime = System.nanoTime();	//Gets time after ending
		scanTime = endTime - startTime; 
		medianCoordinatePoint = new Point(XMedian, YMedian);
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
		int size = points.length;
		String algo = sortingAlgorithm.toString();
		String stats = algo + "\t" + size + "\t" + scanTime;
		return stats; 
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		String MCPString = "MCP: " + medianCoordinatePoint.toString();
		return MCPString; 	
	}

	
	/**
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
		File outputFile = new File(outputFileName);
		PrintWriter fileWriter = new PrintWriter(outputFile);
		fileWriter.write(toString());
		fileWriter.close();
	}	
}
