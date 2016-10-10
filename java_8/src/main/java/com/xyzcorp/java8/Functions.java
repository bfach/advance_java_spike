package com.xyzcorp.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Create an implementation that filters a list of generic types, based on the
 * rule injected in the predicate implementation
 *
 */
public class Functions {

	public static <T> List<T> myFilter(List<T> list, MyPredicate<T> predicate) {
		List<T> result = new ArrayList<>();

		for (T item : list) {
			// rule for filtering, based on predicate implementation
			if (predicate.test(item))
				result.add(item);
		}

		return result;
	}

//	/**
//	 * This is what a map does... takes a list, a function
//	 *
//	 * @param list
//	 * @param myFunction
//	 * @param <T>
//	 * @param <R>
//     * @return
//     */
//	public static <T, R> List<R> myMap(List<T> list, MyFunction<T, R> myFunction){
//
//		final List<R> result = new ArrayList<>();
//
//		for(T item : list){
//			result.add(myFunction.apply(item));
//		}
//
//		return result;
//	}

	/**
	 * This is what a map does... takes a list, a function
	 *
	 * @param list
	 * @param myFunction
	 * @param <T>
	 * @param <R>
	 * @return
	 */
	public static <T, R> List<R> myMap(List<T> list, MyFunction<T, R> myFunction){

		final List<R> result = new ArrayList<>();

		for(T item : list){
			result.add(myFunction.apply(item));
		}

		return result;
	}


	public static <T> void myForEach(List<T> list, MyConsumer<T> myConsumer){
		/*This allows us to accept the item... based on consumer rules*/
		for(T item : list){
			myConsumer.accept(item);
		}
	}

	/**
	 * want to create a list ... and use the supplier to supply what we want in the list
	 * @param supplier
	 * @param count
	 * @param <T>
     * @return
     */
	public static <T> List<T> generate(MySupplier<T> supplier, int count){

		final List<T> result = new ArrayList<>();

		for(int i = 0 ; i < count; i++){
			result.add(supplier.get());
		}

		return result;
	}

}
