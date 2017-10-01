package tech.vision8.pov.rxjava;

import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

/**
 * @author vision8
 */
public class FlatMapExamples {

    private static void example1() {

        List<String> wordsList = Arrays.asList("hello world", "a new", "line");
        Observable.fromIterable(wordsList)
                .flatMap(words -> Observable.fromArray(words.split("\\s+")))
                .subscribe(System.out::println);

    }

    public static void main(String... args) {

        System.out.println("\n---------- example 1 ----------\n");
        example1();

    }

}
