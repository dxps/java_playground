/**
 * Snowflake-style ID generator.<br/><br/>
 * Guarantees uniqueness only if all of these remain true:<br/>
 * 1) each running generator has a globally unique (datacenterId, workerId) pair<br/>
 * 2) the system clock does not move backwards beyond the tolerated threshold<br/>
 * 3) the same node identity is not reused incorrectly by another live generator<br/><br/>
 * Notes:<br/>
 * - This class is thread-safe for callers sharing one instance.<br/>
 * - It is not "collision-proof" across the whole world by itself.<br/>
 * - Operational discipline around node ID assignment still matters.
 */
public class Snowflake {

    /*
     * Bit layout: 41 bits timestamp delta | 5 bits datacenter | 5 bits worker | 12 bits sequence
     * Total = 63 bits, leaving the sign bit unused so the result stays positive.
     */

    /** Custom epoch: 2021-01-01T00:00:00Z */
    private static final long EPOCH = 1609459200000L;

    /** Number of bits allocated to the worker/node identifier */
    private static final long WORKER_ID_BITS = 5L;

    /** Number of bits allocated to the datacenter identifier */
    private static final long DATACENTER_ID_BITS = 5L;

    /** Number of bits allocated to the per-millisecond sequence */
    private static final long SEQUENCE_BITS = 12L;

    /** Max worker ID = 31 */
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

    /** Max datacenter ID = 31 */
    private static final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);

    /** Mask for sequence = 4095 */
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    /** Sequence occupies the lowest 12 bits */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    /** Datacenter sits above worker */
    private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    /** Timestamp sits above datacenter + worker + sequence */
    private static final long TIMESTAMP_LEFT_SHIFT =
            SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

    /**
     * Small clock rollback tolerance in milliseconds.
     * Because minor backward clock jumps can happen from clock sync or VM jitter.
     * Behavior:
     * - if rollback <= tolerated amount, wait until lastTimestamp
     * - if rollback > tolerated amount, fail fast
     */
    private static final long MAX_BACKWARD_MS = 5L;

    /** Must be globally unique in combination with datacenterId across live generators. */
    private final long workerId;

    /** Must be globally unique in combination with workerId across live generators. */
    private final long datacenterId;

    /** Sequence counter within the same millisecond. */
    private long sequence = 0L;

    /** Last timestamp used to generate an ID. */
    private long lastTimestamp = -1L;

    /*
     * Note: Intentionally no default constructor is provided.
     * A default (0,0) is dangerous in distributed systems because
     * multiple nodes may accidentally use it and generate collisions.
     */

    public Snowflake(long workerId, long datacenterId) {
        validateRange(workerId, MAX_WORKER_ID, "workerId");
        validateRange(datacenterId, MAX_DATACENTER_ID, "datacenterId");
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * Generates the next unique ID.
     * Synchronized to make the instance safe for concurrent use by multiple threads.
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        // Here, we tolerate very small rollbacks by waiting.
        if (timestamp < lastTimestamp) {
            long offset = lastTimestamp - timestamp;

            if (offset <= MAX_BACKWARD_MS) {
                timestamp = waitUntil(lastTimestamp);
            } else {
                throw new IllegalStateException(
                        "Clock moved backwards by " + offset + " ms. Refusing to generate id.");
            }
        }

        if (timestamp == lastTimestamp) {
            // Same millisecond: increment sequence and keep only the lower 12 bits.
            sequence = (sequence + 1) & SEQUENCE_MASK;

            // If sequence wraps to 0, we have exhausted 4096 IDs in this millisecond.
            // Wait for the next millisecond to avoid collisions.
            if (sequence == 0L) {
                timestamp = waitUntil(lastTimestamp);
            }
        } else {
            // New millisecond: reset sequence.
            // This is standard Snowflake behavior.
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        // Parentheses added for correctness visibility and maintenance.
        return ((timestamp - EPOCH) << TIMESTAMP_LEFT_SHIFT)
                | (datacenterId << DATACENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }

    /**
     * Wait until time advances beyond the given timestamp.
     * `yield` is used for reducing CPU waste slightly.
     */
    private long waitUntil(long targetTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= targetTimestamp) {
            Thread.yield();
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * Returns current wall clock time in milliseconds.<br/><br/>
     * Left as a method rather than inlining so it can be overridden in tests
     * if you later switch this class to package-private non-final or introduce a Clock.
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    private static void validateRange(long value, long max, String fieldName) {
        if (value < 0 || value > max) {
            throw new IllegalArgumentException(
                    fieldName + " must be between 0 and " + max + ", got " + value);
        }
    }

    public long getWorkerId() {
        return workerId;
    }

    public long getDatacenterId() {
        return datacenterId;
    }

    public long getLastTimestamp() {
        return lastTimestamp;
    }

    public long getSequence() {
        return sequence;
    }
}