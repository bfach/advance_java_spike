package com.xyzcorp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by bfach on 10/12/16.
 */
public class FindClosingDataForORCL {

    public static void main(String[] args) throws IOException {
        final URL url = new URL("http://ichart.finance.yahoo.com/table.csv?s=ORCL&a=00&b=01&c=2015");

        try (
                InputStream inputStream = url.openStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        ) {
//            System.out.println(bufferedReader.lines().collect(Collectors.toList()));
            //lets get closer (so split it out)
            //System.out.println(bufferedReader.lines().map(ln -> Arrays.asList(ln.split(","))).collect(Collectors.toList()));

            //lets get closer
            System.out.println("Average Oracle Price: $" + bufferedReader.lines()
                    .skip(1) //skip first line... headers in CSV
                    .map(ln -> Arrays.asList(ln.split(","))) //split on csv line
                    .map(ls -> Arrays.asList(ls.get(0), ls.get(4))) // take the first (date) and 5th (july) elements
                    .filter(ls -> LocalDate.parse(ls.get(0)).getMonth().equals(Month.JULY)) //filter out for july only

//THIS IS ONE WAY, but the stuff below comment is even better
//                    .map(ls -> Double.parseDouble(ls.get(1))) //parse the line with the amount
//                    .collect(Collectors.averagingDouble(x -> x))); //use the new double and throw it through an averaging collector
                    .mapToDouble(ls -> Double.parseDouble(ls.get(1))) //parse the line with the amount
                    .average().orElseGet(() -> 0.0)); //use the new double and throw it through an averaging collector

        }
    }
}
