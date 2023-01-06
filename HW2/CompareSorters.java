package edu.iastate.cs228.hw2;

/**
 * @author Tanner Smith
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.Random; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{		
		int trialNum = 1;	//keeps track of how many trials have been done
		Scanner userInput = new Scanner(System.in);
		int userChoice = 1;
		Point[] userPoints;
		PointScanner[] scanners = new PointScanner[4];
		System.out.println("Performance of Four Sorting Algorithms in Point Scanning");
		System.out.println();
		System.out.println("Keys: 1 (random integer)   2 (file input)   3 (exit)");
		
		while (userChoice != 3) {
			System.out.print("Trial " + trialNum +": ");
			userChoice = userInput.nextInt();
			if (userChoice == 1) {		//CREATE RANDOM POINTS
				System.out.print("Enter a number of random points: ");
				int numPoints = userInput.nextInt();
				Random rand = new Random();
				userPoints = generateRandomPoints(numPoints, rand); 
				
				//create scanners
				scanners[0] = new PointScanner(userPoints, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(userPoints, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(userPoints, Algorithm.MergeSort);
				scanners[3] = new PointScanner(userPoints, Algorithm.QuickSort);
				
				//iterating through scanner list and printing stats
				System.out.println("\nalgorithm\tsize\ttime (ns)");
				System.out.println("-------------------------------");
				for (PointScanner scanner : scanners) {
					scanner.scan();
					System.out.println(scanner.stats());
					scanner.writeMCPToFile();
				}
				System.out.println("-------------------------------");
				System.out.println();
				trialNum++;
				
			}else if(userChoice == 2) {			//READ FROM A FILE
				System.out.println("Points from a file");
				System.out.print("File Name: ");
				String fileName = userInput.next();
				
				//create scanners
				scanners[0] = new PointScanner(fileName, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(fileName, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(fileName, Algorithm.MergeSort);
				scanners[3] = new PointScanner(fileName, Algorithm.QuickSort);
				
				//iterating through scanner list and printing stats
				System.out.println("\nalgorithm\tsize\ttime (ns)");
				System.out.println("-------------------------------");
				for (PointScanner scanner : scanners) {
					scanner.scan();
					System.out.println(scanner.stats());
				}
				System.out.println("-------------------------------");
				System.out.println();
				trialNum++;
			}else {
				break;
			}
		}
		userInput.close();
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] × [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	private static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		if (numPts < 1) {
			throw new IllegalArgumentException();
		}else {
			Point[] pointList = new Point[numPts];
			for (int i = 0; i<numPts; i++) {
				int xVal = rand.nextInt(101) - 50;
				int yVal = rand.nextInt(101) - 50;
				Point p = new Point(xVal, yVal);
				pointList[i] = p;
			}
			
			return pointList; 
		}
	}
}
