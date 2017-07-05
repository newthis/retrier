/*
 * Copyright 2017 Ravi Chaturvedi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.retrier;


import io.retrier.handler.exception.CompositeExceptionHandler;
import io.retrier.handler.exception.ExceptionHandler;
import io.retrier.handler.exception.ExceptionRunnerExceptionHandler;
import io.retrier.handler.exception.ExceptionsExceptionHandler;

public class Retry {

    public static ExceptionHandler on(ExceptionHandler... handlers) {
        return new CompositeExceptionHandler(handlers);
    }

    @SafeVarargs
    public static ExceptionHandler on(Class<? extends Exception>... exceptionClasses) {
        return new ExceptionsExceptionHandler(false, exceptionClasses);
    }

    public static ExceptionHandler on(Class<? extends Exception> exceptionClass, Runner runner) {
        return new ExceptionRunnerExceptionHandler(false, exceptionClass, runner);
    }

    @SafeVarargs
    public static ExceptionHandler onNested(Class<? extends Exception>... exceptionClasses) {
        return new ExceptionsExceptionHandler(true, exceptionClasses);
    }

    public static ExceptionHandler onNested(Class<? extends Exception> exceptionClass, Runner runner) {
        return new ExceptionRunnerExceptionHandler(true, exceptionClass, runner);
    }
}
