package rsn170330.sp01;

import java.util.Scanner;
import java.util.NoSuchElementException;

/**
 * Doubly Linked List Implementation for Short Project 1.
 * Course: CS 5V81.001: Implementation of Data Structures and Algorithms
 * @author Rahul Nalawade
 * @param <T> Generic type.
 */
public class DoublyLinkedList<T> extends SinglyLinkedList<T> {
	
	/**
	 * Entry [prev|e|next] stores a doubly linked list node.
	 */
	static class Entry<E> extends SinglyLinkedList.Entry<E> {
		Entry<E> prev;

		Entry(E x, Entry<E> next, Entry<E> prev) {
			super(x, next);
			this.prev = prev;
		}
	}

	/**
	 * Doubly Linked List default constructor.
	 */
	public DoublyLinkedList() {
		head = new Entry<>(null, null, null);
		tail = head;
		size = 0;
	}
	
	/**
	 * Returns DLL Iterator after creating one.
	 * @return a doubly linked list iterator
	 */
	public DLLIterator dllIterator() { 
		return new DLLIterator(); 
	}

	/**
	 * Doubly Linked List Iterator Interface
	 * @param <T>
	 */
	public interface DoublyLinkedListIterator<T> {

		boolean hasNext();

		boolean hasPrevious();

		T next();

		T previous();

		void add(T x);

		void remove();
	}
	
	/**
	 * Implementation of DLL Iterator inheriting SLL Iterator and DoublyLinkedListIterator<T> 
	 */
	protected class DLLIterator extends SLLIterator implements DoublyLinkedListIterator<T> {

		/**
		 * NOTE: If you omit the superclass constructor call, the superclass constructor with
		 * no arguments is invoked.
		 */
		// DLLIterator() { super(); }

		// Returns true if there exists a previous entry, false otherwise.
		public boolean hasPrevious() {
			return ((DoublyLinkedList.Entry<T>) cursor).prev != null;
		}

		// Returns the previous entry.
		public T previous() {
			cursor = prev;
			prev = ((DoublyLinkedList.Entry<T>) cursor).prev;

			ready = true;
			return cursor.element;
		}

		/**
		 * Removes the current element (retrieved by the most recent next()). Remove can
		 * be called only if next has been called and the element has not been removed.
		 */
		public void remove() {

			if (!ready) {
				throw new NoSuchElementException();
			}

			// Handle case when tail of a list is to be removed
			if (cursor == tail) {
				prev.next = null;
				tail = prev;
			} else {
				prev.next = cursor.next;
				((DoublyLinkedList.Entry<T>) cursor.next).prev = (Entry<T>) prev; // KISKI ENTRY BHAI? SinglyLinkedList?
			}

			cursor = prev;
			prev = ((DoublyLinkedList.Entry<T>) cursor).prev; 
			// Calling remove again without calling
			// next will result in exception thrown
			ready = false;
			size--;
		}

		// Generic add(x) calls to add(Entry<T> e)
		public void add(T x) {
			System.out.println(x);
			add(new Entry<>(x, null, null));
		}

		/**
		 * Main add(). Adds an Entry to the list.
		 * @param ent the Entry to be added.
		 */
		public void add(Entry<T> ent) {

			// When cursor is tail 
			if (cursor == tail) {
				tail.next = ent;
				ent.prev = (Entry<T>) tail;
				tail = tail.next;
			} else {
				ent.prev = (Entry<T>) cursor;
				ent.next = cursor.next;

				((DoublyLinkedList.Entry<T>) cursor.next).prev = ent;
				cursor.next = ent;
			}
			cursor = ent;
			prev = ent.prev;
			size++;
		}

	} // end of class DLLIterator

	// Add new elements to the end of the list
	public void add(T x) {
		add(new Entry<>(x, null, null));
	}

	// Add first element in the list before the next entry.
	public void add(Entry<T> ent) {
		tail.next = ent;
		ent.prev = (Entry<T>) tail;
		tail = tail.next;
		size++;
	}

	// ----------------------------------- MAIN --------------------------------//

	public static void main(String[] args) throws NoSuchElementException {
		int n = 10;
		if (args.length > 0) { 
			n = Integer.parseInt(args[0]);
		}
		
		DoublyLinkedList<Integer> lst = new DoublyLinkedList<>();
		
		for (int i = 1; i <= n; i++) {
			lst.add(Integer.valueOf(i));
		}
		
		lst.printList();
		
		DoublyLinkedListIterator<Integer> it = lst.dllIterator();
		
		Scanner in = new Scanner(System.in);
		
		whileloop: 
		while (in.hasNext()) {
			int com = in.nextInt();
			
			switch (com) {

			// Checks hasNext() and next() AND Move to next element and print it.
			case 1:
				if (it.hasNext()) {
					System.out.println(it.next());
				} else {
					break whileloop;
				}
				break;

			// Checks hasPrevious() and previous() AND Move to previous element and print it.
			case 2:
				if (it.hasPrevious()) {
					System.out.println(it.previous());
				} else {
					break whileloop;
				}
				break;

			// Checks remove() AND Removes an entry.
			case 3:
				it.remove();
				lst.printList();
				break;

			// Checks add(x) AND Insert an entry.
			case 4:
				int value = in.nextInt();
				it.add(value);

				lst.printList();
				break;

			// Exit loop.
			default:
				break whileloop;
			}
		}

		lst.printList();
		lst.unzip();
		lst.printList();
	}

}