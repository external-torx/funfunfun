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

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Supplier;

import javax.swing.text.DateFormatter;

import org.junit.Test;

/**
 * @author hparry
 *
 */
public class ThreadLocalTest {
    
    @Test
    public void testThreadLocalConstruction() {
        Supplier<Boolean> supplier = () -> true;
        ThreadLocal<Boolean> tl = ThreadLocal.withInitial(supplier);
        assertTrue(tl.get());
    }
    
    @Test
    public void testThreadLocalDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-YYYY");
        Supplier<DateFormatter> supplier = () -> new DateFormatter(sdf);
        ThreadLocal<DateFormatter> tl = ThreadLocal.withInitial(supplier);
        System.out.println(tl.get().getFormat().format(new Date()));
        
    }

}
