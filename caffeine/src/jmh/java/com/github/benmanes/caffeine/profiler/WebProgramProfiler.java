package com.github.benmanes.caffeine.profiler;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.jackrabbit.core.data.util.NamedThreadFactory;
import org.jspecify.annotations.Nullable;
import site.ycsb.generator.NumberGenerator;
import site.ycsb.generator.ScrambledZipfianGenerator;

import java.util.concurrent.Executors;

public class WebProgramProfiler extends ProfilerHook {


  private final int MAXIMUM_CAPACITY = 1000;
  private final int DATA_RANGE = 1000 * 4;
  private final AsyncCache<Integer, @Nullable Integer> cache;

  public WebProgramProfiler() {
    cache = Caffeine.newBuilder()
      .initialCapacity(MAXIMUM_CAPACITY)
      .maximumSize(MAXIMUM_CAPACITY)
      .executor(Executors.newFixedThreadPool(4, new NamedThreadFactory("back-thread")))
      .buildAsync(key -> key);
    for (int i = 0; i < DATA_RANGE; i++) {
      cache.get(i, k -> k);
    }

  }

  @Override
  protected void profile() {
    NumberGenerator generator = new ScrambledZipfianGenerator(DATA_RANGE);
    while (true) {
      cache.get(generator.nextValue().intValue(), k -> k);
      super.calls.increment();
    }
  }

  public static void main(String[] args) {
    WebProgramProfiler webProgramProfiler = new WebProgramProfiler();
    webProgramProfiler.run();
  }

}
