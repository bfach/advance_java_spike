package com.xyzcorp.java8;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Testing of functions 
 * @author bfach
 *
 */
public class LamdasTest {

	@Test
	public void testFilterFalse() {
		List<Integer> list = Arrays.asList(1, 2, 3);		
		
	    MyPredicate<Integer> predicate = new MyPredicate<Integer>() {

			@Override
			public boolean test(Integer t) {
				return false;
			}
			
		};
		
		
		assertEquals(0, Functions.myFilter(list, predicate).size());
	
	}
	
	@Test
	public void testFilterMod2() {
		List<Integer> list = Arrays.asList(1, 2, 3);		
		
	    MyPredicate<Integer> predicate = new MyPredicate<Integer>() {

			@Override
			public boolean test(Integer t) {
				return t % 2 == 0;
			}
			
		};
		
		
		assertEquals(1, Functions.myFilter(list, predicate).size());
	
	}
	
}
