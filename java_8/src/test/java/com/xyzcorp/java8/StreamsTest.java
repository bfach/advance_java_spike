package com.xyzcorp.java8;

import org.junit.Test;

import java.time.ZoneId;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Created by bfach on 10/10/16.
 */
public class StreamsTest {

    @Test
    public void testBasicStream() {
        /**
         * Stream<Integer> stream = Arrays.asList(1, 2, 3, 4, 5).stream();
         //map (cut the ingredients for the sandwich)
         Stream<Integer> integerStream = stream.map( x -> x + 1);
         //reduce (put sandwich together)
         System.out.println(integerStream.collect(Collectors.toList()));
         */


        //map (cut the ingredients for the sandwich)
        //reduce (put sandwich together)
        System.out.println(Arrays.asList(1, 2, 3, 4, 5).stream().map(x -> x + 1).collect(Collectors.toList()));
    }

    @Test
    public void testBasicCount() {
        System.out.println(Arrays.asList(1, 3, 4, 5, 6).stream().map(x -> x + 1).filter(x -> x % 2 != 0).count());
    }

    @Test
    public void testOurOwnCollector() {
//        List<Integer> result = Arrays.asList(1,2,3,4,5).stream().map(x -> x + 1).collect(new Supplier<List<Integer>>() {
//            @Override
//            public List<Integer> get() {
//                //supplier of the list
//                return new ArrayList<Integer>();
//            }
//        }, new BiConsumer<List<Integer>, Integer>() {
//            @Override
//            public void accept(List<Integer> integers, Integer integer) {
//                System.out.format("Adding number %d", integer);
//                //add the item to the list!
//                integers.add(integer);
//            }
//        }, new BiConsumer<List<Integer>, List<Integer>>() {
//            @Override
//            //position property... integers is the thing to ADD TO... the other (integers2) is added to integers
//            public void accept(List<Integer> integers, List<Integer> integers2) {
//                System.out.format("left is %s", integers);
//                System.out.format("right is %s", integers2);
//                integers.addAll(integers2);
//            }
//        });

        List<Integer> result = Arrays.asList(1, 2, 3, 4, 5).stream().map(x -> x + 1).parallel().collect(ArrayList::new,
                (integers, integer) -> {
                    System.out.format("Adding number %d\n", integer);
                    //add the item to the list!
                    integers.add(integer);
                }, (integers, integers2) -> {
                    synchronized (this) {
                        System.out.format("left is %s\n", integers);
                        System.out.format("right is %s\n", integers2);
                        integers.addAll(integers2);
                    }
                });

        System.out.println("result = " + result);
    }

    @Test
    public void testSum() {
        Integer result = Arrays.asList(1, 4, 65, 1000).stream().parallel().collect(Collectors.summingInt(x -> x + 1));
        System.out.println("Result of sum is " + result);
    }


    @Test
    public void testAverageUsingStreamOfIntegers() {
        Double average = Stream.of(100, 90, 91, 43, 90, 85).collect(Collectors.averagingInt(x -> x));
        System.out.println("Average of values is " + average);
        assertEquals(83.16666666666667, average, 0.001);
    }

    @Test
    public void testAverageUsingStreamOfIntegersOptionalDouble() {
        OptionalDouble average = IntStream.of(100, 90, 91, 43, 90, 85).average();
        System.out.println("Average = " + average.getAsDouble());
    }

    @Test
    public void testAverageUsingStreamOfIntegersOptionalDoubleOrElseGet() {
        OptionalDouble average = IntStream.of().average();
        System.out.println("Average (orelseget) = " + average.orElseGet(() -> 100.00));
    }

    @Test
    public void testIntStreamSummaryStatistics() {
        Stream<Integer> numbers = Stream.of(100, 33, 22, 400, 30);
        IntStream intStream = numbers.mapToInt(x -> x);
        System.out.println("Summary stats " + intStream.summaryStatistics());
    }

