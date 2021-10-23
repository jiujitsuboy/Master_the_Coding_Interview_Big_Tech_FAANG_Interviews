package queue;

import java.util.Stack;

/***
 * Implement a Queue using Stacks
 * @author Jose
 *
 * @param <T>
 */
public class Exercise12_Implement_Queue_Using_Stacks<T> {

	private Stack<T> originalOrder;
	private Stack<T> inverseOrder;

	public Exercise12_Implement_Queue_Using_Stacks() {
		originalOrder = new Stack<>();
		inverseOrder = new Stack<>();
	}
	//time complexity O(1)
	public void enqueue(T item) {
		originalOrder.push(item);
	}

	//time complexity O(n)
	public T dequeue() {
		invertElementOrder();
		return (!inverseOrder.isEmpty()) ? inverseOrder.pop() : null;
	}

	//time complexity O(n)
	public T peek() {
		invertElementOrder();
		return (!inverseOrder.isEmpty()) ? inverseOrder.elementAt(inverseOrder.size() - 1) : null;
	}

	//time complexity O(1)
	public boolean empty() {
		return inverseOrder.isEmpty() && originalOrder.isEmpty();
	}
	
	private void invertElementOrder() {
		if (inverseOrder.isEmpty()) {
			while (!originalOrder.isEmpty()) {
				inverseOrder.push(originalOrder.pop());
			}
		}
	}

	public static void main(String[] args) {
		Exercise12_Implement_Queue_Using_Stacks<Integer> queue = new Exercise12_Implement_Queue_Using_Stacks<>();

		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
		queue.enqueue(4);

		System.out.println("---------------------");
		System.out.println(queue.dequeue());
		System.out.println(queue.dequeue());
		System.out.println("---------------------");
		queue.enqueue(5);
		queue.enqueue(6);

		System.out.println(queue.peek());
		System.out.println(queue.dequeue());
		System.out.println(queue.dequeue());
		System.out.println("---------------------");

		System.out.println(queue.empty());

		System.out.println(queue.dequeue());
		System.out.println(queue.dequeue());
		System.out.println(queue.dequeue());

		System.out.println(queue.empty());

	}

}
