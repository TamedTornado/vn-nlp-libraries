/**
 * Phuong LE HONG, phuonglh@gmail.com
 */
package vn.hus.nlp.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author LE HONG Phuong, phuonglh@gmail.com
 * <p>
 * Oct 8, 2009, 2:20:12 PM
 * <p>
 * A selector which selects randomly some elements from a list of elements. This 
 * is useful for constructing training and test sets from a corpus.
 */
public class RandomSelector<T> {

	private List<T> selectedElements = new ArrayList<T>();
	private List<T> unselectedElements;;
	
	
	/**
	 * Constructor of a selector for selecting <code>n</code> elements
	 * from a list of elements.
	 * @param elements a list of elements
	 * @param n an integer
	 */
	public RandomSelector(List<T> elements, int n) {
		select(elements, n);
	}
	
	private void select(List<T> elements, int n) {
		if (n > elements.size()) {
			System.err.println("Error. The size of dataset is less than " + n);
			System.err.println();
			return;
		}
		
		unselectedElements = new ArrayList<T>(elements);

		// create a set of integers
		Set<Integer> pool = new HashSet<Integer>();
		for (int i = 0; i < elements.size(); i++) {
			pool.add(i);
		}
		Random generator = new Random();
		// randomly select n integers from this set
		// each time an integer is selected, it is removed from 
		// the set so that the next time we have a different int
		int k;
		for (int j = 0; j < n; j++) {
			boolean nextRound = false;
			do {
				k = generator.nextInt(elements.size());
				if (pool.contains(k)) {
					selectedElements.add(elements.get(k));
					unselectedElements.remove(elements.get(k));
					pool.remove(k);
					nextRound = true;
				}
			} while (!nextRound);
		}
	}
	
	/**
	 * Returns the list of selected elements.
	 * @return a list of selected elements
	 */
	public List<T> getSelectedElements() {
		return selectedElements;
	}
	
	/**
	 * Returns the list of unselected elements.
	 * @return a list of unselected elements.
	 */
	public List<T> getUnselectedElements() {
		return unselectedElements;
	}
	
	/**
	 * For internal test only.
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> elements = new ArrayList<Integer>();
		for (int i = 0; i < 20; i++) {
			elements.add(i);
		}
		RandomSelector<Integer> randomSelector = new RandomSelector<Integer>(elements, 5);
		System.out.println("Selected elements = ");
		System.out.println(randomSelector.getSelectedElements());
		System.out.println("Unselected elements = ");
		System.out.println(randomSelector.getUnselectedElements());
	}

}