    @Test
    public void testPeek() {
        Stream<Integer> numbers = Stream.of(100, 33, 22, 400, 30);
        IntStream intStream = numbers.peek(x -> System.out.println(x)).mapToInt(x -> x);
        System.out.println("Summary stats peek " + intStream.summaryStatistics());
    }

    @Test
    public void testLimit() {
        List<Integer> list = Stream.iterate(3, integer -> integer + 1).map(x -> x + 4).peek(System.out::println).limit(10)
                .collect(Collectors.toList());

        System.out.println("List of iterators " + list);
    }


    @Test
    public void factorial() {

        //The stream below doesn't make a ton of sense for factorial...
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6);
        Integer reduction = stream.reduce(1, (total, next) -> {
            System.out.format("total: %d, next: %d\n", total, next);
            return total * next;
        });
        System.out.println(reduction);

        //this uses a limit, which sets the factorial value
        //with a seed of 1
        int factorialSize = 3;
        stream = Stream.iterate(1, integer -> integer + 1).peek(System.out::println).limit(factorialSize);
        reduction = stream.reduce(1, (total, next) -> {
            System.out.format("total: %d, next: %d\n", total, next);
            return total * next;
        });
        System.out.println(reduction);

        //this uses a limit, which sets the factorial value
        //WITHOUT seed
        stream = Stream.iterate(1, integer -> integer + 1).peek(System.out::println).limit(factorialSize);
        Optional<Integer> reduction2 = stream.reduce(new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer total, Integer next) {
                System.out.format("total: %d, next: %d\n", total, next);
                return total * next;
            }
        });
        System.out.println(reduction2);

    }

    @Test
    public void testFlatMap() {
        //Stream<Stream<Integer>> streamStream = Stream.of(1, 2, 3, 4).map(x -> Stream.of(-x, x, x + 1));
        //this is where flatMap comes in... it is a combo of map and flatten
        Stream<Integer> streamStream = Stream.of(1, 2, 3, 4).flatMap(x -> Stream.of(-x, x, x + 1));

        System.out.println("stream stream" + streamStream.collect(Collectors.toList()));
    }

    @Test
    public void testSortedWithComparator() {
        Stream<String> stream = Stream.of("Apple", "Orange", "Banana", "Tomato",
                "Grapes");
        System.out.println(stream
                .sorted((string1, string2) -> string1.length() - string2.length())
                .collect(Collectors.toList()));
    }

    @Test
    public void testSortedWithComparatorLevels() {
        Stream<String> stream = Stream.of("Apple", "Orange", "Banana", "Tomato",
                "Grapes");
        Comparator<String> stringComparator = Comparator.comparing(String::length)
                .thenComparing(x -> x);
        System.out.println("Sorted stream" + stream
                .sorted(stringComparator)
                .collect(Collectors.toList()));
    }

    @Test
    public void testJoining() {
        System.out.println("List= " + Stream.of("Apple", "Orange", "Banana", "Tomato",
                "Grapes").collect(Collectors.joining(", ")));

        System.out.println("List2= " + Stream.of("Apple", "Orange", "Banana", "Tomato",
                "Grapes").collect(Collectors.joining(", ", "{", "}")));


    }

    /**
     * Step 1: java.time.ZoneId has a method called getAvailableZoneIds that returns a Set<String>,
     convert the Set<String> to a Stream<String>
     Step 2: Next find all the distinct time zones in the Americas.
     Step 3: Only return the name of the time zone not the prefix of America/. If the time zone was
     America/New_York, make sure that it is only New_York.
     Step 4: Use sorted() which uses the natural Comparable of the object
     Step 5: Recollect the stream back into a Set or List
     */
    @Test
    public void testZoneId() {
        System.out.println("Timezones= " + ZoneId.getAvailableZoneIds().stream()
                .filter(x -> x.startsWith("America"))
                .map(x -> x.split("/")[1]).sorted().collect(Collectors.toSet()));
    }
}
