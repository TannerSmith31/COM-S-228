package edu.iastate.cs228.hw3;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import org.junit.Assert; //added for Zach's cases

import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.w3c.dom.Node;

public class StoutListTester {

	@Test
	public void testMyAdd1() {
		StoutList<String> n = new StoutList<String>();
		n.add("A");  n.add("B");  n.add("1");  n.add("2");
		assertEquals("[(A, B, 1, 2)]", n.toStringInternal());
	}
	
	@Test
	public void testMyAdd2() {
		StoutList<String> n = new StoutList<String>();
		n.add("A");  n.add("B");  n.add("1");  n.add("2");  n.add("C");
		assertEquals("[(A, B, 1, 2), (C, -, -, -)]", n.toStringInternal());
	}
	
	@Test
	public void testStoutIterator1() {
		StoutList<String> n = new StoutList<String>();
		n.add("A");  n.add("B");  n.add("1");  n.add("2");  n.add("C");
		
		Iterator<String> iter = n.iterator();
		assertEquals(iter.next(), "A");
		assertEquals(iter.next(), "B");
		assertEquals(iter.next(), "1");
		assertEquals(iter.next(), "2");
		assertEquals(iter.next(), "C");
	}
	
	@Test
	public void testIterRemove(){
		StoutList<String> n = new StoutList<String>();
		n.add("A");  n.add("B");  n.add("1");  n.add("2");  n.add("C");
		
		Iterator<String> iter = n.iterator();
		iter.next();
		iter.remove();
		assertEquals("[(B, 1, 2, -), (C, -, -, -)]",n.toStringInternal());
		iter.next();
		iter.next();
		iter.remove();
		assertEquals("[(B, 2, -, -), (C, -, -, -)]",n.toStringInternal());
		
	}
	
	@Test
	public void testListIterator1() {
		StoutList<String> n = new StoutList<String>();
		n.add("A");  n.add("B");  n.add("1");  n.add("2");  n.add("C");
		
		ListIterator<String> iter = n.listIterator();
		assertEquals(iter.next(), "A");
		assertEquals(iter.next(), "B");
		assertEquals(iter.next(), "1");
		assertEquals(iter.previous(), "1");
		assertEquals(iter.previous(), "B");
		assertEquals(iter.next(), "B");
		iter.next();
		iter.next();
		assertEquals(iter.next(), "C");
		
	}
	
	@Test
	public void testListIterator2() {	//test cursor
		StoutList<String> n = new StoutList<String>();
		n.add("A");  n.add("B");  n.add("1");  n.add("2");  n.add("C");
		
		ListIterator<String> iter = n.listIterator();
		assertEquals("[(| A, B, 1, 2), (C, -, -, -)]", n.toStringInternal(iter));
		iter.next();
		iter.next();
		assertEquals("[(A, B, | 1, 2), (C, -, -, -)]", n.toStringInternal(iter));
		iter.previous();
		assertEquals("[(A, | B, 1, 2), (C, -, -, -)]", n.toStringInternal(iter));
		
	}
	
	@Test
	public void testRemove1() {
		StoutList<String> n = new StoutList<String>();
		n.add("A");  n.add("B");  n.add("1");  n.add("2");  n.add("C");
		
		Iterator<String> iter = n.iterator();
		iter.next();
		iter.next();
		iter.remove();
		assertEquals("[(A, 1, 2, -), (C, -, -, -)]", n.toStringInternal());
		iter.next();
		iter.remove();
		assertEquals("[(A, 2, -, -), (C, -, -, -)]", n.toStringInternal());
		iter.next();
		iter.next();
		iter.remove();
		assertEquals("[(A, 2, -, -)]", n.toStringInternal());
		
	}
	
