package streams;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Steams {

    public static void main(String[] args) {

//        exampleOne();
        streamSetToSortedList();
    }

    private static void exampleOne() {
        List<String> myList = Arrays.asList("a", "b", "c", "c#", "c++", "d");

        myList
                .stream()
                .filter(s -> s.contains("c"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);
    }

    private static void streamSetToSortedList() {
        Set<String> mySet = new HashSet<>(
                Arrays.asList(
                        "foo",
                        "bar",
                        "mcveighs",
                        "gretzsky's",
                        "khao san rd."));

        List<String> sortedList = mySet
                .stream()
                .sorted()
                .collect(Collectors.toList());

        System.out.println(sortedList);
    }
}
