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
package io.retrier.handler;


import io.retrier.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * {@link RetryCountHandler} is a {@link Handler} implementation to make sure retry is happening within the max retries limit.
 */
public class RetryCountHandler implements Handler {

  private static final Logger LOGGER = LoggerFactory.getLogger(RetryCountHandler.class);

  private final int maxRetries;
  private final AtomicInteger retryCount;

  public RetryCountHandler(int maxRetries) {
    validate(maxRetries);
    this.maxRetries = maxRetries;
    this.retryCount = new AtomicInteger();
  }

  private void validate(int maxRetries) {
    if (maxRetries <= 0) {
      throw new IllegalArgumentException("Max retry count should be positive.");
    }
  }

  @Override
  public void handlePreExec() {
    retryCount.incrementAndGet();
  }

  @Override
  public void handleException(Exception e) throws Exception {
    if (retryCount.get() >= maxRetries) {
      logFailure();
      throw e;
    }

    logSuccess();
  }

  private void logFailure() {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Exceeded Max Retries of ", maxRetries);
    }
  }

  private void logSuccess() {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Current retry count'{}' is within max retries limit '{}'", retryCount.get(), maxRetries);
    }
  }
}
