package com.xyzcorp.java8;

import java.util.ArrayList;
import java.util.List;

/**
 * Create an implementation that filters a list of generic types, based on the
 * rule injected in the predicate implementation
 *
 * @param <T>
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

}
