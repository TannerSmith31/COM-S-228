package edu.iastate.cs228.hw3;
import java.util.Iterator;
import java.util.ListIterator;

public class StoutListMain {
	public static void main(String []args) {
		StoutList<Integer> stoutList = new StoutList<>(2);
        stoutList.add(0);
        stoutList.add(null);
        ListIterator<Integer> iterator = stoutList.listIterator(2);
        iterator.previous();
        iterator.remove();
        iterator.add(0);
		System.out.println(stoutList.toStringInternal());
	}
}
