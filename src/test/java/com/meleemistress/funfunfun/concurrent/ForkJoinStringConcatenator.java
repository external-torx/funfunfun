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
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

/**
 * @author hparry
 *
 */
public class ForkJoinStringConcatenator extends RecursiveTask<String> {

    private static final long serialVersionUID = 1L;
    private String[]          strings;
    private final int         threshold        = 2;

    public ForkJoinStringConcatenator(String[] strings) {
        this.strings = strings;
    }

    /* (non-Javadoc)
     * @see java.util.concurrent.RecursiveTask#compute()
     */
    @Override
    protected String compute() {
        if (strings.length == 0) {
            return null;
        }
        if (strings.length < threshold) {
            return strings[0];
        }
        if (strings.length == threshold) {
            return strings[0] + strings[1];
        } else {
            System.out.println("Strings: "
                               + Arrays.asList(strings).stream().collect(Collectors.joining()));
            ForkJoinStringConcatenator leftTask = new ForkJoinStringConcatenator(
                                                                                 Arrays.copyOfRange(strings,
                                                                                                    0,
                                                                                                    2));
            leftTask.fork();
            ForkJoinStringConcatenator rightTask = new ForkJoinStringConcatenator(
                                                                                  Arrays.copyOfRange(strings,
                                                                                                     2,
                                                                                                     strings.length));
            String rightResult = rightTask.compute();
            String leftResult = leftTask.join();
            System.out.println(String.format("left result: %s  --  right result: %s",
                                             leftResult, rightResult));
            if (leftResult != null && rightResult != null)
                return leftResult + rightResult;

            if (leftResult == null) {
                return rightResult;

            }
            return leftResult;

        }
    }

}
