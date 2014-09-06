/**
 * Copyright (c) 2014 Halloran Parry, all rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.meleemistress.funfunfun.notation;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.meleemistress.funfunfun.interfaces.MathyStuff;

/**
 * @author hparry
 *
 */
public class NotationTest {

    @Test
    public void testAdd() {
        BinaryOperator<Integer> add = (Integer x, Integer y) -> x + y;
        assertEquals(Integer.valueOf(5), add.apply(2, 3));
    }

    @Test
    public void testDoMath() {
        MathyStuff multiply = (x, y) -> x * y;
        assertEquals(8, multiply.doMath(4, 2));

        MathyStuff add = (x, y) -> x + y;
        assertEquals(10, add.doMath(8, 2));

        assertEquals(10, add.doMath(multiply.doMath(4, 2), 2));
    }

    @Test
    public void testFilter() {
        String[] strings = { "good ash", "bad ash", "necronomicon" };
        Predicate<String> isAsh = (String var) -> var.contains("ash");

        List<String> aspectsOfAsh = Stream.of(strings).filter(isAsh).collect(Collectors.toList());

        assertEquals(2, aspectsOfAsh.size());
    }

    @Test
    public void testFlatMap() {
        List<Integer> list = Stream.of(asList(1, 2, 3), asList(4, 5)).flatMap(numbers -> numbers.stream()).map(num -> num * 2).collect(toList());
        Stream.of(list).filter(number -> {
            System.out.println(number);
            return true;
        }).count();
        assertEquals(2, list.get(0).intValue());
    }

    @Test
    public void testReduce() {
        List<Integer> list = asList(1, 2, 3, 4);
        int sum = list.stream().reduce(0, (acc, elem) -> acc + elem);
        assertEquals(10, sum);
    }

    @Test
    public void testCountLowercase() {
        String foo = "Foo";
        int count = foo.chars().map((int elem) -> Character.isLowerCase(elem) ? 1
                                                                             : 0).sum();
        assertEquals(2, count);
    }

    @Test
    public void testMostLowercase() {
        Function<String, Integer> countLowercase = (String string) -> string.chars().map((int elem) -> Character.isLowerCase(elem) ? 1
                                                                                                                                  : 0).sum();
        String most = asList("Foo", "BAR", "baz", "duuuuuude", "DUUUUUUdDE").stream().reduce("",
                                                                                             (current,
                                                                                              elem) -> countLowercase.apply(current) > countLowercase.apply(elem) ? current
                                                                                                                                                                 : elem);
        assertEquals("duuuuuude", most);
    }

    @Test
    public void testMapImplementation() {
        Function<Integer, Integer> lambda = arg -> arg * 2;

        List<Integer> list = Stream.of(1, 2, 3).reduce(new ArrayList<Integer>(),
                                                       (BiFunction<List<Integer>, ? super Integer, List<Integer>>) (List<Integer> accumulator,
                                                                                                                    Integer elem) -> {
                                                           if (accumulator == null) {
                                                               accumulator = new ArrayList<Integer>();
                                                           }
                                                           accumulator.add(lambda.apply(elem));
                                                           return accumulator;
                                                       }, (listA, listB) -> {
                                                           listA.addAll(listB);
                                                           return listA;
                                                       });

        assertEquals(Integer.valueOf(2), (Integer) list.get(0));
        System.out.println(list);

    }
    
    @Test
    public void testFilterImplementation() {
        List<Integer> list = filter(asList(1, 2, 3, 4, 5), elem -> elem % 2 == 0);
        assertEquals(Integer.valueOf(2), list.get(0));
    }
    
    private <T> List<T> filter(List<T> list, Predicate<? super T> filterLambda) {
        return list.stream().reduce(new ArrayList<T>(), (List<T> accumulator, T elem) -> {
            if (accumulator == null) {
                accumulator = new ArrayList<>();
            }
            if (filterLambda.test(elem)) {
                accumulator.add(elem);
            }
            return accumulator;
        }, (listA, listB) -> {
            listA.addAll(listB);
            return listA;
        });
    }
}
