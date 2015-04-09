/*
 * Copyright (c) 2015 Halloran Parry, all rights reserved.
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
package com.meleemistress.funfunfun.concurrent;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author hparry
 *
 */
public class ConcurrencyTest {

    @Test
    public void testForkJoin() {
        String[] strings = new String[] { "a", "b", "c", "d", "e", "f", "g" };
        String expected = "abcdefg";
        ForkJoinStringConcatenator concatenator = new ForkJoinStringConcatenator(
                                                                                 strings);
        String actual = concatenator.compute();
        assertEquals(expected, actual);
    }

    @Test
    public void testStream() {
        String[] strings = new String[] { "a", "b", "c", "d", "e", "f", "g" };
        String expected = "abcdefg";

        String actual = Arrays.asList(strings).stream().parallel().collect(Collectors.joining());
        assertEquals(expected, actual);
    }

}
