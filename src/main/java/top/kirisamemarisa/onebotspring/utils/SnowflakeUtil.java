package top.kirisamemarisa.onebotspring.utils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author: MarisaDAZE
 * @Description: 雪花ID工具类
 * @Date: 2024/1/23
 */
public class SnowflakeUtil {
    private final long twepoch = 1288834974657L;
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private final long sequenceBits = 12L;
    private final long workerIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);
    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;
    private static final Random random = new Random();

    public SnowflakeUtil(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 生成雪花ID
     *
     * @return .
     */
    public synchronized String nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        long l = ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
        return String.valueOf(l);
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return Instant.now().toEpochMilli();
    }

    /**
     * <p> 生成一个雪花ID，但有可能会有重复</p>
     * <p>如需大量生成，请实例化工具类后调用nextId()方法</p>
     *
     * @return .
     */
    public static String nextIdOne() {
        int i1 = random.nextInt(31);
        int i2 = random.nextInt(31);
        SnowflakeUtil util = new SnowflakeUtil(i1, i2);
        return util.nextId();
    }

    public static void main(String[] args) {

        SnowflakeUtil idWorker = new SnowflakeUtil(0, 0);
        for (int i = 0; i < 1000; i++) {
            String id = idWorker.nextId();
            System.out.println(id);
        }
        //
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String id = SnowflakeUtil.nextIdOne();
            if (ids.contains(id)) {
                System.out.println("出现重复的了...");
            } else {
                ids.add(id);
            }
            System.out.println(id);
        }
    }
}