	@Test
	public void testAddPos1() {
		StoutList<String> n = new StoutList<String>();
		n.add("A");  n.add("B");  n.add("1");  n.add("2");  n.add("C");
		//"[(A, B, 1, 2), (C, -, -, -)]"
		
		n.add(3, "D");
		assertEquals("[(A, B, -, -), (1, D, 2, -), (C, -, -, -)]", n.toStringInternal());
		
		n.add(4, "E");
		assertEquals("[(A, B, -, -), (1, D, E, 2), (C, -, -, -)]", n.toStringInternal());
		
		n.add(4, "F");
		assertEquals("[(A, B, -, -), (1, D, F, -), (E, 2, -, -), (C, -, -, -)]", n.toStringInternal());
		
		//is this correct? because as it stands, you cant add to the end of the list with the add(pos, E) function...
		n.add(7, "G");
		assertEquals("[(A, B, -, -), (1, D, F, -), (E, 2, G, -), (C, -, -, -)]", n.toStringInternal());
		
		n.add(9, "H");
		assertEquals("[(A, B, -, -), (1, D, F, -), (E, 2, G, -), (C, H, -, -)]", n.toStringInternal());	//ERRORS
		
	}
	
	@Test
	public void testAddPos2() {
		StoutList<String> n = new StoutList<String>();
		n.add("0");  n.add("5");  n.add("7");  n.add("99");  n.add("3");
		n.add("2"); n.add("10");  n.add("11"); n.add("1"); n.add("9");
		n.add("6");
		
		n.add(4, "88");
		assertEquals("[(0, 5, 7, 99), (88, 3, 2, -), (10, 11, -, -), (1, 9, 6, -)]", n.toStringInternal());
	}
	
	@Test
	public void testSet1() {
		StoutList<String> n = new StoutList<String>();
		n.add("A");  n.add("B");  n.add("1");  n.add("2");  n.add("C");
		
		ListIterator<String> iter = n.listIterator();
		iter.next();
		iter.next();
		iter.set("X");
		assertEquals("[(A, X, 1, 2), (C, -, -, -)]", n.toStringInternal());
		iter.set("Y");
		assertEquals("[(A, Y, 1, 2), (C, -, -, -)]", n.toStringInternal());
		
	}
	
	@Test
	public void testIterAdd() {
		StoutList<String> n = new StoutList<String>();
		n.add("0");  n.add("5");  n.add("3");  n.add("2");  n.add("1");
		n.add("9"); n.add("6");
		
		ListIterator<String> iter = n.listIterator(2);
		iter.add("7");
		assertEquals("[(0, 5, 7, -), (3, 2, -, -), (1, 9, 6, -)]", n.toStringInternal());
		iter.next(); iter.next();
		iter.add("10");
		assertEquals("[(0, 5, 7, -), (3, 2, 10, -), (1, 9, 6, -)]", n.toStringInternal());
		iter.add("11");
		assertEquals("[(0, 5, 7, -), (3, 2, 10, 11), (1, 9, 6, -)]", n.toStringInternal());
		iter.previous(); iter.previous(); iter.previous(); iter.previous();
		iter.add("99");
		assertEquals("[(0, 5, 7, 99), (3, 2, 10, 11), (1, 9, 6, -)]", n.toStringInternal());
		iter.add("88");
		assertEquals("[(0, 5, 7, 99), (88, 3, 2, -), (10, 11, -, -), (1, 9, 6, -)]", n.toStringInternal());
		//ASK ABOUT THIS PART!
	}
	
	
	

