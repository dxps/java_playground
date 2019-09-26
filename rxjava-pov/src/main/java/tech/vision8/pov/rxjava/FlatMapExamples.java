package tech.vision8.pov.rxjava;

import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

/**
 * Examples using flatMap operator.
 *
 * @author vision8
 */
public class FlatMapExamples {

    private final static List<String> wordsList = Arrays.asList("hello world", "a new", "line");


    /**
     * A classic example of flatMap usage: splitting (by space) each string
     * (that may contain one or more words) into words. The result is multiple
     * Observables, each created by an emission. flatMap flattens them all back
     * to a single Observable to which we can subscribe.
     */
    private static void example1() {

        System.out.println("\n---------- example 1 ----------\n");
        Observable.fromIterable(wordsList)
                .flatMap(words -> Observable.fromArray(words.split("\\s+")))
                .subscribe(System.out::println);

    }


    /**
     * This example is based on the previous example and it uses zipWith operator
     * just to be able to prepend each emission with an index (counter).
     */
    private static void example2() {

        System.out.println("\n---------- example 2 ----------\n");
        Observable.fromIterable(wordsList)
                .flatMap(words -> Observable.fromArray(words.split("\\s+")))
                .zipWith(
                        Observable.range(1, Integer.MAX_VALUE),
                        (word, index) -> String.format("%2d. %s", index, word))
                .subscribe(System.out::println);

    }


    public static void main(String... args) {

        example1();
        example2();

    }

}
