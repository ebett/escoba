package org.javagames.escoba.core;

import java.util.concurrent.CompletableFuture;

public class DelayedAction {

  public static void delay(final long time, final Runnable runnable) {
    CompletableFuture.runAsync(() -> {
      try {
        Thread.sleep(time);
      }
      catch (InterruptedException e) {}

      runnable.run();
    });
  }
}