	@Test
	public void pdfExample() {
		StoutList<String> n = new StoutList<String>();
		n.add("A");  n.add("B");  n.add("1");	 n.add("2"); 
		n.add("C");	 n.add("D");  n.add("E");    n.add("F");
		n.remove(2); n.remove(2);
		//How do I go to the next node?
		n.remove(5);
		//After calling remove(5) you are at the start for the pdf example
		n.add("V");
		assertEquals("[(A, B, -, -), (C, D, E, V)]",n.toStringInternal());
		n.add("W");
		assertEquals("[(A, B, -, -), (C, D, E, V), (W, -, -, -)]",n.toStringInternal());
		n.add(2,"X");
		assertEquals("[(A, B, X, -), (C, D, E, V), (W, -, -, -)]",n.toStringInternal());
		n.add(2,"Y");
		assertEquals("[(A, B, Y, X), (C, D, E, V), (W, -, -, -)]",n.toStringInternal());
		n.add(2,"Z");
		assertEquals("[(A, B, Z, -), (Y, X, -, -), (C, D, E, V), (W, -, -, -)]",n.toStringInternal());
		System.out.println(n.toStringInternal());

		//////FROM THE COURSE PAGE
		
		//Removes W
		n.remove(9);
		System.out.println(n.toStringInternal());
		assertEquals("[(A, B, Z, -), (Y, X, -, -), (C, D, E, V)]",n.toStringInternal());

		//Removes Y
		n.remove(3);
		System.out.println(n.toStringInternal());
		assertEquals("[(A, B, Z, -), (X, C, -, -), (D, E, V, -)]",n.toStringInternal());

		//Removes X (mini merge)
		n.remove(3);
		System.out.println(n.toStringInternal());
		assertEquals("[(A, B, Z, -), (C, D, -, -), (E, V, -, -)]",n.toStringInternal());
		System.out.println(n.toStringInternal());

		//Removes E (No merge with predecessor Node)
		n.remove(5);
		System.out.println(n.toStringInternal());
		assertEquals("[(A, B, Z, -), (C, D, -, -), (V, -, -, -)]",n.toStringInternal());
		System.out.println(n.toStringInternal());

		//Removes C (Full merge with successor Node)
		n.remove(3);
		System.out.println(n.toStringInternal());
		assertEquals("[(A, B, Z, -), (D, V, -, -)]",n.toStringInternal());
		System.out.println(n.toStringInternal());
	}
		
		////FROM ZACH
		
		@Test
	      public void testExample() {
	           StoutList<String> stoutList = new StoutList<>();
	           stoutList.add("A");
	           stoutList.add("B");
	           stoutList.add("Z");
	           stoutList.add("Z");
	           stoutList.add("C");
	           stoutList.add("D");
	           stoutList.add("E");
	           stoutList.remove(2);
	           stoutList.remove(2);
//	        System.out.println(stoutList.toStringInternal());
	           Assert.assertEquals("[(A, B, -, -), (C, D, E, -)]", stoutList.toStringInternal());

//	        System.out.println("After adding V");
	           stoutList.add("V");
//	        System.out.println(stoutList.toStringInternal());
	           Assert.assertEquals("[(A, B, -, -), (C, D, E, V)]", stoutList.toStringInternal());

//	        System.out.println("After adding W");
	           stoutList.add("W");
//	        System.out.println(stoutList.toStringInternal());
	           Assert.assertEquals("[(A, B, -, -), (C, D, E, V), (W, -, -, -)]", stoutList.toStringInternal());

//	        System.out.println("After adding X");
	           stoutList.add(2, "X");
//	        System.out.println(stoutList.toStringInternal());
	           Assert.assertEquals("[(A, B, X, -), (C, D, E, V), (W, -, -, -)]", stoutList.toStringInternal());

//	        System.out.println("After adding Y");
	           stoutList.add(2, "Y");
//	        System.out.println(stoutList.toStringInternal());
	           Assert.assertEquals("[(A, B, Y, X), (C, D, E, V), (W, -, -, -)]", stoutList.toStringInternal());

//	        System.out.println("After adding Z");
	           stoutList.add(2, "Z");
//	        System.out.println(stoutList.toStringInternal());
	           Assert.assertEquals("[(A, B, Z, -), (Y, X, -, -), (C, D, E, V), (W, -, -, -)]", stoutList.toStringInternal());

//	        System.out.println("After removing W");
	           stoutList.remove(9);
//	        System.out.println(stoutList.toStringInternal());
	           Assert.assertEquals("[(A, B, Z, -), (Y, X, -, -), (C, D, E, V)]", stoutList.toStringInternal());

//	        System.out.println("After removing Y (mini-merge)");
	           stoutList.remove(3);
//	        System.out.println(stoutList.toStringInternal());
	           Assert.assertEquals("[(A, B, Z, -), (X, C, -, -), (D, E, V, -)]", stoutList.toStringInternal());

//	        System.out.println("After removing x (mini-merge)");
	           stoutList.remove(3);
//	        System.out.println(stoutList.toStringInternal());
	           Assert.assertEquals("[(A, B, Z, -), (C, D, -, -), (E, V, -, -)]", stoutList.toStringInternal());

//	        System.out.println("After removing E (no-merge)");
	           stoutList.remove(5);
//	        System.out.println(stoutList.toStringInternal());
	           Assert.assertEquals("[(A, B, Z, -), (C, D, -, -), (V, -, -, -)]", stoutList.toStringInternal());

//	        System.out.println("After removing C (full - merge)");
	           stoutList.remove(3);
//	        System.out.println(stoutList.toStringInternal());
	           Assert.assertEquals("[(A, B, Z, -), (D, V, -, -)]", stoutList.toStringInternal());
	      }

