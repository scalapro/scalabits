package com.cologique.scalabits.circle1.review;


class ArraySnippets {
	public static void main(String[] args) {
		String[] a1 = { "something" };
		@SuppressWarnings("unused")
		Object[] a2 = a1;
		// a2[0] = new Double(100.0); // Runtime ArrayStoreException.
		String string = a1[0];
		System.out.println(string);
		
		int[] a3 = {1, 2, 3};
		System.out.println(a3);
		// println(a3); // No auto-boxing of array elements.
		// reverse(a3); // No auto-boxing of array elements.
		Integer[] a4 = {1, 2, 3};
		println(a4);
		
		Object[] reverse1 = reverse(a4);
		println(reverse1);
		
		// Integer[] a6 = (Integer[])a5; // No can do! Runtime ClassCastException.
		
		Integer[] reverse2 = new Integer[a4.length];
		genericReverse(a4, reverse2);
		println(reverse2);
		
		Integer[] reverse3 = genericInPlaceReverse(a4);
		println(reverse3);
		
	}
	
	public static Object[] reverse(Object[] array) {
		if (array == null) return null;
		Object[] rev = new Object[array.length];
		for (int i = 0; i < array.length; i++)
			rev[array.length - 1 - i] = array[i];
		return rev;
	}
	
	public static <T> T[] genericInPlaceReverse(T[] array) {
		if (array == null)
			return null;
		int mid = array.length / 2;
		for (int i = 0; i < mid; i++) 
			swap(array, i, array.length - 1 - i);
		return array;
	}
	
	private static <T> void swap(T[] array, int pos1, int pos2) {
		T temp = array[pos1];
		array[pos1] = array[pos2];
		array[pos2] = temp;
	}
	
	public static <T> T[] genericReverse(T[] array, T[] reverse) {
		if (array == null)
			return null;
		if (reverse == null)
			throw new IllegalArgumentException("reverse expected to to have been allocated");
		if (reverse.length != array.length)
			throw new IllegalArgumentException("reverse must be the sane size as array");
		for (int i = 0; i < array.length; i++)
			reverse[array.length - 1 - i] = array[i];
		return reverse;
	}
	
	/**
	 * Wrong!
	 */
	public static <T> T[] genericAttemptReverseAlloc(T[] array) {
		T[] reverse = null;
		// T[] reverse = new T[array.length]; // Not alllowd in Java generics.
		// ...
		return reverse;
	}
	
	public static void println(Object[] array) {
		if (array == null)
			return;
		for (Object o : array) 
			System.out.print(o + " ");
		System.out.println();
			
	}
}