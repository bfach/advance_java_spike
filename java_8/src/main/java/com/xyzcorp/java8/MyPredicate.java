package com.xyzcorp.java8;


/**
 * Can be used as a functional filter, lamda function :)
 * 
 * @author bfach
 *
 * @param <T>
 */
public interface MyPredicate<T> {

	public boolean test(T t);
}