	      // Oscars Tests

	      @Test
	      public void testAdd() {
	           StoutList<Integer> stoutList = new StoutList<Integer>();
	           stoutList.add(5);
	           Assert.assertEquals("[(5, -, -, -)]", stoutList.toStringInternal());
	      }

	      @Test
	      public void testAdd2() {
	           StoutList<Integer> stoutList = new StoutList<>();
	           stoutList.add(5);
	           stoutList.add(7);
	           stoutList.add(1);
	           stoutList.add(-2);
	           stoutList.add(16);
	           stoutList.add(0);
	           Assert.assertEquals("[(5, 7, 1, -2), (16, 0, -, -)]", stoutList.toStringInternal());
	      }

	      @Test
	      public void testListIteratorConstructorWithPosition() {
	           StoutList<Integer> stoutList = new StoutList<>();
	           stoutList.add(8);
	           stoutList.add(3);
	           stoutList.add(5);
	           ListIterator<Integer> iter = stoutList.listIterator(1);
	           Assert.assertEquals(1, iter.nextIndex());
	      }

	      //probs wrong
	  @Test
	  public void testListIteratorConstructorWithPositionNewNode() {
	        StoutList<Integer> stoutList = new StoutList<>(2);
	        stoutList.add(7);
	        stoutList.add(2);
	        stoutList.add(4);
	        stoutList.add(3);
	        stoutList.add(8);
	        Integer integer = -1;
	        stoutList.add(integer);
	        stoutList.add(0);
	        ListIterator<Integer> iter = stoutList.listIterator(5);
	        System.out.println(stoutList.toStringInternal(iter));
//	          System.out.println(iter.next());
//	          System.out.println(stoutList.toStringInternal(iter));
//	          System.out.println(iter.previous());
//	          System.out.println(stoutList.toStringInternal(iter));
	        Assert.assertEquals(integer, iter.next());
	  }

	  @Test
	  public void testNext() {
	        StoutList<Integer> stoutList = new StoutList<>();
	        stoutList.add(5);
	        stoutList.add(7);
	        stoutList.add(1);
	        ListIterator<Integer> iter = stoutList.listIterator();
	        
	        System.out.println(stoutList.toStringInternal());
	        
	        Assert.assertEquals(5, iter.next().intValue());
	  }

	  @Test
	  public void testNextWithNewNode() {
	        StoutList<Integer> stoutList = new StoutList<>(2);
	        stoutList.add(5);
	        stoutList.add(7);
	        stoutList.add(9);
	        ListIterator<Integer> iter = stoutList.listIterator(2);
	        Assert.assertEquals(9, iter.next().intValue());
	  }

	  @Test
	  public void testPrevious() {
	        StoutList<Integer> stoutList = new StoutList<>(2);
	        stoutList.add(5);
	        stoutList.add(7);
	        stoutList.add(9);
	        ListIterator<Integer> iter = stoutList.listIterator(2);
	        Assert.assertEquals(9, iter.next().intValue());
	  }

