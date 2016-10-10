package com.xyzcorp.java8;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		
	    assertEquals(0, Functions.myFilter(list, t -> false).size());
	
	}
	
	@Test
	public void testFilterMod2() {
		List<Integer> list = Arrays.asList(1, 2, 3);		
		
	    assertEquals(1, Functions.myFilter(list, t -> t % 2 == 0).size());
	
	}

	@Test
	public void testMap(){
		List<Integer> list = Arrays.asList(1, 2, 3, 5, 12);
		List<Integer> result = Functions.myMap(list, integer -> integer + 10);
		System.out.println("result = " + result);


		assertEquals((int)result.get(0), (int)11);
	}



//	@Test
//	public void testMapAlternate(){
//		List<String> list = Arrays.asList("foo", "bar", "baz", "diego", "Danno");
//		List<Integer> result = Functions.myMap(list, new MyFunction<String, Integer>() {
//			@Override
//			public Integer apply(String str) {
//				return str.length();
//			}
//		});
//		System.out.println("result = " + result);
//
//
//		assertEquals((int)result.get(0), (int)3);
//	}

	/**
	 * Using lamda is a bit better than having the embedded impl
	 */
//	@Test
//	public void testMapAlternate(){
//		List<String> list = Arrays.asList("foo", "bar", "baz", "diego", "Danno");
//		List<Integer> result = Functions.myMap(list, str -> str.length());
//		System.out.println("result = " + result);
//
//
//		assertEquals((int)result.get(0), (int)3);
//	}

	/**
	 * Functions reference instead of lamda above
	 */
	@Test
	public void testMapAlternate(){
		List<String> list = Arrays.asList("foo", "bar", "baz", "diego", "Danno");
		List<Integer> result = Functions.myMap(list, String::length);
		System.out.println("result = " + result);


		assertEquals((int)result.get(0), (int)3);
	}


	/**
	 * This is what consumer DOES... it takes something in and does some sort of side effect ELSEWHERE
	 */
	@Test
	public void testForeach(){
		final List<String> strings = Arrays.asList("foo", "bar", "baz", "diego", "Danno");
		Functions.myForEach(strings, s -> System.out.println(s));
	}

	/**
	 * This is what consumer DOES... it takes something in and does some sort of side effect ELSEWHERE
	 *
	 * This case is a method reference
	 */
	@Test
	public void testForeachMethodReference(){
		final List<String> strings = Arrays.asList("foo", "bar", "baz", "diego", "Danno");
		Functions.myForEach(strings, System.out::println);
	}





	/**
	 * Streams example :)
	 */
	@Test
	public void testForeachSum(){
		int sum = Stream.of(1, 2, 3, 5, 12).mapToInt(x -> x).sum();
		assertEquals(23, sum);
		System.out.println(sum);
	}

	/**
	 * Streams example 2 :)
	 */
	@Test
	public void testForeachSumWithFilter(){
		int sum = Stream.of(1, 2, 3, 5, 12).filter(x -> x > 10).collect(Collectors.summingInt(x -> x));
		assertEquals(12, sum);
		System.out.println(sum);
	}



	/**
	 * public void testMethodReferenceWithStaticMethod(){

	 List<Integer> integers = Arrays.asList(3, 1, -5, 4, 12, -9);
	 System.out.println(Functions.myMap(integers, x -> Math.abs(x)));

	 }
	 */
	@Test
	public void testMethodReferenceWithStaticMethod(){

		List<Integer> integers = Arrays.asList(3, 1, -5, 4, 12, -9);
		List<Integer> values = Functions.myMap(integers, Math::abs);
		System.out.println(values);

		//The below is just rolling it up and confirming it worked :)
		int sum = values.stream().mapToInt(x -> x).sum();
		assertEquals(34, sum);
	}


	@Test
	public void testMethodReferenceContainingTypeTrickQuestions(){
		List<Integer> integers = Arrays.asList(3, 1, -5, 4, 12, -9);
		//System.out.println(Functions.myMap(integers, i -> i.toString()));
		System.out.println(Functions.myMap(integers, Object::toString));
	}

	@Test
	public void testMethodReferenceAnInstance(){
		List<Integer> integers = Arrays.asList(3, 1, 4, 12, 10000000);

		TaxRate rate = new TaxRate(2016, 0.25);
		//System.out.println(Functions.myMap(integers, num -> rate.apply(num)));
		System.out.println(Functions.myMap(integers, rate::apply));
	}

	public void testMethodReferenceNewType(){
		List<Integer> integers = Arrays.asList(3, 1, 4, 12, 10000000);
		//System.out.println(Functions.myMap(integers, value -> new Double(value)));
		System.out.println(Functions.myMap(integers,  Double::new));

	}
}
