import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

class SnowflakeTest {

    @Test
    void generatesTenMillionUniqueIds_singleThread() {
        Snowflake snowflake = new Snowflake(1L, 1L);

        int total = 10_000_000;
        Set<Long> ids = ConcurrentHashMap.newKeySet();

        for (int i = 0; i < total; i++) {
            long id = snowflake.nextId();
            boolean added = ids.add(id);
            assertTrue(added, "Collision detected for id=" + id + " at iteration=" + i);
        }

        assertEquals(total, ids.size(), "Expected all generated IDs to be unique");
    }

    @Test
    void generatesTenMillionUniqueIds_multiThread() throws InterruptedException {
        Snowflake snowflake = new Snowflake(1L, 1L);

        int total = 10_000_000;
        int threads = Math.max(4, Runtime.getRuntime().availableProcessors());
        int perThread = total / threads;
        int remainder = total % threads;

        Set<Long> ids = ConcurrentHashMap.newKeySet();
        AtomicInteger generated = new AtomicInteger();
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(threads);

        for (int t = 0; t < threads; t++) {
            final int work = perThread + (t < remainder ? 1 : 0);

            Thread worker = new Thread(() -> {
                try {
                    start.await();

                    for (int i = 0; i < work; i++) {
                        long id = snowflake.nextId();
                        boolean added = ids.add(id);
                        assertTrue(added, "Collision detected for id=" + id);
                        generated.incrementAndGet();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                } finally {
                    done.countDown();
                }
            });

            worker.start();
        }

        start.countDown();
        done.await();

        assertEquals(total, generated.get(), "Expected to generate exactly 10,000,000 IDs");
        assertEquals(total, ids.size(), "Expected all generated IDs to be unique");
    }
}