	  @Test
	  public void testPreviousStart() {
	        StoutList<Integer> stoutList = new StoutList<>(2);
	        stoutList.add(5);
	        stoutList.add(7);
	        stoutList.add(9);
	        ListIterator<Integer> iter = stoutList.listIterator(2);
	        iter.previous();
	        iter.previous();
	        Assert.assertEquals(5, iter.next().intValue());
	  }

	  @Test
	  public void testPreviousFromEnd() {
	        StoutList<Integer> stoutList = new StoutList<>(2);
	        stoutList.add(5);
	        stoutList.add(7);
	        stoutList.add(9);
	        stoutList.add(3);
	        ListIterator<Integer> iter = stoutList.listIterator(3);
	        Assert.assertEquals(3, iter.next().intValue());
	  }

	  @Test
	  public void testContainsTrue() {
	        StoutList<Integer> stoutList = new StoutList<>();
	        stoutList.add(10);
	        stoutList.add(43);
	        stoutList.add(-23);
	        Integer check = 3;
	        stoutList.add(check);
	        stoutList.add(-52);
	        Assert.assertTrue(stoutList.contains(check));
	  }

	  @Test
	  public void testContainsFalse() {
	        StoutList<Integer> stoutList = new StoutList<>();
	        stoutList.add(4);
	        stoutList.add(7);
	        stoutList.add(-23);
	        stoutList.add(13);
	        stoutList.add(-2);
	        Assert.assertFalse(stoutList.contains(8));
	  }

	      @Test
	      public void testNextIndex() {
	           StoutList<Integer> stoutList = new StoutList<>();
	           stoutList.add(13);
	           stoutList.add(8);
	           stoutList.add(3);
	           ListIterator<Integer> iterator = stoutList.listIterator(1);
	           Assert.assertEquals(1, iterator.nextIndex());
	      }

	  @Test
	  public void testNextIndexSize() {
	        StoutList<Integer> stoutList = new StoutList<>();
	        stoutList.add(4);
	        stoutList.add(9);
	        stoutList.add(2);
	        ListIterator<Integer> iterator = stoutList.listIterator();
	        iterator.next();
	        iterator.next();
	        iterator.next();
	        Assert.assertEquals(3, iterator.nextIndex());
	  }

	      @Test
	      public void testPreviousIndex() {
	           StoutList<Integer> stoutList = new StoutList<>();
	           stoutList.add(-5);
	           stoutList.add(-9);
	           stoutList.add(2);
	           ListIterator<Integer> iterator = stoutList.listIterator(1);
	           Assert.assertEquals(0, iterator.previousIndex());
	      }

	      @Test
	      public void testPreviousIndexStart() {
	           StoutList<Integer> stoutList = new StoutList<>();
	           stoutList.add(7);
	           stoutList.add(-4);
	           stoutList.add(5);
	           ListIterator<Integer> iterator = stoutList.listIterator();
	           Assert.assertEquals(-1, iterator.previousIndex());
	      }

	      @Test
	      public void testHasNextTrue() {
	           StoutList<Integer> stoutList = new StoutList<>();
	           stoutList.add(9);
	           stoutList.add(1);
	           stoutList.add(2);
	            Assert.assertTrue(stoutList.iterator().hasNext());
	      }

	      @Test
	      public void testHasNextTrueEndOfNode() {
	           StoutList<Integer> stoutList = new StoutList<>(2);
	           stoutList.add(9);
	           stoutList.add(1);
	           stoutList.add(2);
	           ListIterator<Integer> iterator = stoutList.listIterator(2);
	            Assert.assertTrue(iterator.hasNext());
	      }

	      @Test
	      public void testHasNextFalse() {
	           StoutList<Integer> stoutList = new StoutList<>();
	           stoutList.add(10);
	           stoutList.add(-4);
	           stoutList.add(7);
	           ListIterator<Integer> iterator = stoutList.listIterator(2);
	           iterator.next();
	            Assert.assertFalse(iterator.hasNext());
	      }

	      @Test
	      public void testHasPreviousTrue() {
	           StoutList<Integer> stoutList = new StoutList<>();
	           stoutList.add(3);
	           stoutList.add(0);
	           stoutList.add(15);
	           ListIterator<Integer> iterator = stoutList.listIterator(1);
	            Assert.assertTrue(iterator.hasPrevious());
	      }

