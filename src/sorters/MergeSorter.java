package sorters;
import java.util.Arrays;


/**
 *  
 * @author akashsetti
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.
 *
 */

public class MergeSorter extends AbstractSorter {

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts input array of integers
	 */
	public MergeSorter(Point[] pts) {
		super(pts);
		algorithm = "mergesort";
	}

	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter.
	 * 
	 */
	@Override
	public void sort() {
		mergeSortRec(points);
	}

	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of
	 * points. One way is to make copies of the two halves of pts[], recursively
	 * call mergeSort on them, and merge the two sorted subarrays into pts[].
	 * 
	 * @param pts point array
	 */
	private void mergeSortRec(Point[] pts) {
		int n = pts.length;
		int m = n / 2;

		if (n <= 1) {
			return;
		}

		Point[] left = Arrays.copyOfRange(pts, 0, m);
		Point[] right = Arrays.copyOfRange(pts, m, n);

		mergeSortRec(left);
		mergeSortRec(right);

		Point[] temp = new Point[pts.length];
		temp = merge(left, right);

		for (int i = 0; i < temp.length; i++) {
			pts[i] = temp[i];
		}
	}

	/**
	 * Merges two sorted arrays while sorting the new array. This method works with
	 * mergeSortRec to run the mergesort algorithm.
	 * 
	 * @param left
	 * @param right
	 * @return sorted array
	 */
	private Point[] merge(Point[] left, Point[] right) {
		int p = left.length;
		int q = right.length;
		Point[] D = new Point[p + q];

		int i = 0;
		int j = 0;
		int Dindex = 0;
		while ((i < p) && (j < q)) {
			if (pointComparator.compare(left[i], right[j]) <= 0) {
				D[Dindex] = left[i];
				i++;
			} else {
				D[Dindex] = right[j];
				j++;
			}
			Dindex++;
		}
		if (i >= p) {
			for (int z = j; z < q; z++) {
				D[Dindex] = right[z];
				Dindex++;
			}
		} else {
			for (int z = i; z < p; z++) {
				D[Dindex] = left[z];
				Dindex++;
			}
		}
		return D;

	}

}
