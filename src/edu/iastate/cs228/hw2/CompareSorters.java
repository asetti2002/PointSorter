package edu.iastate.cs228.hw2;

/**
 *  
 * @author akashsett
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class CompareSorters {
	/**
	 * Repeatedly take integer sequences either randomly generated or read from
	 * files. Use them as coordinates to construct points. Scan these points with
	 * respect to their median coordinate point four times, each time using a
	 * different sorting algorithm.
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException {
		//
		// Conducts multiple rounds of comparison of four sorting algorithms. Within
		// each round,
		// set up scanning as follows:
		//
		// a) If asked to scan random points, calls generateRandomPoints() to initialize
		// an array
		// of random points.
		//
		// b) Reassigns to the array scanners[] (declared below) the references to four
		// new
		// PointScanner objects, which are created using four different values
		// of the Algorithm type: SelectionSort, InsertionSort, MergeSort and QuickSort.

		// For each input of points, do the following.
		//
		// a) Initialize the array scanners[].
		//
		// b) Iterate through the array scanners[], and have every scanner call the
		// scan()
		// method in the PointScanner class.
		//
		// c) After all four scans are done for the input, print out the statistics
		// table from
		// section 2.
		//
		// A sample scenario is given in Section 2 of the project description.

		System.out.println("Performances of Four Sorting Algorithms in Point Scanning");
		System.out.println();
		Scanner scan = new Scanner(System.in);
//		File file;
		System.out.println("keys:  1 (random integers)  2 (file input)  3 (exit)");
		int trialNum = 1;
		int check;
		PointScanner[] scanners = new PointScanner[4];
		while (true) {
			System.out.print("Trail " + trialNum + ": ");
			check = scan.nextInt();
			if (check == 1) {
				System.out.print("Enter the number of random points: ");
				int size = scan.nextInt();
				System.out.println();
				Random rand = new Random();
				Point[] arr = generateRandomPoints(size, rand);
				scanners[0] = new PointScanner(arr, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(arr, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(arr, Algorithm.MergeSort);
				scanners[3] = new PointScanner(arr, Algorithm.QuickSort);
				System.out.println("algorithm   size  time (ns)");
				System.out.println("----------------------------------");

				for (int i = 0; i < scanners.length; i++) {
					scanners[i].scan();
					System.out.println(scanners[i].stats());
				}
				System.out.println("----------------------------------\n");

			} else if (check == 2) { 
				System.out.println("Points from a file"); // src/edu/iastate/cs228/hw2/points.txt
				System.out.print("File name: ");
				String fileName = scan.next();
				System.out.println();
				try {
					scanners[0] = new PointScanner(fileName, Algorithm.SelectionSort);
					scanners[1] = new PointScanner(fileName, Algorithm.InsertionSort);
					scanners[2] = new PointScanner(fileName, Algorithm.MergeSort);
					scanners[3] = new PointScanner(fileName, Algorithm.QuickSort);
					System.out.println("algorithm   size  time (ns)");
					System.out.println("----------------------------------");

					for (int i = 0; i < scanners.length; i++) {
						scanners[i].scan();
						System.out.println(scanners[i].stats());
					}
					System.out.println("----------------------------------\n");
				} catch (FileNotFoundException e) {
					System.out.println("The file was not found.");
				}

			} else {
				break;
			}
			trialNum++;
		}
		scan.close();
		
	}

	/**
	 * This method generates a given number of random points. The coordinates of
	 * these points are pseudo-random numbers within the range [-50,50] � [-50,50].
	 * Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing.
	 * 
	 * @param numPts number of points
	 * @param rand   Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException {

		if (numPts < 1) {
			throw new IllegalArgumentException();
		}
		Point[] pt = new Point[numPts];
		for (int i = 0; i < numPts; i++) {
			Point p = new Point(rand.nextInt(101) - 50, rand.nextInt(101) - 50);
			pt[i] = p;
		}

		return pt;
	}

}