	      @Test
	      public void testHasPreviousTrueEndOfNode() {
	           StoutList<Integer> stoutList = new StoutList<>();
	           stoutList.add(5);
	           stoutList.add(-1);
	           stoutList.add(5);
	           ListIterator<Integer> iterator = stoutList.listIterator(2);
	            Assert.assertTrue(iterator.hasPrevious());
	      }

	      @Test
	      public void testHasPreviousFalse() {
	           StoutList<Integer> stoutList = new StoutList<>();
	           stoutList.add(4);
	           stoutList.add(-6);
	           stoutList.add(2);
	           ListIterator<Integer> iterator = stoutList.listIterator();
	            Assert.assertFalse(iterator.hasPrevious());
	      }

	  @Test
	  public void testSetNext() {
	        StoutList<Integer> stoutList = new StoutList<>();
	        stoutList.add(-7);
	        stoutList.add(0);
	        stoutList.add(4);
	        ListIterator<Integer> iterator = stoutList.listIterator(2);
	        iterator.previous();
	        Integer integer = -4;
	        iterator.set(integer);
	        Assert.assertEquals(integer, iterator.next());
	  }

	  @Test
	  public void testSetPrevious() {
	        StoutList<Integer> stoutList = new StoutList<>();
	        stoutList.add(-7);
	        stoutList.add(0);
	        stoutList.add(4);
	        ListIterator<Integer> iterator = stoutList.listIterator(1);
	        iterator.next();
	        Integer integer = -4;
	        iterator.set(integer);
	        Assert.assertEquals(integer, iterator.previous());
	        iterator.previous();
	        iterator.set(2);
	        integer = 2;
	        assertEquals(integer, iterator.next());
	        
	  }

	  @Test
	  public void testSetTwice() {
	        StoutList<Integer> stoutList = new StoutList<>();
	        stoutList.add(-7);
	        stoutList.add(0);
	        stoutList.add(4);
	        ListIterator<Integer> iterator = stoutList.listIterator(1);
	        iterator.next();
	        Integer integer = -4;
	        iterator.set(integer);
	        integer = 2;
	        iterator.set(integer);
	        Assert.assertEquals("[(-7, 2, 4, -)]", stoutList.toStringInternal());
	  }

	  @Test
	  public void testGet() {
	        StoutList<Integer> stoutList = new StoutList<>();
	        stoutList.add(-7);
	        stoutList.add(0);
	        Integer integer = 4;
	        stoutList.add(integer);
	        Assert.assertEquals(integer, stoutList.get(2));
	  }

	      @Test
	      public void testAddIndex() {
	           StoutList<Integer> stoutList = new StoutList<>();
	           stoutList.add(-7);
	           stoutList.add(0);
	           stoutList.add(1, 4);
	           Assert.assertEquals("[(-7, 4, 0, -)]", stoutList.toStringInternal());
	      }

	  @Test
	  public void testAddIndexNewNode() {
	        StoutList<Integer> stoutList = new StoutList<>(2);
	        stoutList.add(-7);
	        stoutList.add(0);
	        stoutList.add(2, 4);
	        Assert.assertEquals("[(-7, 0), (4, -)]", stoutList.toStringInternal());
	  }

	      @Test
	      public void testAddIndexNewNodeSplit() {
	           StoutList<Integer> stoutList = new StoutList<>(2);
	           stoutList.add(-7);
	           stoutList.add(0);
	           stoutList.add(1, 4);
	           Assert.assertEquals("[(-7, 4), (0, -)]", stoutList.toStringInternal());
	      }

	      @Test
	      public void testAddIndexTail() {
	           StoutList<Integer> stoutList = new StoutList<>(2);
	           stoutList.add(0);
	           Assert.assertEquals("[(0, -)]", stoutList.toStringInternal());
	      }

