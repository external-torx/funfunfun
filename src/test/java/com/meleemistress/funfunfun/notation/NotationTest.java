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

import static org.junit.Assert.assertEquals;

import java.util.function.BinaryOperator;

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
        assertEquals(8, multiply.doMath(4,  2));
        
        MathyStuff add = (x, y) -> x + y;
        assertEquals(10, add.doMath(8, 2));
        
        assertEquals(10, add.doMath(multiply.doMath(4, 2), 2));
    }

}
