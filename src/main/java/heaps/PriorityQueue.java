package heaps;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * PriorityQueue is a data structure that can be a Max or a Min Heap. Using an
 * array as a underlying structure, we keep the greatest/smallest value in the
 * top. The heap logically looks like a tree where every node is greater/lesser
 * that his children. But the whole tree is not sorted or ordered, just every
 * node is greater/lesser that his children.
 * 
 * 
 * Heaps by design are structure that resemble a Complete binary tree, there two
 * types, Min and Max.
 * 
 * Max Heap: Every parent node is greater than their children, so the root node
 * has the greates value of the tree. For a specific parent node, their children
 * no should be the following greatest values in the tree.
 * 
 * 50 40 25 20 35 10 15
 * 
 * 
 * 
 * Min Heap: The root node is the smallest value of the tree, so every children
 * of this node should be greater, but not should be the following lower values
 * in the tree.
 * 
 * 
 * 10 15 25 20 35 30 50
 * 
 * 
 * Heaps can be represented as nodes classes or as an array, but with arrays we
 * dont have the information of who's the parent and who's the children, so it
 * need a mathematical formula to find them:
 *
 * parent of a node = floor(nodes_index - 1)/2 right child = ((nodes_index * 1)
 * + 2) leftchild = ((nodes_index * 2) + 1)
 * 
 * 
 * 
 * 
 * 
 * @author JoseAlejandro
 *
 * @param <T>
 */
public class PriorityQueue<T extends Comparable<T>> {
	private List<T> heap;
	private Comparator<T> comparator;

	public PriorityQueue(List<T> heap) {
		this(heap, null);
	}

	public PriorityQueue(List<T> heap, Comparator<T> comparator) {
		if (comparator == null) {
			this.comparator = (obj1, obj2) -> obj1.compareTo(obj2);
		} else {
			this.comparator = comparator;
		}
		this.heap = heap;
		buildHeap();
	}

	public int size() {
		return heap.size();
	}

	public boolean isEmpty() {
		return heap.isEmpty();
	}

	private boolean isIndexInRange(int index) {
		return index >= 0 && index < heap.size();
	}

	private int getParentIndex(int index) {
		return (index - 1) / 2;
	}

	private int getFirstChildIndex(int index) {
		return (index * 2) + 1;
	}

	private int getSecondChildIndex(int index) {
		return (index * 2) + 2;
	}

	/**
	 * Interchange the position of two elements in the heap
	 * 
	 * Time O(1) Space O(1)
	 * 
	 * @param index1
	 * @param index2
	 */
	private void swap(int index1, int index2) {

		if (isIndexInRange(index1) && isIndexInRange(index2)) {
			T temp = heap.get(index1);
			heap.set(index1, heap.get(index2));
			heap.set(index2, temp);
		}

	}

	/**
	 * Compare to position of the heap using the comparator set in the priority
	 * queue. The comparator determine if this is a Max or Min heap
	 * 
	 * 
	 * Time O(1) Space O(1)
	 * 
	 * @param index1
	 * @param index2
	 * @return
	 */
	private boolean compare(int index1, int index2) {
		boolean isEqualOrGreater = false;
		if (this.comparator.compare(heap.get(index1), heap.get(index2)) > 0) {
			isEqualOrGreater = true;
		}

		return isEqualOrGreater;
	}

	/**
	 * Retrieve without removing the top element of the heap
	 * 
	 * Complexity
	 * 
	 * Time O(1) Space O(1)
	 * 
	 * @return
	 */
	public T peek() {
		T min = null;
		if (heap.size() > 0) {
			min = heap.get(0);
		}
		return min;
	}