	      @Test
	      public void testRemoveNext() {
	           StoutList<Integer> stoutList = new StoutList<>(2);
	           stoutList.add(0);
	           stoutList.add(7);
	           stoutList.remove(0);
	           Assert.assertEquals("[(7, -)]", stoutList.toStringInternal());
	      }

	      @Test
	      public void testRemoveNextDeleteNode() {
	           StoutList<Integer> stoutList = new StoutList<>(2);
	           stoutList.add(0);
	           stoutList.add(7);
	           stoutList.add(3);
	           stoutList.remove(2);
	           Assert.assertEquals("[(0, 7)]", stoutList.toStringInternal());
	      }

	      @Test
	      public void testRemoveNextDeleteFullMerge() {
	           StoutList<Integer> stoutList = new StoutList<>(4);
	           stoutList.add(0);
	           stoutList.add(1);
	           stoutList.add(2);
	           stoutList.add(3);
	           stoutList.add(4);
	           stoutList.remove(1);
	           stoutList.remove(1);
	           stoutList.remove(1);
	           Assert.assertEquals("[(0, 4, -, -)]", stoutList.toStringInternal());
	      }

	      @Test
	      public void testRemoveNextDeleteMiniMerge() {
	           StoutList<Integer> stoutList = new StoutList<>(4);
	           stoutList.add(0);
	           stoutList.add(7);
	           stoutList.add(3);
	           stoutList.add(4);
	           stoutList.add(1);
	           stoutList.add(2);
	           stoutList.add(5);
	           stoutList.remove(1);
	           stoutList.remove(1);
	           stoutList.remove(1);
	           Assert.assertEquals("[(0, 1, -, -), (2, 5, -, -)]", stoutList.toStringInternal());
	      }

	      @Test
	      public void testRemoveNextEmpty() {
	           StoutList<Integer> stoutList = new StoutList<>(2);
	           stoutList.add(0);
	           stoutList.remove(0);
	           Assert.assertEquals("[]", stoutList.toStringInternal());
	      }

	  @Test
	  public void testRemoveNextNoMerge() {
	        StoutList<Integer> stoutList = new StoutList<>(2);
	        stoutList.add(0);
	        stoutList.add(7);
	        stoutList.add(3);
	        stoutList.add(2);
	        stoutList.remove(2);
	        Assert.assertEquals("[(0, 7), (2, -)]", stoutList.toStringInternal());
	  }

	      @Test
	      public void testRemoveNextEnd() {
	           StoutList<Integer> stoutList = new StoutList<>(4);
	           stoutList.add(0);
	           stoutList.add(7);
	           stoutList.add(3);
	           stoutList.add(2);
	           stoutList.add(5);
	           stoutList.add(1);
	           stoutList.add(8);
	           stoutList.add(9);
	           stoutList.add(10);
	           stoutList.remove(8);
	           Assert.assertEquals("[(0, 7, 3, 2), (5, 1, 8, 9)]", stoutList.toStringInternal());
	      }

	  @Test
	  public void testRemoveNextTwice() {
	        StoutList<Integer> stoutList = new StoutList<>(2);
	        stoutList.add(0);
	        stoutList.add(7);
	        stoutList.add(3);
	        stoutList.add(2);
	        ListIterator<Integer> iterator = stoutList.listIterator(2);
	        iterator.next();
	        iterator.remove();
	        boolean threwException = false;
	        try {
	             iterator.remove();
	        } catch (IllegalStateException e) {
	             threwException = true;
	        }
	        Assert.assertTrue(threwException);
	  }

	  @Test
	  public void testRemovePast() {
	        StoutList<Integer> stoutList = new StoutList<>(2);
	        stoutList.add(0);
	        stoutList.add(7);
	        ListIterator<Integer> iterator = stoutList.listIterator(2);
	        iterator.previous();
	        iterator.remove();
	        Assert.assertEquals("[(0, -)]", stoutList.toStringInternal());
	  }

