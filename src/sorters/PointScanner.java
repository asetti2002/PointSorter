package sorters;

import java.io.File;

/**
 * 
 * @author akashsetti
 *
 */

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * This class sorts all the points in an array of 2D points to determine a
 * reference point whose x and y coordinates are respectively the medians of the
 * x and y coordinates of the original points.
 * 
 * It records the employed sorting algorithm as well as the sorting time for
 * comparison.
 *
 */
public class PointScanner {
	/**
	 * Array of points operated on by a sorting algorithm.
	 */
	private Point[] points;
	/**
	 * point whose x and y coordinates are respectively the medians of the x
	 * coordinates and y coordinates of those points in the array points[].
	 */
	private Point medianCoordinatePoint;

	/**
	 * Sorting algorithm that will be used.
	 */
	private Algorithm sortingAlgorithm;

	/**
	 * execution time in nanoseconds.
	 */
	protected long scanTime;

	/**
	 * This constructor accepts an array of points and one of the four sorting
	 * algorithms as input. Copy the points into the array points[].
	 * 
	 * @param pts input array of points
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException {
		if (pts == null) {
			throw new IllegalArgumentException();
		}
		if (pts.length == 0) {
			throw new IllegalArgumentException();
		}

		// make a copy
		points = new Point[pts.length];
		for (int i = 0; i < pts.length; i++) {
			points[i] = pts[i];
		}

		sortingAlgorithm = algo;

	}

	/**
	 * This constructor reads points from a file.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException if the input file contains an odd number of
	 *                                integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException {
		File file = new File(inputFileName);
		Scanner scanner = new Scanner(file);

		ArrayList<Integer> intsArr = new ArrayList<Integer>();

		while (scanner.hasNextInt()) {
			intsArr.add(scanner.nextInt());
		}

		if (intsArr.size() % 2 != 0) {
			scanner.close();
			throw new InputMismatchException();
		}
		points = new Point[intsArr.size() / 2]; // since this contains both the x's and the y's
		Point p;
		int index = 0;
		for (int i = 0; i < intsArr.size() - 1; i += 2) {
			p = new Point(intsArr.get(i), intsArr.get(i + 1));
			points[index] = p;
			index++;
		}
		sortingAlgorithm = algo;
		scanner.close();
	}

	/**
	 * Carry out two rounds of sorting using the algorithm designated by
	 * sortingAlgorithm as follows:
	 * 
	 * a) Sort points[] by the x-coordinate to get the median x-coordinate. b) Sort
	 * points[] again by the y-coordinate to get the median y-coordinate. c)
	 * Construct medianCoordinatePoint using the obtained median x- and
	 * y-coordinates.
	 * 
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter,
	 * InsertionSorter, MergeSorter, or QuickSorter to carry out sorting.
	 * 
	 * @param algo
	 * @return
	 */
	public void scan() {
		AbstractSorter aSorter = null;
		// create an object to be referenced by aSorter according to sortingAlgorithm.
		// for each of the two
		// rounds of sorting, have aSorter do the following:
		//
		// a) call setComparator() with an argument 0 or 1.
		//
		// b) call sort().
		//
		// c) use a new Point object to store the coordinates of the
		// medianCoordinatePoint
		//
		// d) set the medianCoordinatePoint reference to the object with the correct
		// coordinates.
		//
		// e) sum up the times spent on the two sorting rounds and set the instance
		// variable scanTime.
		long xTime = 0;
		long yTime = 0;
		long startTime = 0;
		long endTime = 0;
		int medianX = 0;
		int medianY = 0;

		if (sortingAlgorithm.equals(Algorithm.SelectionSort)) {
			aSorter = new SelectionSorter(points);

		} else if (sortingAlgorithm.equals(Algorithm.InsertionSort)) {
			aSorter = new InsertionSorter(points);

		} else if (sortingAlgorithm.equals(Algorithm.MergeSort)) {
			aSorter = new MergeSorter(points);

		} else if (sortingAlgorithm.equals(Algorithm.QuickSort)) {
			aSorter = new QuickSorter(points);
		}

		aSorter.setComparator(0);
		startTime = System.nanoTime();
		aSorter.sort();
		endTime = System.nanoTime();
		xTime = endTime - startTime;
		medianX = aSorter.getMedian().getX();

		aSorter.setComparator(1);
		startTime = System.nanoTime();
		aSorter.sort();
		endTime = System.nanoTime();
		yTime = endTime - startTime;
		medianY = aSorter.getMedian().getY();

		scanTime = xTime + yTime;
		medianCoordinatePoint = new Point(medianX, medianY);

		try {
			writeMCPToFile();
		} catch (FileNotFoundException e) {
			System.out.println("The file was not found");
		}

	}

	/**
	 * Outputs performance statistics in the format:
	 * 
	 * <sorting algorithm> <size> <time>
	 * 
	 * For instance,
	 * 
	 * selection sort 1000 9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description.
	 */
	public String stats() {
		return sortingAlgorithm + "	" + points.length + "  " + scanTime;
	}

	/**
	 * Write MCP after a call to scan(), in the format "MCP: (x, y)" The x and y
	 * coordinates of the point are displayed on the same line with exactly one
	 * blank space in between.
	 */
	@Override
	public String toString() {
		return "MCP: " + medianCoordinatePoint.toString();
	}

	/**
	 * 
	 * This method, called after scanning, writes point data into a file by
	 * outputFileName. The format of data in the file is the same as printed out
	 * from toString(). The file can help you verify the full correctness of a
	 * sorting result and debug the underlying algorithm.
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException {
		// the file not found exception isnt necessary because we provide the file
		// everytime
		try {
			String outputFileName = "src/edu/iastate/cs228/hw2/myFile.txt";
			FileWriter writer = new FileWriter(outputFileName, true);
			writer.write(toString() + "\n");
			writer.close();
		} catch (IOException e) {
			System.out.println("Caught an IOException");
		}
	}

}