	/**
	 * taking a list, and converted into a min heap by applying siftDown at the last
	 * parent and then repeating the process to every element behind the last parent
	 * of the list. We use SiftDown, instead SiftUp, because SiftDown offer better
	 * performance because the majority of the elements are at the bottom of the
	 * tree, and those take O(1) in swapping with there parents, in contrast to
	 * doing this using SiftUp which start at the root and need to take the value
	 * down all the way, so the advantage is that because the majority of nodes are
	 * at the bottom, and the operation at that level takes O(1) time the complexity
	 * convey to O(n) instead O(n log(n)), which is the time it takes with SiftUp (N
	 * nodes per log n, which is the time it takes to do a shiftUp or shiftDown
	 * operation )
	 * 
	 * Complexity
	 * 
	 * Time O(n) => because we use SiftDown instead SiftUp
	 * 
	 * space O(1) => no extra store used
	 * 
	 * 
	 */
	public void buildHeap() {
		if (heap.size() > 0) {
			int indexParent = getParentIndex(heap.size() - 1);
			for (int index = indexParent; index >= 0; index--) {
				siftDown(index);
			}
		}
	}

	/**
	 * take the element at the specified index and goes up, using his parent until
	 * he locate the spot where this element is lesser that they respective
	 * ancestors.
	 * 
	 * Complexity
	 * 
	 * Time: O(log(n))=> in very step we only traverse half of the nodes (taking his
	 * parents)
	 * 
	 * Space 0(1)=> no additional structure used related to the input
	 * 
	 * 
	 * @param currIndex
	 */
	public void siftUp(int currIndex) {
		int indexParent = getParentIndex(currIndex);

		while (isIndexInRange(indexParent) && !this.compare(currIndex, indexParent)) {
			swap(currIndex, indexParent);
			currIndex = indexParent;
			indexParent = getParentIndex(currIndex);
		}
	}

	/**
	 * take the element at the specified index and compare it with his lesser child
	 * and if his child is lesser than him, we swap it, and continue doing this
	 * until we can't find a lesser child or reach the leaf of the tree.
	 * 
	 * Time: O(log(n))=> in every step we only traverse half of the nodes (taking
	 * lesser child)
	 * 
	 * 
	 * Space 0(1)=> no additional structure used related to the input
	 * 
	 * 
	 * @param currIndex
	 */
	public void siftDown(int currIndex) {

		int indexFirstChild = getFirstChildIndex(currIndex);
		int indexSecondChild = getSecondChildIndex(currIndex);
		int indexSwap = -1;

		while (isIndexInRange(indexFirstChild)) {

			if (isIndexInRange(indexSecondChild) && !this.compare(indexSecondChild, indexFirstChild)) {
				indexSwap = indexSecondChild;
			} else {
				indexSwap = indexFirstChild;
			}

			if (this.compare(currIndex, indexSwap)) {
				swap(currIndex, indexSwap);
				currIndex = indexSwap;
				indexFirstChild = getFirstChildIndex(currIndex);
				indexSecondChild = getSecondChildIndex(currIndex);
			} else {
				return;
			}

		}

	}

	/**
	 * Remove the top element of the heap, it first swap it with the last element,
	 * then SiftDown the new top element until it finds his correct position.
	 * 
	 * Time O(log(n)) Space O(1)
	 * 
	 * @return
	 */
	public T remove() {
		T min = peek();
		if (min != null) {
			int firstIndex = 0;
			int lastIndex = heap.size() - 1;

			if (this.size() > 1) {
				swap(firstIndex, lastIndex);
			}
			heap.remove(lastIndex);
			siftDown(firstIndex);

		}
		return min;
	}

	/**
	 * 
	 * Insert the element at the end of the array and then locate it in the correct
	 * position using SiftUp
	 * 
	 * Time O(log(n)) Space O(1)
	 * 
	 * @param value
	 */
	public int insert(T value) {
		heap.add(value);
		siftUp(heap.size() - 1);
		return this.size();
	}

	@Override
	public String toString() {
		return heap.toString();
	}

	public static void main(String[] args) {
		List<Integer> numbers = new ArrayList<Integer>();
		numbers.add(31);
//		numbers.add(17);
//		numbers.add(12);
//		numbers.add(18);
//		numbers.add(9);
//		numbers.add(44);
//		numbers.add(102);
//		numbers.add(30);
//		numbers.add(23);

		PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>(numbers);
		System.out.println(minHeap);
		System.out.println(minHeap.remove());
		System.out.println(minHeap.remove());
	}
}