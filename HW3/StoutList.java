package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the list interface based on linked nodes
 * that store multiple items per node.  Rules for adding and removing
 * elements ensure that each node (except possibly the last one)
 * is at least half full.
 * @author Tanner Smith
 * (for a majority)
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E>
{
  private static final int DEFAULT_NODESIZE = 4;	//Default number of elements that may be stored in each node
  
  private final int nodeSize;	//number of elements that can be stored in each node
  
  /**
   * Dummy node for head.  It should be private but set to public here only  
   * for grading purpose.  In practice, you should always make the head of a 
   * linked list a private instance variable.  
   */
  public Node head;
  
  private Node tail;	//dummy node for tail
  
  private int size;		//number of elements in the list
  
  /**
   * Constructs an empty list with the default node size.
   */
  public StoutList()
  {
    this(DEFAULT_NODESIZE);
  }

  /**
   * Constructs an empty list with the given node size.
   * @param nodeSize number of elements that may be stored in each node, must be 
   *   an even number
   */
  public StoutList(int nodeSize)
  {
    if (nodeSize <= 0 || nodeSize % 2 != 0) throw new IllegalArgumentException();
    
    // dummy nodes
    head = new Node();
    tail = new Node();
    head.next = tail;
    tail.previous = head;
    this.nodeSize = nodeSize;
    size = 0;							//I ADDED THIS LINE! to initialize size (even though this section didnt have a "todo"
  }
  
  /**
   * Constructor for grading only.  Fully implemented. 
   * @param head
   * @param tail
   * @param nodeSize
   * @param size
   */
  public StoutList(Node head, Node tail, int nodeSize, int size)
  {
	  this.head = head; 
	  this.tail = tail; 
	  this.nodeSize = nodeSize; 
	  this.size = size; 
  }

  /**
   * Gets the total number of elements over all nodes in the list
   * @return size of list
   */
  @Override
  public int size()
  {
    return size;
  }
  
  /**
   * Adds an item to the end of the list
   * @param item
   * @throws NullPointer Exception if you try to add a null item
   */
  @Override
  public boolean add(E item) throws NullPointerException
  {
	 if(item == null) {
		 throw new NullPointerException();
	 }
	 if(size == 0){
		 Node newNode = new Node(); //creates a new node. Size is accounted for in Node class
		 
		 //link node into chain
		 head.next = newNode;
		 newNode.next = tail;
		 newNode.previous = head;
		 tail.previous = newNode;
		 //add item at offset 0
		 newNode.addItem(item);	//this adds E at the first available offset (always 0 in this case)
		 
		//BOOKKEEPING
		 size++;
		 return true;
	 }
	 else if (tail.previous.count < nodeSize) {//checking if there is space in last cell to put another item. Previous cell being the head is covered in prior branch
		 tail.previous.addItem(item);//adds E at the first available offset
		 
		 //BOOKKEEPING
		 size++;
		 return true;
	 }
	 else if (tail.previous.count == nodeSize) {//executes if the last node is full and you need to add another node.
		 Node newNode = new Node();
		 
		 //link node into chain
		 tail.previous.next = newNode;
		 newNode.next = tail;
		 newNode.previous = tail.previous;
		 tail.previous = newNode;
		 //add item at offset 0
		 newNode.addItem(item);
		 
		//BOOKKEEPING
		 size++;
		 return true;
	 }
	 else {
		 return false;
	 }
  }

  /**
   * adds an item to a given position in the list, shifting all elements
   * to the right of it rightward
   * right to accomplish this.
   * @param pos - position to insert the item
   * @param item - item to insert
   */
  @Override
  public void add(int pos, E item)	//WHY IS THIS NOT BOOLEAN LIKE THE OTHER ADD?
  {
	if(item == null) {
		throw new NullPointerException();
	}
	if(pos == size) {					//NOTE: THIS WAS NOT IN THE SKELLETON ON CANVAS!!
		add(item);						//BUT I THINK IT IS HINTED AT IN THE HINTS TO GET STARTED
		size++;
		return;
	}
	NodeInfo info = find(pos);
	Node n = info.node;
	int off = info.offset;
	if (size == 0) {
		Node newNode = new Node(); //creates a new node. Size is accounted for in Node class
		 
		 //link node into chain
		 head.next = newNode;
		 newNode.next = tail;
		 newNode.previous = head;
		 tail.previous = newNode;
		 //add item at offset 0
		 newNode.addItem(item);	//this adds E at the first available offset (always 0 in this case)
	}
	else if(off == 0 && n.previous != head && n.previous.count < nodeSize) {	//if n has a predecessor which has room and is not the head
		n.previous.addItem(item);	//put item at end of predecessor
	}
	else if (off == 0 && n == tail && n.previous.count == nodeSize) {	//if n is the tail node and its predecessor is full
		Node newNode = new Node();
			 
		 //link node into chain
		 tail.previous.next = newNode;
		 newNode.next = tail;
		 newNode.previous = tail.previous;
		 tail.previous = newNode;
		 
		 //add item at offset 0
		newNode.addItem(item);
	}
	else if (n.count < nodeSize) { //If node n has space, put element in node n
		n.addItem(off, item);
	}
	else {	//the current node is full so it must be split
		Node newSucNode = new Node();
		
		//linking successor node into chain
		n.next.previous = newSucNode;
		newSucNode.next = n.next;
		n.next = newSucNode;
		newSucNode.previous = n;
		
		//move last M/2 elements of node n into new successor node
		for(int i = 0; i < nodeSize/2; i++) {
			newSucNode.addItem(n.data[nodeSize/2]);
			n.removeItem(nodeSize/2);
		}
		
		if (off <= nodeSize/2) {	//If offset was in 1st half, put in original node at offset off
			n.addItem(off, item);	//recursive call
		}
		if (off > nodeSize/2) {		//If offset was in 2nd half, put in successor node at offset (off-M/2)
			int newPos = off - nodeSize/2;
			newSucNode.addItem(newPos, item);	//recursive call
		}
	}
	
	//BOOKKEEPING
	size++;
  }

  /**
   * removes an item at a given index and returns the item removed
   * @param pos - index of element to be removed
   * @return deleted element
   */
  @Override
  public E remove(int pos)	//DO I NEED TO ACCOUNT FOR POS OUT OF BOUNDS?
  {
	  NodeInfo info = find(pos);
	  Node n = info.node;
	  int off = info.offset;
	  E removedItem = n.data[off];
	  
	  if (n == tail.previous && n.count < 2) {	//tail node with only 1 element. Delete the node
		  n.previous.next = tail;
		  n.next.previous = n.previous;
	  }
	  else if (n == tail.previous || n.count > nodeSize/2) {	//tail node with 2 or more elements OR node has more than M/2 elements
		  n.removeItem(off);
	  }
	  else {	//n must have M/2 or less elements, so merging must happen.
		  Node successor = n.next;
		  
		  if (successor.count > nodeSize/2) {	//successor has more than M/2 elements. MiniMerge: move first successor element to n
			  n.removeItem(off);
			  n.addItem(successor.data[0]);
			  successor.removeItem(0);
			  
		  }else {	//successor has M/2 or less elements. Full merge
			  //move all elements from successor to n and delete successor 
			  n.removeItem(off);				//remove item
			  for (int i = n.count; i < nodeSize; i++) {	//copy over successor elements
				  if (successor.count == 0) {	//if there is nothing left in the successor, break the loop
					  break;
				  }
				  n.addItem(successor.data[0]);
				  successor.removeItem(0);
			  }
			  
			  //delete the successor node
			  successor.next.previous = n;
			  n.next = successor.next;
		  }
	  }
	  
	  //BOOKEEPING
	  size--;
	  return removedItem;
    
  }

  /**
   * Sort all elements in the stout list in the NON-DECREASING order. You may do the following. 
   * Traverse the list and copy its elements into an array, deleting every visited node along 
   * the way.  Then, sort the array by calling the insertionSort() method.  (Note that sorting 
   * efficiency is not a concern for this project.)  Finally, copy all elements from the array 
   * back to the stout list, creating new nodes for storage. After sorting, all nodes but 
   * (possibly) the last one must be full of elements.  
   *  
   * Comparator<E> must have been implemented for calling insertionSort().    
   */
  public void sort()
  {
	  int startSize = size;
	  E[] elements = (E[]) new Comparable[startSize];	//unchecked exception
	  Node curNode = head.next;
	  for (int pos = 0; pos < startSize; pos++) {	//take all elements out of list, put them in array, delete nodes
		  E item = curNode.data[0];
		  elements[pos] = item;
		  remove(0);	//remove element (this should remove nodes accordingly
	  }
	  
	  //call insertion sort
	  insertionSort(elements, new Comparator<E>() {
		  //instantiate a comparator for the sorter to user
		  @Override
		  public int compare(E item1, E item2) {
			  return item1.compareTo(item2);
		  }
	  });
	  
	  //remake the list with all nodes full
	  for (int i = 0; i < startSize; i++) {
		  add(elements[i]);
	  }
  }
  
  /**
   * Sort all elements in the stout list in the NON-INCREASING order. Call the bubbleSort()
   * method.  After sorting, all but (possibly) the last nodes must be filled with elements.  
   *  
   * Comparable<? super E> must be implemented for calling bubbleSort(). 
   */
  public void sortReverse() 
  {
	  int startSize = size;
	  E[] elements = (E[]) new Comparable[startSize];	//unchecked exception
	  Node curNode = head.next;
	  for (int pos = 0; pos < startSize; pos++) {	//take all elements out of list, put them in array, delete nodes
		  E item = curNode.data[0];
		  elements[pos] = item;
		  remove(0);	//remove element (this should remove nodes accordingly
	  }
	  
	  //call insertion sort
	  bubbleSort(elements);
	  
	  //remake the list with all nodes full
	  for (int i = 0; i < startSize; i++) {
		  add(elements[i]);
	  }
  }
  
  /**
   * creates a new iterator for StoutList(NOT a list iterator)
   * iterator starts at the beginning of the list (pos 0)
   * @return - new iterator
   */
  @Override
  public Iterator<E> iterator()
  {
    return new StoutIterator();
  }

  /**
   * creates a new LIST iterator for StoutList
   * iterator starts at the beginning of the list (pos 0)
   * @return new list iterator
   */
  @Override
  public ListIterator<E> listIterator()
  {
    return new StoutListIterator();
  }

  /**
   * creates a new LIST iterator for StoutList that is initially
   * positioned at the passed index
   * @param index - position to start the iterator at
   */
  @Override
  public ListIterator<E> listIterator(int index)
  {
    return new StoutListIterator(index);
  }
  
  /**
   * Returns a string representation of this list showing
   * the internal structure of the nodes.
   */
  public String toStringInternal()
  {
    return toStringInternal(null);
  }

  /**
   * Returns a string representation of this list showing the internal
   * structure of the nodes and the position of the iterator.
   *
   * @param iter
   *            an iterator for this list
   */
  public String toStringInternal(ListIterator<E> iter) 
  {
      int count = 0;
      int position = -1;
      if (iter != null) {
          position = iter.nextIndex();
      }

      StringBuilder sb = new StringBuilder();
      sb.append('[');
      Node current = head.next;
      while (current != tail) {
          sb.append('(');
          E data = current.data[0];
          if (data == null) {
              sb.append("-");
          } else {
              if (position == count) {
                  sb.append("| ");
                  position = -1;
              }
              sb.append(data.toString());
              ++count;
          }

          for (int i = 1; i < nodeSize; ++i) {
             sb.append(", ");
              data = current.data[i];
              if (data == null) {
                  sb.append("-");
              } else {
                  if (position == count) {
                      sb.append("| ");
                      position = -1;
                  }
                  sb.append(data.toString());
                  ++count;

                  // iterator at end
                  if (position == size && count == size) {
                      sb.append(" |");
                      position = -1;
                  }
             }
          }
          sb.append(')');
          current = current.next;
          if (current != tail)
              sb.append(", ");
      }
      sb.append("]");
      return sb.toString();
  }


  /**
   * Node type for this list.  Each node holds a maximum
   * of nodeSize elements in an array.  Empty slots
   * are null.
   */
  private class Node
  {
    /**
     * Array of actual data elements.
     */
    public E[] data = (E[]) new Comparable[nodeSize]; // Unchecked warning unavoidable.
    
    public Node next;	//link to next node
    
    public Node previous;	//link to previous node
    
    /**
     * Index of the next available offset in this node, also 
     * equal to the number of elements in this node.
     */
    public int count;

    
    /**
     * Adds an item to this node at the first available offset.
     * Precondition: count < nodeSize
     * @param item element to be added
     */
    void addItem(E item)
    {
      if (count >= nodeSize)
      {
        return;
      }
      data[count++] = item;
      //useful for debugging
      //      System.out.println("Added " + item.toString() + " at index " + count + " to node "  + Arrays.toString(data));
    }
  
    
    /**
     * Adds an item to this node at the indicated offset, shifting
     * elements to the right as necessary.
     * 
     * Precondition: count < nodeSize
     * @param offset array index at which to put the new element
     * @param item element to be added
     */
    void addItem(int offset, E item)
    {
      if (count >= nodeSize)
      {
    	  return;
      }
      for (int i = count - 1; i >= offset; --i)
      {
        data[i + 1] = data[i];
      }
      ++count;
      data[offset] = item;
      //useful for debugging 
//      System.out.println("Added " + item.toString() + " at index " + offset + " to node: "  + Arrays.toString(data));
    }

    
    /**
     * Deletes an element from this node at the indicated offset, 
     * shifting elements left as necessary.
     * Precondition: 0 <= offset < count
     * @param offset
     */
    void removeItem(int offset)
    {
      E item = data[offset];					//what is the point of this line? could I make this be returned?
      for (int i = offset + 1; i < nodeSize; ++i)
      {
        data[i - 1] = data[i];
      }
      data[count - 1] = null;
      --count;
    }    
  }
  
  /**
   * An implementation of the iterator interface for StoutList.
   * Only supports one directional travel and methods inherited
   * from the Iterator interface. NOT a list iterator
   * Completely made by me as there was no "toDo" to make it
   * though the document implied that this would be helpful
   */
  private class StoutIterator implements Iterator<E>{
	  int curIndex;			//index of the cursor
	  Node curNode;			//node the cursor is in
	  int curOffset;		//cursor's current offset within the node
	  boolean canRemove;	//Tells whether or not you may call remove or set
	  
	  
	  /*
	   * constructs an iterator starting at index 0 (beginning of list)
	   * which is at offset 0 of the first node after the head.
	   */
	  public StoutIterator()
	    {
	    	curIndex = 0;
	    	curNode = head.next;
	    	curOffset = 0;
	    	canRemove = false;	
	    }
	  
	  
	  /*
	   * returns true if there is an element after the cursor. Returns false otherwise
	   */
	  @Override
	  public boolean hasNext()
	  {
		  return curIndex < size; 	
	  }
	  
	  
	  /**
	   * moves the cursor forward one and serves the element that the cursor
	   * passed over
	   * @return passed element
	   * @throws NoSuchElementException if there is no next element to be served
	   */
	  @Override
	    public E next() throws NoSuchElementException
	    {
		  if (!hasNext()) {
			  throw new NoSuchElementException();
		  }
		  if (curOffset == curNode.count) { //if you are at the last element of the node, move cursor to the beginning of the next node
			  curNode = curNode.next;
			  curOffset = 0;
		  }
		  E returnItem = curNode.data[curOffset++]; //this gets the item from the current offset and THEN increments curOffset (post-increment)
		  //curOffset is updated in the line above here so we don't need to do curOffset++ here again
		  canRemove = true;
		  curIndex++;
		  return returnItem;
		  
	    }
	  

	  	/**
	  	 * removes the element last served by the iterator
	  	 */
	    @Override
	    public void remove()
	    {
	    	if (!canRemove) {
	    		throw new IllegalStateException(); 
	    	}
	    	int off = curOffset - 1; //this only works because this is one directional
	  	  		//this only works because this is one directional
	  	  
	  	  	if (curNode == tail.previous && curNode.count < 2) {	//tail node with only 1 element. Delete the node
	  	  		curNode.previous.next = tail;
	  		  	curNode.next.previous = curNode.previous;
	  		  	curNode = curNode.previous;
	  		  	curOffset = curNode.count; //cursor at the end of the previous node
	  		  	
	  	  	}
	  	  	else if (curNode == tail.previous || curNode.count > nodeSize/2) {	//tail node with 2 or more elements OR node has more than M/2 elements
	  	  		curNode.removeItem(off);
	  	  		curOffset--;
	  	  	}
	  	  	else {	//n must have M/2 or less elements, so merging must happen.
	  	  		Node successor = curNode.next;
	  		  
	  	  		if (successor.count > nodeSize/2) {	//successor has more than M/2 elements. MiniMerge: move first successor element to n
	  	  			curNode.removeItem(off);
	  			  	curNode.addItem(successor.data[0]);
	  			  	successor.removeItem(0);
	  			  	curOffset--;
	  			  
	  	  		}else {	//successor has M/2 or less elements. Full merge
	  	  			//move all elements from successor to n and delete successor 
	  	  			curNode.removeItem(off);				//remove item
	  	  			for (int i = curNode.count; i < nodeSize; i++) {	//copy over successor elements
	  	  				if (successor.count == 0) {	//if there is nothing left in the successor, break the loop
	  	  					break;
	  	  				}
	  	  				curNode.addItem(successor.data[0]);
	  	  				successor.removeItem(0);
	  	  			}
	  			  
	  	  			//delete the successor node
	  	  			successor.next.previous = curNode;
	  	  			curNode.next = successor.next;
	  	  			curOffset--;
	  	  		}
	  	  	}
	  	  
	  	  	//BOOKEEPING
	  	  	size--;
	  	  	curIndex--;
	  	  	canRemove = false;
	    }
  }
  
  /**
   * A list iterator for the stout list. This implements all the methods and functionality
   * from the listIterator interface and supports bi-directional traversal
   * @author Tanner Smith
   *
   */
  private class StoutListIterator implements ListIterator<E>
  {
	// constants you possibly use ...   
	  
	// instance variables ... NOTE: I have added all of these
	int curIndex;	//current logical index the pointer is at (or to the right of)
	Node curNode;	//current node the pointer is in
	int curOffset; //current offset the pointer is at (or to the right of
	boolean canRemove;	//keeps track of whether or not you are allowed to remove an element
	int direction = 1;	//keep track of direction 1 if moving right (i.e. next was called), -1 if moving left (i.e. previous was called)
	  
    /**
     * Default constructor 
     * starts the cursor at index 0 (beginning of the list)
     * which is offset 0 in the node after the head
     */
    public StoutListIterator()
    {
    	curIndex = 0;			//starting the iterator at index 0 (begining)
    	curNode = head.next;	//starting iterator in start node (1 after head)
    	curOffset = 0;			//starting offset at 0
    	canRemove = false;
    	
    }

    /**
     * Constructor finds node at a given position.
     * @param pos
     */
    public StoutListIterator(int pos)
    {
    	curIndex = pos;					//starting iterator index at given index 'pos'
    	curNode = find(pos).node;		//starting iterator in node that contains pos
    	curOffset = find(pos).offset;	//starting iterator at offset of pos
    	canRemove = false;
    	
    }

    /**
     * returns whether or not there is an element after the cursor
     */
    @Override
    public boolean hasNext()
    {
    	return curIndex < size;	
    }

    /**
     * moves the cursor forward one, serving the element passed by the cursor.
     * also updates direction to rightward and sets canRemove to true
     * @return element passed by cursor
     * @throws noSuchElementException if there is no next element to be served
     */
    @Override
    public E next()
    {
    	if (!hasNext()) {
			  throw new NoSuchElementException();
		}
		if (curOffset == curNode.count) { //if you are at the last element of the node, move cursor to the begining of the next node
			curNode = curNode.next;
			curOffset = 0;
		}
		E returnItem = curNode.data[curOffset++]; //this gets the item from the current offset and THEN increments curOffset (post-increment)
		//curOffset is updated in the line above here so we don't need to do curOffset++ here again
		curIndex++;
		canRemove = true;
		direction = 1;	//direction is "to the right"
		return returnItem;
    }

    /**
     * removes the item that was previously returned with next()
     * This is directional
     */
    @Override
    public void remove()
    {
    	if (!canRemove) {
    		throw new IllegalStateException(); 
    	}
    	if (direction == 1) {	//moving rightward
    		int off = curOffset - 1; //this only works because this is one directional
  	  		//this only works because this is one directional
  	  
	  	  	if (curNode == tail.previous && curNode.count < 2) {	//tail node with only 1 element. Delete the node
	  	  		curNode.previous.next = tail;
	  		  	curNode.next.previous = curNode.previous;
	  		  	curNode = curNode.previous;
	  		  	curOffset = curNode.count; //cursor at the end of the previous node
	  		  	
	  	  	}
	  	  	else if (curNode == tail.previous || curNode.count > nodeSize/2) {	//tail node with 2 or more elements OR node has more than M/2 elements
	  	  		curNode.removeItem(off);
	  	  		curOffset--;
	  	  	}
	  	  	else {	//n must have M/2 or less elements, so merging must happen.
	  	  		Node successor = curNode.next;
	  		  
	  	  		if (successor.count > nodeSize/2) {	//successor has more than M/2 elements. MiniMerge: move first successor element to n
	  	  			curNode.removeItem(off);
	  			  	curNode.addItem(successor.data[0]);
	  			  	successor.removeItem(0);
	  			  	curOffset--;
	  			  
	  	  		}else {	//successor has M/2 or less elements. Full merge
	  	  			//move all elements from successor to n and delete successor 
	  	  			curNode.removeItem(off);				//remove item
	  	  			for (int i = curNode.count; i < nodeSize; i++) {	//copy over successor elements
	  	  				if (successor.count == 0) {	//if there is nothing left in the successor, break the loop
	  	  					break;
	  	  				}
	  	  				curNode.addItem(successor.data[0]);
	  	  				successor.removeItem(0);
	  	  			}
	  			  
	  	  			//delete the successor node
	  	  			successor.next.previous = curNode;
	  	  			curNode.next = successor.next;
	  	  			curOffset--;
	  	  		}
	  	  	}
    	
    	} else if (direction == -1) {	//moving leftward
    		int off = curOffset; //this only works because this is one directional
  	  		//this only works because this is one directional
  	  
	  	  	if (curNode == tail.previous && curNode.count < 2) {	//tail node with only 1 element. Delete the node
	  	  		curNode.previous.next = tail;
	  		  	curNode.next.previous = curNode.previous;
	  		  	curNode = curNode.previous;
	  		  	curOffset = curNode.count; //cursor at the end of the previous node
	  		  	
	  	  	}
	  	  	else if (curNode == tail.previous || curNode.count > nodeSize/2) {	//tail node with 2 or more elements OR node has more than M/2 elements
	  	  		curNode.removeItem(off);
	  	  	}
	  	  	else {	//n must have M/2 or less elements, so merging must happen.
	  	  		Node successor = curNode.next;
	  		  
	  	  		if (successor.count > nodeSize/2) {	//successor has more than M/2 elements. MiniMerge: move first successor element to n
	  	  			curNode.removeItem(off);
	  			  	curNode.addItem(successor.data[0]);
	  			  	successor.removeItem(0);
	  			  
	  	  		}else {	//successor has M/2 or less elements. Full merge
	  	  			//move all elements from successor to n and delete successor 
	  	  			curNode.removeItem(off);				//remove item
	  	  			for (int i = curNode.count; i < nodeSize; i++) {	//copy over successor elements
	  	  				if (successor.count == 0) {	//if there is nothing left in the successor, break the loop
	  	  					break;
	  	  				}
	  	  				curNode.addItem(successor.data[0]);
	  	  				successor.removeItem(0);
	  	  			}
	  			  
	  	  			//delete the successor node
	  	  			successor.next.previous = curNode;
	  	  			curNode.next = successor.next;

	  	  		}
	  	  	}
    	}
  	  
  	  	//BOOKEEPING
  	  	size--;
  	  	canRemove = false;	
    }
    
    
    // Other methods you may want to add or override that could possibly facilitate 
    // other operations, for instance, addition, access to the previous element, etc.
    //
    // NOTE: I WROTE EVERYTHING BENEATH THIS POINT THAT IS WITHIN THE LIST ITERATOR
    
    /**
     * adds an element into the list to the list wherever the cursor is located
     * @param item - the item you want to add
     * @throws NullPointerException if you try to add a null item to the list
     */
    public void add(E item) throws NullPointerException {
    	if (item == null) {
    		throw new NullPointerException();
    	}
    	if(curIndex == size) {	//you are at the end of a node (or there are 0 nodes)
    		if(size == 0){
    			Node newNode = new Node(); //creates a new node. Size is accounted for in Node class
    			//link node into chain
    			head.next = newNode;
    			newNode.next = tail;
    			newNode.previous = head;
    			tail.previous = newNode;
    			//add item at offset 0
    			newNode.addItem(item);	//this adds E at the first available offset (always 0 in this case)
    				 
    			//CURSOR BOOKKEEPING
    	    	curOffset++;
    	    	curNode = newNode;
    		}
    		else if (tail.previous.count < nodeSize) {//checking if there is space in last cell for item. Previous cell being the head is covered in prior branch
    			tail.previous.addItem(item);//adds E at the first available offset
    				 
    			//CURSOR BOOKKEEPING
    			curOffset++;
    		}
    		else if (tail.previous.count == nodeSize) {//executes if the last node is full and you need to add another node.
    			Node newNode = new Node();
    				 
    			//link node into chain
    			tail.previous.next = newNode;
    			newNode.next = tail;
    			newNode.previous = tail.previous;
    			tail.previous = newNode;
    			//add item at offset 0
    			newNode.addItem(item);
    				 
    			//CURSOR BOOKKEEPING
    			curOffset = 1;	//you are now in a new node right after the inserted element
    			curNode = newNode;
    		}
    	} else if (curOffset == 0 && curNode.previous != head && curNode.previous.count < nodeSize) {	
    		curNode.previous.addItem(item);
    		//should keep cursor in the same node and offset
    	} else if (curOffset == 0 && curNode == tail && curNode.previous.count < nodeSize) {
    		curNode.previous.addItem(item);
    				 
    		//CURSOR BOOKEEPING
    		curOffset++;
    	}else if (curNode.count < nodeSize) {//current node has space in it
    		curNode.addItem(curOffset, item);
    		curOffset++;
    		//stays in same node
    		
    	}else {	//the current node is full and must be split
    		Node newSucNode = new Node();
    			
    		//linking successor node into chain
    		curNode.next.previous = newSucNode;
    		newSucNode.next = curNode.next;
    		curNode.next = newSucNode;
    		newSucNode.previous = curNode;
    			
    		//move last M/2 elements of node n into new successor node
    		for(int i = 0; i < nodeSize/2; i++) {
    			newSucNode.addItem(curNode.data[nodeSize/2]);
    			curNode.removeItem(nodeSize/2);
    		}
    			
    		if (curOffset <= nodeSize/2) {	//we were in the 1st half of the node
    			curNode.addItem(curOffset, item);
    			curOffset++;
   				
   			}
    		else if(curOffset > nodeSize/2) {	//we were in the 2nd half of the node
    			int newPos = curOffset - nodeSize/2;
    			newSucNode.addItem(newPos, item);
    			curOffset = newPos + 1;
   				curNode = curNode.next;
   			}
    	}
    	
    	//BOOKKEEPING
    	size++;
    	curIndex++;
    }
    /**
     * returns the next index of the iterator (the index of what element 
     * will be served next...in this case curIndex)
     */
    public int nextIndex() {
    	return curIndex;
    }
    
    /**
     * returns the previous index of the iterator
     */
    public int previousIndex() {
    	return curIndex - 1; 
    }
    
    /**
     * returns whether or not there is an element to the left of the iterator
     */
    public boolean hasPrevious() {
    	return curIndex > 0;
    }
    
    /**
     * moves the cursor backwards (left) one, serving the element passed by the cursor.
     * also updates direction to leftward (-1) and sets canRemove to true
     * @return element passed by cursor
     * @throws noSuchElementException if there is no next element to be served
     */
    public E previous() {
    	if (!hasPrevious()) {
			  throw new NoSuchElementException();
		}
		if (curOffset == 0) { //if you are at the first element of the node, move cursor to the end of the last node
			curNode = curNode.previous;
			curOffset = curNode.count;
		}
		E returnItem = curNode.data[--curOffset]; //this decrements the current offset and THEN gets the item (pre-increment)
		//curOffset is updated in the line above here so we don't need to do curOffset++ here again
		curIndex--;
		canRemove = true;
		direction = -1;	//direction is "to the left"
		return returnItem;
    }
    
    /**
     * replaces the last element served with the passed item and
     * serves the item that was replaced
     * @param item - item to replace the last served element
     * @return replaced item
     */
    public void set(E item) {
    	if (!canRemove) {
    		throw new IllegalStateException();
    	}
    	if (direction == 1) {	//If traveling right, set object to left of cursor	(because it was just returned)
    		curNode.data[previousIndex()] = item;
    	}
    	else if (direction == -1) {	//If traveling left, set object right of cursor (because it was just returned)
    		curNode.data[nextIndex()] = item;
    		
    	}
    }
  }
  

  /**
   * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING order. 
   * @param arr   array storing elements from the list 
   * @param comp  comparator used in sorting 
   */
  private void insertionSort(E[] arr, Comparator<? super E> comp)
  {
	  int length = arr.length;
	  E key;
	  int i, j;
	  for (i = 1; i < length; i++){
		  key = arr[i];
		  j = i - 1;
		  while (j >= 0 && arr[j].compareTo(key) > 0){
			  arr[j + 1] = arr[j];
			  j = j - 1;
		  }
		  arr[j + 1] = key;
	  }
  }
  
  /**
   * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a 
   * description of bubble sort please refer to Section 6.1 in the project description. 
   * You must use the compareTo() method from an implementation of the Comparable 
   * interface by the class E or ? super E. 
   * @param arr  array holding elements from the list
   */
  private void bubbleSort(E[] arr)
  {
	   int n = arr.length;  
	   E temp;
	   for(int i=0; i < n; i++){  
		   for(int j=1; j < (n-i); j++){  
			   if(arr[j-1].compareTo(arr[j]) < 0){  
				   
				   //swap elements  
		           temp = arr[j-1];  
		           arr[j-1] = arr[j];  
		           arr[j] = temp;  
		       }                    
		   }
	   }
	  
  }
  
  //I CREATED EVERYTHING PAST THIS POINT
  
  /**
   * private class that creates objects that store a node and offset.
   * Used for finding where an index is in the list.
   * @author Tanner Smith
   */
  private class NodeInfo
  {
	  public Node node;
	  public int offset;
	  public NodeInfo(Node node, int offset)
	  {
		 this.node = node;
		 this.offset = offset;
	  }
  }
  
  /**
   * returns the node and offset for the given logical index
   * 
   * @param pos - logical index
   * @return NodeInfo object storing the node and offset of index 'pos'
   */
  NodeInfo find(int pos){
	  int desiredIndex = pos;
	  int curIndex = 0;
	  Node curNode = head.next;
	  int curOffset = 0;
	  while(curNode != tail && curIndex + curNode.count <= desiredIndex) {	//"curNode != tail" is so the program doesn't try "curNode.count" on the tail node
		  curIndex += curNode.count;	//this line HAS to come before the last line (won't work otherwise)
		  curNode = curNode.next;
	  } 
	  //after breaking the previous while loop, you are now in the correct node
	  
	  while(curIndex < desiredIndex) {
		  curIndex++;
		  curOffset++;
	  }
	  //after breaking the previous while loop, you are now at the correct offset
	  NodeInfo location = new NodeInfo(curNode, curOffset);
	  return location;
  }
}