	      @Test
	      public void testRemovePastDeleteNode() {
	           StoutList<Integer> stoutList = new StoutList<>(2);
	           stoutList.add(0);
	           stoutList.add(7);
	           stoutList.add(3);
	           ListIterator<Integer> iterator = stoutList.listIterator(3);
	           iterator.previous();
	           iterator.remove();
	           Assert.assertEquals("[(0, 7)]", stoutList.toStringInternal());
	      }

	  @Test
	  public void testRemovePastFullMerge() {
	        StoutList<Integer> stoutList = new StoutList<>(4);
	        stoutList.add(0);
	        stoutList.add(1);
	        stoutList.add(2);
	        stoutList.add(3);
	        stoutList.add(4);
	        stoutList.add(5);
	        stoutList.remove(2);
	        stoutList.remove(2);
	        ListIterator<Integer> iterator = stoutList.listIterator(2);
	        iterator.previous();
	        iterator.remove();
	        Assert.assertEquals("[(0, 4, 5, -)]", stoutList.toStringInternal());
	  }

	  @Test
	  public void testRemovePastMiniMerge() {
	        StoutList<Integer> stoutList = new StoutList<>(4);
	        stoutList.add(0);
	        stoutList.add(7);
	        stoutList.add(3);
	        stoutList.add(4);
	        stoutList.add(1);
	        stoutList.add(2);
	        stoutList.add(5);
	        stoutList.remove(1);
	        stoutList.remove(1);
	        ListIterator<Integer> iterator = stoutList.listIterator(2);
	        iterator.previous();
	        iterator.remove();
	        Assert.assertEquals("[(0, 1, -, -), (2, 5, -, -)]", stoutList.toStringInternal());
	  }

	  @Test
	  public void testRemovePastNormal() {
	        StoutList<Integer> stoutList = new StoutList<>(8);
	        stoutList.add(0);
	        stoutList.add(7);
	        stoutList.add(3);
	        stoutList.add(9);
	        stoutList.add(4);
	        stoutList.add(5);
	        ListIterator<Integer> iterator = stoutList.listIterator(2);
	        iterator.previous();
	        iterator.remove();
	        Assert.assertEquals("[(0, 3, 9, 4, 5, -, -, -)]", stoutList.toStringInternal());
	  }

	      @Test
	      public void testRemovePastEmpty() {
	           StoutList<Integer> stoutList = new StoutList<>(2);
	           stoutList.add(0);
	           ListIterator<Integer> iterator = stoutList.listIterator(1);
	           iterator.previous();
	           iterator.remove();
	           assertEquals("[]", stoutList.toStringInternal());
	      }

	  @Test
	  public void testRemovePastRemoveTwice() {
	        StoutList<Integer> stoutList = new StoutList<>(8);
	        stoutList.add(0);
	        stoutList.add(7);
	        stoutList.add(3);
	        stoutList.add(9);
	        stoutList.add(4);
	        stoutList.add(5);
	        ListIterator<Integer> iterator = stoutList.listIterator(2);
	        iterator.previous();
	        iterator.remove();
	        boolean threwException = false;
	        try {
	             iterator.remove();
	        } catch (IllegalStateException e) {
	             threwException = true;
	        }
	        Assert.assertTrue(threwException);
	  }

	      @Test
	      public void testSortInsertion() {
	           StoutList<Integer> stoutList = new StoutList<>(4);
	           stoutList.add(0);
	           stoutList.add(7);
	           stoutList.add(3);
	           stoutList.add(9);
	           stoutList.add(4);
	           stoutList.add(5);
	           stoutList.sort();
	           Assert.assertEquals("[(0, 3, 4, 5), (7, 9, -, -)]", stoutList.toStringInternal());
	      }

	      @Test
	      public void testSortBubble() {
	           StoutList<Integer> stoutList = new StoutList<>(4);
	           stoutList.add(0);
	           stoutList.add(7);
	           stoutList.add(3);
	           stoutList.add(9);
	           stoutList.add(4);
	           stoutList.add(5);
	           stoutList.sortReverse();
	           Assert.assertEquals("[(9, 7, 5, 4), (3, 0, -, -)]", stoutList.toStringInternal());

		

	}
}